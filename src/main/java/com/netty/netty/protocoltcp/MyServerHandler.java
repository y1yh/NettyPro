package com.netty.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MyServerHandler
 * @Description TODO
 * @date 2020/2/23 22:47
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

	private int count;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
		//接收数据,并处理
		int len = msg.getLen();
		byte[] content = msg.getContent();
		
		System.out.println();
		System.out.println("服务器接收消息如下：");
		System.out.println("长度 = " + len);
		System.out.println("内容 = " + new String(content, CharsetUtil.UTF_8));
		System.out.println("服务器接收到消息包数量 = " + (++this.count));
		System.out.println();
		
		//回复消息
		String responseContent = UUID.randomUUID().toString();
		int resLen = responseContent.getBytes(CharsetUtil.UTF_8).length;
		byte[] responseContentBytes = responseContent.getBytes(CharsetUtil.UTF_8);
		
		//构建一个协议包
		MessageProtocol messageProtocol = new MessageProtocol();
		messageProtocol.setLen(resLen);
		messageProtocol.setContent(responseContentBytes);
		ctx.writeAndFlush(messageProtocol);


	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
