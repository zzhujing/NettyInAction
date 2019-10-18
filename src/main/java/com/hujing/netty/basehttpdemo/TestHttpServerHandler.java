package com.hujing.netty.basehttpdemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author hujing
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {


    private static final String FAVICON_PATH = "/favicon.ico";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        System.out.println(ctx.channel().remoteAddress());

        TimeUnit.SECONDS.sleep(8);

        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            System.out.println("request http method : " + httpRequest.method().name());
            String uri = httpRequest.uri();
            if (FAVICON_PATH.equals(uri)) {
                System.out.println("request uri :" + uri);
                return;
            }
            //definition return value
            ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            //add response
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            //add header
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            ctx.writeAndFlush(response);

            //此时需要根据http的请求类别来关闭channel,若不关闭则服务器段不会立刻关闭channel
            ctx.channel().close();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel read completable");
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channel read");
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel registry");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channle active");
        super.channelActive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel unregistry");
        super.channelUnregistered(ctx);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel inactive");
        super.channelInactive(ctx);
    }
}
