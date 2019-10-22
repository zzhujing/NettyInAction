package com.hujing.netty.reactor;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReactorDemo {
    public static void main(String[] args) {

    }
}


class MainReactor implements Runnable{

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;


    public MainReactor() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8899));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT,new Accepter(serverSocketChannel));
    }

    @Override
    public void run() {
        while (true) {
            try {
                int readyCount = selector.select();
                if (readyCount==0) continue;
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey next = iter.next();
                    //分发
                    dispatch(next);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatch(SelectionKey next) {

        //使用Acceptor接受连接
        Runnable runnable = (Runnable) next.attachment();
        if (runnable != null) {
            runnable.run();
        }
    }
}

//接受ServerSocketChannel连接注册到SubReactor上
class Accepter implements Runnable{

    ServerSocketChannel serverSocketChannel;
    //初始化SubReactor
    SubReactor[] subReactors = new SubReactor[Runtime.getRuntime().availableProcessors() * 2];
    volatile int next = 0;
    public Accepter(ServerSocketChannel serverSocketChannel) throws IOException {
        this.serverSocketChannel = serverSocketChannel;
        init();
    }

    private void init() throws IOException {
        for (int i = 0; i < subReactors.length; i++) {
            subReactors[i] = new SubReactor();
        }
    }

    @Override
    public void run() {
        //接受连接
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            subReactors[next].register(socketChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class SubReactor implements Runnable{

    Selector selector;

    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public SubReactor() throws IOException {
        selector = Selector.open();
    }

    public void register(SocketChannel socketChannel) throws ClosedChannelException {
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    @Override
    public void run() {
        while (true) {
            try {
                int readyCount = selector.select();
                if (readyCount==0) continue;
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    if (next.isReadable()) {
                        read();
                    }
                    if (next.isWritable()) {
                        write();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void write() {
        executorService.submit(this::process);
    }

    private void process() {

    }

    private void read() {
        executorService.submit(this::process);
    }
}