package com.hujing.netty.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author : hujing
 * @date : 2019/9/25
 */
public class OldServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8899);

        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            byte[] buffer = new byte[4096];

            long readLength = 0;
            while (true) {
                int read = dataInputStream.read(buffer);
                readLength += read;
                if (read == -1) {
                    System.out.println("readLength " + readLength);
                    break;
                }
            }
        }
    }
}
