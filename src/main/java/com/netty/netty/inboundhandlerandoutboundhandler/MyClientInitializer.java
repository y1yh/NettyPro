package com.netty.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MyClientInitializer
 * @Description TODO
 * @date 2020/2/23 14:20
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {


	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		//加入出站的handler对数据进行编码
		pipeline.addLast(new MyLongToByteEncoder());
		//加入入站的编码器(入站的handler)
//		pipeline.addLast(new MyByteToLongDecoder());
		pipeline.addLast(new MyByteToLongDecoder2());
		//加入一个自定义的 handler, 处理业务
		pipeline.addLast(new MyClientHandler());
	}
}
