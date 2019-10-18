package com.hujing.netty.test;

import com.google.common.collect.Maps;
import io.opencensus.internal.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * 基于nio的简易聊天程序
 */
public class NioChatServer {

    private static Map<String, SocketChannel> clientMap = Maps.newHashMap();


    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8899));
        serverSocketChannel.accept();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);


        for (; ; ) {
            selector.select(); //阻塞


            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                final SocketChannel client;

                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel) selectionKey.channel();
                    client = serverSocketChannel1.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                    clientMap.put(UUID.randomUUID().toString(), client);
                    System.out.println("客户端已连接");
                    iterator.remove();
                } else if (selectionKey.isReadable()) {
                    client = ((SocketChannel) selectionKey.channel());
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    while (client.read(readBuffer) > 0) {
                        readBuffer.flip();
                        String message = String.valueOf(StandardCharsets.UTF_8.decode(readBuffer).array());
                        String sender = null;
                        for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                            if (entry.getValue() == client) {
                                sender = entry.getKey();
                                break;
                            }
                        }
                        if (sender != null) {
                            //向其他客户端发送消息
                            for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                if (entry.getKey() != sender) {
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                    writeBuffer.put((sender + ":" + message).getBytes());
                                    writeBuffer.flip();
                                    entry.getValue().write(writeBuffer);
                                }
                            }
                        }
                        readBuffer.clear();
                    }
                    iterator.remove();
                }
            }
        }
    }
}
