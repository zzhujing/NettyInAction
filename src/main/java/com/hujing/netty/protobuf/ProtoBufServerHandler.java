package com.hujing.netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author : hujing
 * @date : 19-9-19
 */
public class ProtoBufServerHandler extends SimpleChannelInboundHandler<DateInfo.OneOfAll> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DateInfo.OneOfAll msg) throws Exception {
        switch (msg.getDateType()) {
            case EMP:
                System.out.println(msg.getEmployee());
                break;
            case PERSON:
                System.out.println(msg.getPerson());
                break;
            default:
                break;
        }
    }
}
