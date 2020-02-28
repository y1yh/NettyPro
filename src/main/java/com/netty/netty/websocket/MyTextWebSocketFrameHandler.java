package com.netty.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MyTextWebSocketFrameHandler
 * @Description TODO
 * @date 2020/2/21 19:06
 */
public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		System.out.println("服务器收到消息 " + msg.text());
		
		//回复消息
		ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间" + LocalDateTime.now() + " " + msg.text()));
	}
	//web客户端连接后, 会触发方法
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// id 表示唯一的值  LongText是唯一的 ShortText不是唯一的
		System.out.println("handlerAdded 被调用 " + ctx.channel().id().asLongText());
		System.out.println("handlerAdded 被调用 " + ctx.channel().id().asShortText());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerRemoved 被调用 " + ctx.channel().id().asLongText());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("发生异常 " + cause.getMessage());
		//关闭连接
		ctx.close();
	}
}
