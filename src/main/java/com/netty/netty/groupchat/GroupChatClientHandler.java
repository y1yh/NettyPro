package com.netty.netty.groupchat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName GroupChatClientHandler
 * @Description TODO
 * @date 2020/2/20 20:17
 */
public class GroupChatClientHandler extends SimpleChannelInboundHandler<String> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println(msg.trim());
	}
}
