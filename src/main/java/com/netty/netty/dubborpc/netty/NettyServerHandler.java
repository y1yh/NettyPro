package com.netty.netty.dubborpc.netty;

import com.netty.netty.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName NettyServerHandler
 * @Description TODO
 * @date 2020/2/26 20:39
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//获取客户端发送的消息,并调用服务
		System.out.println("msg = " + msg);
		/*
		 * 客户端在调用服务器的api时，我们需要定义一个协议
		 * 	比如我们要求 每次发送消息都必须以某个字符串开头 例: "HelloService#hello#你好"
		 */
		if (msg.toString().startsWith("HelloService#hello#")) {
			String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
			ctx.writeAndFlush(result);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
