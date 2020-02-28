package com.netty.netty.inboundhandlerandoutboundhandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MyClient
 * @Description TODO
 * @date 2020/2/23 14:17
 */
public class MyClient {

	public static void main(String[] args) throws Exception{
		NioEventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class)
					.handler(new MyClientInitializer());

			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 7000).sync();
			
			//监听关闭
			channelFuture.channel().closeFuture().sync();

		} finally {
			group.shutdownGracefully();
		}
	}
}