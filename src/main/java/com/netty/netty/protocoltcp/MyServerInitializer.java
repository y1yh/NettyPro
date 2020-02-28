package com.netty.netty.protocoltcp;

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
		
		pipeline.addLast(new MyMessageDecoder());// 解码器
		pipeline.addLast(new MyMessageEncoder());// 编码器
		
		pipeline.addLast(new MyServerHandler());
	}
}
