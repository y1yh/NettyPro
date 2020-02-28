package com.netty.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MyClientHandler
 * @Description TODO
 * @date 2020/2/23 23:05
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

	private int count;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 使用客户端发送10条数据 "今天天气冷,吃火锅" 编号
		String mes = "今天天气冷,吃火锅";
		byte[] content = mes.getBytes(CharsetUtil.UTF_8);
		int length = content.length;
		
		for (int i = 0; i < 5; i++) {
			//构建协议包对象
			MessageProtocol messageProtocol = new MessageProtocol();
			messageProtocol.setLen(length);
			messageProtocol.setContent(content);
			ctx.writeAndFlush(messageProtocol);
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
		int len = msg.getLen();
		byte[] content = msg.getContent();

		System.out.println();
		System.out.println("客户端接收消息如下：");
		System.out.println("长度 = " + len);
		System.out.println("内容 = " + new String(content, CharsetUtil.UTF_8));
		System.out.println("客户端接收消息数量 = " + (++this.count));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
