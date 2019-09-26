package com.hujing.netty.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @author : hujing
 * @date : 2019/9/24
 */
public class NioCharsetTest {
    public static void main(String[] args) throws IOException {
        String inputFileStr = "charsetInput.txt";
        String outputFileStr = "charsetOutput.txt";
        RandomAccessFile inputFile = new RandomAccessFile(inputFileStr, "r");
        RandomAccessFile outputFile = new RandomAccessFile(outputFileStr, "rw");

        FileChannel fc1 = inputFile.getChannel();
        FileChannel fc2 = outputFile.getChannel();

        long inputFileLength = new File(inputFileStr).length();

        MappedByteBuffer buffer = fc1.map(FileChannel.MapMode.READ_ONLY, 0, inputFileLength);

        Charset charset = StandardCharsets.ISO_8859_1;
        CharBuffer charBuffer = charset.decode(buffer);
        ByteBuffer encode = charset.encode(charBuffer);
        fc2.write(encode);
    }
}
