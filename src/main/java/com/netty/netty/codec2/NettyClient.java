package com.netty.netty.codec2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName NettyClient
 * @Description TODO
 * @date 2020/2/22 15:11
 */
public class NettyClient {
	public static void main(String[] args) throws Exception{
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {

			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							//在 pipeline 加入 ProtobufEncoder 编码
							pipeline.addLast("encoder", new ProtobufEncoder());
							//加入自己的处理器
							pipeline.addLast(new NettyClientHandler());
						}
					});
			//启动客户端去连接服务器端
			//关于 ChannelFuture 要分析，涉及到netty的异步模型
			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6669).sync();
			//给关闭通道进行监听
			channelFuture.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
}
