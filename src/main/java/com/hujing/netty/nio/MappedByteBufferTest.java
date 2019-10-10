package com.hujing.netty.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author hujing
 * @date 19-9-23
 * 文件内存映射Buffer,可以直接操作内存,而不需要使用文件系统
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("mappedBuffer.txt", "rw");
        FileChannel channel = accessFile.getChannel();
        //获取文件的内存映射对象
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        buffer.put(0, (byte) 'a');
        buffer.put(1, (byte) 'a');
        accessFile.close();
    }
}
