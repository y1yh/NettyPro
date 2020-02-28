package com.netty.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MyServerHandler
 * @Description TODO
 * @date 2020/2/20 23:11
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
	/**
	 * 
	 * @param ctx 	上下文
	 * @param evt	事件
	 * @throws Exception
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			String eventType = null;
			switch (event.state()) {
				case READER_IDLE:
					eventType = "读空闲";
					break;
				case WRITER_IDLE:
					eventType = "写空闲";
					break;
				case ALL_IDLE:
					eventType = "读写空闲";
					break;
			}
			System.out.println(ctx.channel().remoteAddress() + "--超时时间--" + eventType);
			// TODO 服务器做响应的处理
			System.out.println("服务器做响应的处理");
		}
	}
}
