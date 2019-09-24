package com.hujing.netty.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

import static com.hujing.netty.protobuf.DateInfo.OneOfAll.DataType.*;

/**
 * @author : hujing
 * @date : 19-9-19
 */
public class ProtoBufClientHandler extends SimpleChannelInboundHandler<DateInfo.OneOfAll> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int random = new Random().nextInt(2);
        DateInfo.OneOfAll dateInfo = null;
        if (random == 0) {
            dateInfo = DateInfo.OneOfAll.newBuilder()
                    .setDateType(EMP)
                    .setEmployee(DateInfo.Employee.newBuilder()
                            .setAddress("hz")
                            .setAge(10)
                            .setUsername("emp")
                            .build())
                    .build();
        } else {
            dateInfo = DateInfo.OneOfAll.newBuilder()
                    .setDateType(PERSON)
                    .setPerson(DateInfo.Person.newBuilder()
                            .setAddress("jx")
                            .setAge(10)
                            .setUsername("person")
                            .build())
                    .setPerson(DateInfo.Person.newBuilder().build())
                    .build();
        }

        ctx.channel().writeAndFlush(dateInfo);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DateInfo.OneOfAll msg) throws Exception {
    }
}
