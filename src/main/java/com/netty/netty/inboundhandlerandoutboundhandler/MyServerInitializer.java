package com.netty.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MyServerInitializer
 * @Description TODO
 * @date 2020/2/23 11:05
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		//得到管道
		ChannelPipeline pipeline = ch.pipeline();
		//入站的handler进行解码 MyByteToLongDecoder
//		pipeline.addLast(new MyByteToLongDecoder());
		pipeline.addLast(new MyByteToLongDecoder2());
		//出站的handler进行编码
		pipeline.addLast(new MyLongToByteEncoder());
		//加入自定义的handler处理业务逻辑
		pipeline.addLast(new MyServerHandler());
		
	}
}
