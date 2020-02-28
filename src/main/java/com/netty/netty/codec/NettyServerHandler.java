package com.netty.netty.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName NettyServerHandler
 * @Description TODO
 * @date 2020/2/22 15:07
 */
//public class NettyServerHandler extends ChannelInboundHandlerAdapter {
public class NettyServerHandler extends SimpleChannelInboundHandler<StudentPOJO.Student> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, StudentPOJO.Student msg) throws Exception {
		System.out.println("客户端发送的数据 id = " + msg.getId() + ", 名字 = " + msg.getName());
	}
	
//	@Override
//	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		//读取从客户端发送的 StudentPojo.Student
//		StudentPOJO.Student student = (StudentPOJO.Student) msg;
//
//		System.out.println("客户端发送的数据 id = " + student.getId() + ", 名字 = " + student.getName());
//	}

	//数据读取完毕
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//writeAndFlush 是 write + flush
		//将数据写入到缓存，并刷新
		//一般讲，我们对这个发送的数据进行编码
		ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<)喵1", CharsetUtil.UTF_8));
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
