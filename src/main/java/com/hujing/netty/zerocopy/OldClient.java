package com.hujing.netty.zerocopy;

import lombok.Cleanup;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author : hujing
 * @date : 2019/9/25
 */
public class OldClient {
    public static void main(String[] args) throws IOException {
        @Cleanup Socket socket = new Socket("localhost", 8899);
        @Cleanup InputStream inputStream = new FileInputStream("/home/hujing/Documents/openjdk-8u40-b25-linux-x64-10_feb_2015.tar.gz");
        @Cleanup DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096 * 4096];
        long readLength = 0;
        int read = 0;
        long startTime = System.currentTimeMillis();
        while ((read = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, buffer.length);
            readLength += read;
        }
        System.out.println("readLength : " + readLength);
        System.out.println("Old Way Spend Time : " + (System.currentTimeMillis() - startTime));
    }
}
