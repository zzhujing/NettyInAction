package com.hujing.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author : hujing
 * @date : 2019/9/24
 * 使用Nio完成简单聊天服务端
 */
public class NioChatServerTest {


    private static Map<String, SocketChannel> clientMap = new HashMap<>();


    public static void main(String[] args) throws IOException {
        //1 构建ServerSocketChannel通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8899));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.accept();


        //2 构建Selector
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        for (; ; ) {

            //selector分发
            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            selectionKeys.forEach(selectionKey -> {

                final SocketChannel client;

                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();
                    try {
                        client = serverChannel.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        //将客户端的SocketChannel保存到Map中
                        clientMap.put("【" + UUID.randomUUID().toString() + "】", client);

                        System.out.println("连接成功 :" + client);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (selectionKey.isReadable()) {
                    client = ((SocketChannel) selectionKey.channel());
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    try {
                        int read = client.read(readBuffer);
                        if (read > 0) {
                            readBuffer.flip();
                            //在控制台输出自己客户端发出的话
                            System.out.println(readBuffer.position() + ", " + readBuffer.limit());
                            String message = String.valueOf(StandardCharsets.UTF_8.decode(readBuffer).array());
                            System.out.println(message);
                            System.out.println(readBuffer.position() + ", " + readBuffer.limit());
                            //获取当前Client的UUID
                            String sender = clientMap.keySet().stream().filter(k -> clientMap.get(k) == client).findAny().orElse(null);

                            //向其他客户端发送信息
                            clientMap.forEach((k, v) -> {
                                if (v != client) {
                                    try {
                                        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                        writeBuffer.put((sender + ": " + message).getBytes());
                                        writeBuffer.flip();
                                        v.write(writeBuffer);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            selectionKeys.clear();
        }

    }
}
