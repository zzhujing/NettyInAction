package com.hujing.netty.source;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author : hujing
 * @date : 2019/10/15
 */
public class CodecUtil {


    public static ByteBuffer read(SocketChannel socketChannel) {

        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        int read = 0;
        try {
            read = socketChannel.read(readBuffer);
            if (read == -1) {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readBuffer;
    }

    public static String newString(ByteBuffer byteBuffer) {
        byteBuffer.flip();
        return new String(StandardCharsets.UTF_8.decode(byteBuffer).array());
    }

    public static void write(SocketChannel channel, String content) {
        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
        writeBuffer.put(content.getBytes(StandardCharsets.UTF_8));
        writeBuffer.flip();
        try {
            channel.write(writeBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
