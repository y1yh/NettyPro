package com.netty.netty.dubborpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName NettyClient
 * @Description TODO
 * @date 2020/2/26 21:36
 */
public class NettyClient {

	//创建线程池
	private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private static NettyClientHandler client;

	private int count;

	//编写方法使用代理模式，获取一个代理对象
	public Object getBean(final Class<?> serviceClass, final String providerName) {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{serviceClass}, (proxy, method, args) -> {
			System.out.println("(proxy, method, args) 进入...." + (++count) + " 次");
			
			// {}  部分的代码，客户端每调用一次 hello, 就会进入到该代码
			if (client == null) {
				initClient();
			}
			/*
			 *	设置要发送给客户端的信息
			 * providerName 协议头  args[0] 就是客户端调用api hello(???), 参数
			 */
			client.setParam(providerName + args[0]);

			return executor.submit(client).get();
		});
	}

	private static void initClient() {
		client = new NettyClientHandler();

		EventLoopGroup group = new NioEventLoopGroup();
		try {

			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							pipeline.addLast(new StringDecoder());
							pipeline.addLast(new StringEncoder());
							pipeline.addLast(client);
						}
					});
			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 7000).sync();

			//记录：写了这句会出问题 具体原因待查验 
			//channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
