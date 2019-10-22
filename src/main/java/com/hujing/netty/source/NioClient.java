package com.hujing.netty.source;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author : hujing
 * @date : 2019/10/15
 */
public class NioClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        NioClient client = new NioClient();
        for (int i = 0; i < 10; i++) {
            //连接上服务端之后向服务端发送数据
            client.send("From Client :" + i);
            Thread.sleep(1000L);
        }
    }

    private Selector selector;
    private SocketChannel socketChannel;
    private List<String> responseQueue = Lists.newArrayList();
    private CountDownLatch counted = new CountDownLatch(1);

    public NioClient() throws IOException, InterruptedException {
        selector = Selector.open();
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        new Thread(() -> {
            try {
                handlerKeys();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        if (counted.getCount()!=0) counted.await();

        System.out.println("client 启动完成");
    }

    private void handlerKeys() throws IOException {
        while (true) {
            int select = selector.select();
            if (select == 0) continue;
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if (!key.isValid()) {
                    continue;
                }
                if (key.isConnectable()) {
                    handlerConnectable(key);
                }
                if (key.isReadable()) {
                    handlerReadable(key);
                }
                if (key.isWritable()) {
                    handlerWritable(key);
                }
            }
        }
    }

    private void handlerWritable(SelectionKey key) throws ClosedChannelException {
        SocketChannel channel = (SocketChannel) key.channel();

        for (String content : responseQueue) {
            CodecUtil.write(channel, "客户端　：　" + content);
        }
        responseQueue.clear();
        channel.register(selector, SelectionKey.OP_READ, responseQueue);
    }

    private void handlerReadable(SelectionKey key) throws ClosedChannelException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer readBuffer = CodecUtil.read(channel);
        if (readBuffer == null) {
            System.out.println("连接断开");
            return;
        }
        if (readBuffer.position() > 0) {
            System.out.println("服务器响应：　" + CodecUtil.newString(readBuffer));
        }
    }

    private void handlerConnectable(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        if (!channel.isConnectionPending()) {
            return;
        }
        channel.finishConnect();
        System.out.println("发起新的连接" + channel);
        channel.register(selector, SelectionKey.OP_READ, responseQueue);
        //连接成功标记值-1
        counted.countDown();
    }

    private void send(String content) throws ClosedChannelException {
        responseQueue.add(content);
        socketChannel.register(selector, SelectionKey.OP_WRITE, responseQueue);
        //唤起在等待的Channel
        selector.wakeup();
    }
}
