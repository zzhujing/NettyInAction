package com.hujing.netty.source;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static java.nio.channels.SelectionKey.*;

/**
 * @author : hujing
 * @date : 2019/10/15
 */
public class NioServer {


    public static void main(String[] args) throws IOException {
        NioServer nioServer = new NioServer();
    }

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public NioServer() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8899));
        serverSocketChannel.register(selector, OP_ACCEPT);
        handlerKeys();
    }

    public void handlerKeys() throws IOException {
        while (true) {
            int readyCount = selector.select(); //获取就绪的channel数量
            if (readyCount == 0) continue;
            //获取所有就绪的事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                //从当前迭代器中移除该事件
                iter.remove();
                if (!key.isValid()) {
                    //直接跳出这次循环
                    continue;
                }
                handlerkey(key);
            }
        }
    }

    private void handlerkey(SelectionKey key) throws IOException {
        //如果key.intestops()为OP_ACCEPT
        if (key.isAcceptable()) {
            handlerAccept(key);
        }
        if (key.isReadable()) {
            handlerRead(key);
        }
        if (key.isWritable()) {
            handlerWritable(key);
        }
    }

    private void handlerWritable(SelectionKey key) throws ClosedChannelException {
        SocketChannel channel = (SocketChannel) key.channel();
        @SuppressWarnings("unchecked")
        List<String> response = (List<String>) key.attachment();
        for (String resp : response) {
            System.out.println("写入数据 ： " + resp);
            CodecUtil.write(channel, resp);
        }
        response.clear();
        channel.register(selector, OP_READ, response);
    }

    private void handlerRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer readBuffer = CodecUtil.read(channel);
        //说明读取数据为null,连接断开了
        if (readBuffer == null) {
            System.out.println("连接断开了" + channel);
            channel.register(selector, 0);
            return;
        }

        if (readBuffer.position() > 0) {
            String result = CodecUtil.newString(readBuffer);
            //打印result
            System.out.println("收到消息 : " + result);
            @SuppressWarnings("unchecked")
            List<String> response = (List<String>) key.attachment();
            response.add("响应" + result);
            channel.register(selector, OP_WRITE, response);
        }
    }

    private void handlerAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        System.out.println("接受到了新的客户端连接 ： " + socketChannel);
        //使用attachment来传递一个List
        socketChannel.register(selector, OP_READ, new ArrayList<String>());
    }

}
