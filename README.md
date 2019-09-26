## 记录netty相关的笔记
---

> Reactor模式,就是使用Dispatch来就行任务分发的异步无阻塞的网络通信模式

#### 相比传统阻塞IO的优势
 - 不用为每个客户端都启动一个线程
 - 不用浪费太多资源在线程之间调度
 - 异步非阻塞
 - 分发不通的事件处理器来响应不同类型的IO事件
 
#### reactor里面的角色模型
- Reactor (Initiation Dispatcher): 定义一些规范控制事件的调度方式,又提供了Handler的注册方式.
        会通过同步事件分离器(Nio_Selector)来等待事件发生,一旦发生事件会回调处理器的回调方法 
        
- Synchronous Event Demultiplexer (Nio_Selector) : 控制事件分发,调用方调用后会同步阻塞(Selector.select())
    当有事件被激活的才进行下一步.在Linux中成为io的多路复用机制,比如select,poll,epoll.

- handler : 具体的事件,或是一种资源.比如连接建立,连接取消等.通常为句柄或者文件描述符.

- EventHandler : 处理对应事件的处理器

- ConCreate : 具体事件处理器的实现.


#### reactor的实现流程

- Reactor接受EventHandler的注册,EventHandler都包含自己感兴趣的Handler标示
- Reactor会调用同步事件分发器的同步阻塞方法等待有Handler被激活,并且Reactor会接受到该Handler
- Reactor根据该Handler会遍历寻找到对应的EventHandler执行回调方法
- 其中基于Netty的Accept存在的意义是将ParentEventLoopGroup中的连接传递给ChildEventLoopGroup去调度

#### 基于NIO的Reactor实现


- 建立reactor

```
        //1 构建ServerSocketChannel通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8899));
        serverSocketChannel.configureBlocking(false);
        //2 构建Selector
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
```

- 分发handler
```
    while (!Thread.interrupted()) {
        selector.select();
        Set selected = selector.selectedKeys();
        Iterator it = selected.iterator();
        while (it.hasNext())
            //分发handler
            dispatch((SelectionKey)(it.next());
            selected.clear();
            }
        } catch (IOException ex) { /* ... */ }
    }
    
    void dispatch(SelectionKey k) {
        Runnable r = (Runnable)(k.attachment());
        if (r != null)
        r.run();
    }

```

- Accept

```
class Acceptor implements Runnable { 
// inner
    public void run() {
      try {
        //接受客户端的请求
        SocketChannel c = serverSocket.accept();
        if (c != null)
           //创建Handler
          new Handler(selector, c);
      }
      catch(IOException ex) { /* ... */ }
    }
```
- Setup Handler
```
client = serverChannel.accept();
client.configureBlocking(false);
client.register(selector, SelectionKey.OP_READ);
```

- Request Handing

```
//netty4将不同的类型判断用不通的回调监听方法来展示
class Handler continued
   public void run() {
    try {
       if (state == READING) read();
       else if (state == SENDING) send();
    }
 catch (IOException ex) { /* ... */ }
  }
    //读取处理方式
  void read() throws IOException {socket.read(input);
    if (inputIsComplete()) {
       process();
        state = SENDING;
        // Normally also do first write nowsk.interestOps(SelectionKey.OP_WRITE);
    }
  } 
    //写出处理方式
 void send() throws IOException {
    socket.write(output);
        if (outputIsComplete())
     sk.cancel();
      }
 }


```

#### netty在创建Channel的时候机会创建字符缓冲区，并且根据是否有Unsafe包来判断采用堆外零拷贝还是堆内缓冲区