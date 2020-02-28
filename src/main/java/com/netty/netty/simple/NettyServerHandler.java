package com.netty.netty.simple;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName NettyServerHandler
 * @Description 
 * 		说明：
 * 			1.我们自定义一个Handler 需要继承 netty 规定好的HandlerAdapter
 * 			2.这时我们自定义一个Handler才能成为一个handler	
 * @date 2020/2/18 22:04
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
	/**
	 *	读取数据事件(这里我们可以读取客户端发送的消息)
	 *	1.	ChannelHandlerContext ctx:上下文对象，含有管道pipeline, 通道channel, 地址等信息
	 *	2.	Object msg：就是客户端发送的数据 默认Object
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		//比如这里我们有一个非常耗时的业务--> 异步执行 -> 提交到该channel 对应的NIOEventLoop 的taskQueue中
		
		//解决方案1 用户程序自定义的普通任务

		ctx.channel().eventLoop().execute(() -> {
			try {
				Thread.sleep(5 * 1000);
				ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<)喵2", CharsetUtil.UTF_8));
			}catch (Exception ex) {
				System.out.println("发生异常 ：" + ex.getMessage());
			}
		});
		ctx.channel().eventLoop().execute(() -> {
			try {
				Thread.sleep(10 * 1000);
				//此时第二个任务必须等待第一个完成才执行 所以实际上需要等待15秒
				ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<)喵3", CharsetUtil.UTF_8));
			}catch (Exception ex) {
				System.out.println("发生异常 ：" + ex.getMessage());
			}
		});
		
		// 2. 用户自定义定时任务 -> 该任务是提交到scheduleTaskQueue中
		ctx.channel().eventLoop().schedule(() -> {
			ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<)喵4", CharsetUtil.UTF_8));
		}, 5, TimeUnit.SECONDS);
		
//		System.out.println("服务器读取线程 " + Thread.currentThread().getName() + "  ,channel = " + ctx.channel());
//		System.out.println("server ctx = " + ctx);
//		System.out.println("看看channel 和 pipeline的关系");
//
		Channel channel = ctx.channel();
//		ChannelPipeline pipeline = ctx.pipeline(); //本质是一个双向链表,出栈入栈
//		
//		//将msg 转化成一个 ByteBuf
//		//ByteBuf是netty提供的,不是 NIO 的 ByteBuffer.
//		ByteBuf byteBuf = (ByteBuf) msg;
//
//		System.out.println("客户端端发送的消息是：" + byteBuf.toString(CharsetUtil.UTF_8));
		System.out.println("客户端地址:" + channel.remoteAddress());
	}

	/**
	 *	数据读取完毕
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// writeAndFlush 是write + flush
		// 将数据写入到缓存并刷新
		// 一般来讲，我们对这个发送的数据进行编码
		ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<)喵1", CharsetUtil.UTF_8));
	}

	/**
	 *	处理异常，一般是需要关闭通道
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
