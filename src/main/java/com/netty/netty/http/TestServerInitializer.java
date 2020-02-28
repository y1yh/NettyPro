package com.netty.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName TestServerInitializer
 * @Description TODO
 * @date 2020/2/19 19:58
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		//得到管道
		ChannelPipeline pipeline = ch.pipeline();
		
		//加入一个netty提供的 HttpServerCodec codec => [coder + decoder]
		//1. HttpServerCodec 是netty 提供的处理http的 编-解码器
		pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
		
		//2.增加一个自定义的handler
		pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());
		
		System.out.println("ok");
	}
}
