package com.netty.netty.tcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MyServerInitializer
 * @Description TODO
 * @date 2020/2/23 22:45
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new MyServerHandler());
	}
}
