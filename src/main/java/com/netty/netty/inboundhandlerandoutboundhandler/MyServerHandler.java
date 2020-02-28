package com.netty.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MyServerHandler
 * @Description TODO
 * @date 2020/2/23 14:13
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
		System.out.println("从客户端" + ctx.channel().remoteAddress() + " 读取到Long " + msg);
		ChannelPipeline pipeline = ctx.pipeline();
		//给客户端发送一个long
		ctx.writeAndFlush(98765L);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
