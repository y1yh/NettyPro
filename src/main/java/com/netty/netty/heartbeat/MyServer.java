package com.netty.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MyServer
 * @Description TODO
 * @date 2020/2/20 22:50
 */
public class MyServer {
	public static void main(String[] args) throws InterruptedException {
		//创建两个线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) {
							//获取 pipeline
							ChannelPipeline pipeline = ch.pipeline();
							/*
							 *  加入一个 netty提供的 IdleStateHandler
							 *  说明 ：IdleStateHandler(long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit)
							 *  	1. IdleStateHandler是netty提供的处理空闲状态的处理器
							 *  	2. readerIdleTime:	表示多长时间进行没有读,就会发送一个心跳检测包检测是否连接
							 *  	3. writerIdleTime:	表示多长时间进行没有读,就会发送一个心跳检测包检测是否连接
							 *  	4. allIdleTime	:	表示多长时间进行没有读/写,就会发送一个心跳检测包检测是否连接
							 *  	5. 文档说明：Triggers an {@link IdleStateEvent} when a {@link Channel} has not performed read, 
							 *  				write, or both operation for a while.
							 *  	6. 当 IdleStateEvent 触发后,就会传递给管道的下一个handler去处理 通过调用(触发)下一个handler的
							 *  		userEventTriggered方法,在该方法中处理 IdleStateEvent(读空闲、写空闲、读写空闲) 	
							 */
							pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
							// 加入一个对空闲检测进一步处理的handler(自定义)
							pipeline.addLast(new MyServerHandler());
						}
					});
			//启动服务器
			ChannelFuture channelFuture = bootstrap.bind(7000).sync();
			//监听关闭
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
