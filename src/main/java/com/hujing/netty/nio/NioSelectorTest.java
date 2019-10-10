package com.hujing.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author : hujing
 * @date : 2019/9/24
 * Nio SelectorTest
 * <p>
 * <p>
 * Selector
 * -> 是一个SelectableChannel的选择器,SelectableChannel通过注册SelectionKey对象来和Selector打交道，
 * SelectionKey代表的就是一些事件，比如连接创立，是否可读,Accept,readable,connected
 * 获取方式：
 * Selector#open()
 * SelectorProvider.openSelector();
 * Selector中含有三种SelectionKey的Set
 * <p>
 * - 当前所有的注册进来的SelectableChannel的Selectionkey,可使用keys()方法获取
 * - selectedSet : 进行过至少一种操作的SelectionKey集合，是KeySet的子集
 * - cancelledSet : 已经取消注册的SelectableChannel,是KeySet的子集
 * <p>
 * <p>
 * 当SelectableChannel调用registry()方法的时候，会将该SelectableChennel对应的SelectionKey添加到KeySet中
 * <p>
 * CancelledKeys会在Selector.select()方法调用的时候被清空，这个set本身不能进行直接的修改
 * <p>
 * - 删除所有的CancelledKeys
 * - 执行所有剩余Channel的Key事件
 * - 将所有还为加入到SelectedKeys的channel加入进去
 * - 已经准备好的Selected Key将会被按位加入到read Set中
 * - 如果第二步中有Key被Cancel那么将进行步骤1
 * <p>
 * CancelKey的几种情景：
 * -SelectableChannel.close()
 * -SelectionKey.cancel()
 * SelectedKey在通道unregistry()之后只能通过Set.remove()或者Iterate.remove()进行移除
 */
public class NioSelectorTest {
    public static void main(String[] args) throws IOException {
        int[] ports = new int[]{5000, 5001, 5002, 5003};

        Selector selector = Selector.open();
        //开启四个服务端
        for (int i = 0; i < ports.length; i++) {
            //开启服务端连接
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //使用非阻塞
            serverSocketChannel.configureBlocking(false);
            ServerSocket socket = serverSocketChannel.socket();
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            serverSocketChannel.bind(address);
            //注册到Selector中
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        }

        for (; ; ) {
            int num = selector.select();
            //打印连接的个数
            System.out.println("num = " + num);
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                //如果是Accept状态，那么将通过SelectionKey来获取到当前的SelectableChannel，然后注册到selector托管
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,  SelectionKey.OP_READ);
                    //事件执行完毕需要从迭代器中删除
                    iterator.remove();
                } else if (selectionKey.isReadable()) {
                    //获取到客户端Channel开始读取
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    System.out.println("socketChannel = " + socketChannel);
                    long readLength = 0;
                    for (; ; ) {
                        ByteBuffer buffer = ByteBuffer.allocate(512);
                        int read = socketChannel.read(buffer);
                        System.out.println("read = " + read);
                        if (read <= 0) {
                            break;
                        }
                        readLength += read;
                        buffer.flip();
                        socketChannel.write(buffer);
                    }
                    System.out.println("readLength : " + readLength + "from client : " + socketChannel);
                    iterator.remove();
                }
            }
        }
    }
}
