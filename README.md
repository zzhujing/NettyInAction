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
- Reactor会调用同步事件分发器的同步阻塞方法等待有Handler被激活,并且Reactor会接收到回调的Handler
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

> netty在创建Channel的时候机会创建字符缓冲区，并且根据是否有Unsafe包来判断采用堆外零拷贝还是堆内缓冲区

#### Channel

- 是一个`network socket`连接点，可以对`io`进行`read`,`wirte`,`connect`,`bind`

##### 作用
- 进行`io`操作，`read`,`wirte`,`connect`,`bind`
- 获取`io`状态，`isopen`,`isconnected`
- 配置Channel相关配置信息`ChannelConfig`
- 通过`channelPipeline`来处理所有的`ChannelHandlers`

#### ChannelPipeline

- 串联了一系列的`ChannelHandler`,处理获或者拦截channel的io读写事件，且是线程安全的，并且在添加自己的业务ChannleHandler的时候可以指定`EventExecutorGroup`，如果你处理器没有使用异步的话

- `InboundChannel` : 只会拦截处理入站请求，channel读如数据
- `OutboundCHannel` : 只会拦截出站请求，比较向Channel写出数据

#### ChannelOption,ChannelConfig

- ChannelOption是提供一些常量配置项

- Attribute 可以在Channel中传递业务k-v

- ChannelConfig是配置具体的Channel配置

#### ChannelHandlerContext

- 包含了ChannelHandler对象，实际上ChannelPipeline维护的就是ChannelHanlderContext的双向链表


Channel
ChannelPipeline : Channel创建的时候会创建ChannelPipeline.这两是一一对应的
ChannelHandlerContext : ChannelPipeline包含ChannelHandlerContext的双向链表
ChannelHandler ： ChannelHandlerContext包含ChannelHandler
ChannelInitializer: 用来批量注册ChannelHandler，执行时机是在Channel创建的时候就会回调  


Channel注册到哪儿去了？如何注册


#### Netty线程模型

- EventLoopGroup继承了EventExecutorGroup继承了ScheduledExecutorService
    
- EventLoop

1.一个EventLoopGroup包含多个EventLoop
2.一个EventLoop只包含一个Thread
3.EventLoop上面绑定的所有IO事件都通过其中的线程执行
4.一个Channel生命周期只会注册到一个EventLoop上
5.一个EventLoop会被分配给一个或多个Channel 对应底层Nio一个Selector对应多个Channel

这样做一个Channel的IO过程完全是单线程的，很好的避免了多线程并发

#### DirectBuffer 和 HeadBuffer

DirectByteBuffer中有一个long类型的`address`字段来引用native堆中的引用，就可以直接进行io操作

HeapByteBuffer在write的时候则需要将原来HeapBuffer中的数据拷贝到一个临时的DirectByteBuffer中，然后再去操作IO

为什么和底层IO交互的时候必须使用directByteBuffer?
- 因为调用底层io的系统层面读写需要传入的byte[]是内存稳定的，而HeapByteBuffer在Java堆中，会因为GC而移动地址引用会导致io读写失败



#### Netty启动流程


1.server启动 , 初始化main,sub两个线程组, selector等待客户端线程连接, 以及可动态调整处理io读写的ByteBuffer产生器 , 会初始化ServerSocketChannle并注册到Selector中阻塞等待客户端连接


2. 初始化NioServerSocketChannle的时候,设置了该Channel的一些属性,比如感兴趣的SelectionKey,非阻塞等. 并且初始化了ChannelPipeline , 在初始化ChannelPipelin的时候,会初始化里面的ChannelHandlerContext双向链表(TailContext,HeadContext) , 最后初始化了可以获取可动态扩展的ByteBufAllocate

3. 在init()初始化Channel的最后给pipeline.addLast()了一个ChannelInitializer , 

------- 下面的会在完成注册的时候进行回调-----------------------------

	- 这个组件的功能就是在对象的Handler完成注册(重点)然后回调HandlerAdded()方法中会回调其中的initialize()这时他会将我们配置的handler()方法的ChannelHandler添加到ChannelPipeline中 

	- 同时将获取当前Channel所在的事件循环对象添加一个异步线程(Accepter)在handlerRead的时候将原来在Main Reactor(AbstractBootstrap中)创建的ServerSocketChannel交给Sub Reactor(ServerBootstrap)去执行read操作


4. 从Main Reactor中使用ThreadChoose获取一个事件循环对象开始注册SingleThreadEventLoop#regiter(FutureProsime futurePromise) , 使用该事件循环对象和Channel对象构造一个FuturePromise开始调用在AbstraceUnsafe#register方法

	- eventLoop.inEventLoop() 来判断该EventLoop是否是当前Channel所在线程来避免多线程访问一个Channel造成的读写并发问题
	- 进入到AbstractChannel#register0方法,里面会将Channel注册到对应EventLoop中的Selector上
	- 顺序回调PendingHandlerCallback的execute方法,从而回调初始化过程的ChannelInitialze方法添加自定义的业务处理器和启动Acceptor,其中我们自定义的ChannelInitializer会在addLast的时候立即按添加顺序得到执行,因为此时已经在注册状态 , 所有的Handler的HandlerAdded方法会得到回调
	- 调用safeSetSuccess()方法使用FutureProsime的可写Future特性,且通过CAS的方式来通知所有的FutureListener注册已经完成
	- 后面就是一系列ChannelHandler的回调


5. 最后调用FuturePromise的方法进行阻塞等待服务器的启动完成
