package com.netty.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MyServer
 * @Description TODO
 * @date 2020/2/21 18:27
 */
public class MyServer {

	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {

			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							//因为基于http协议, 使用http的编码和解码器
							pipeline.addLast(new HttpServerCodec());
							//是以块方式写，添加 ChunkedWriteHandler 处理器
							pipeline.addLast(new ChunkedWriteHandler());

							/*
							 * 说明	
							 * 	1.http数据在传输过程中是分段, HttpObjectAggregator 就是可以将多个段聚合
							 * 	2. 这就是为什么当浏览器发送大量数据时, 就会发出多次http请求
							 */
							pipeline.addLast(new HttpObjectAggregator(8192));
							/*
							 *	说明
							 * 		1.对应webSocket, 它的数据是以 帧(frame) 形式传递
							 * 		2.可以看到 WebSocketFrame 下面6个子类
							 * 		3.浏览器请求时 ws://localhost:7000/hello 表示请求的uri
							 * 		4.WebSocketServerProtocolHandler核心功能是将http协议升级为ws协议,保持长连接
							 * 		5.是通过一个状态码 101
							 */
							pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
							//自定义的handler, 处理业务逻辑
							pipeline.addLast(new MyTextWebSocketFrameHandler());
						}
					});
			ChannelFuture channelFuture = bootstrap.bind(7000).sync();
			//监听关闭
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
	}
}
