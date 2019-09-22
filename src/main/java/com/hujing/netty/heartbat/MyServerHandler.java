package com.hujing.netty.heartbat;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author hujing
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;

            String eventType = null;

            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    eventType = "读异常";
                    break;
                case WRITER_IDLE:
                    eventType = "写异常";
                    break;
                case ALL_IDLE:
                    eventType = "读写异常";
                    break;
                default:
                    break;
            }

            System.out.println(ctx.channel().remoteAddress() + " , " + eventType);
            ctx.close();
        }
    }
}
