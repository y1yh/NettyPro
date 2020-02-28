package com.netty.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName NettyClientHandler
 * @Description TODO
 * @date 2020/2/26 21:26
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {
	
	private ChannelHandlerContext context;//上下文
	private String result;	//返回的结果
	private String param;	//客户端调用时传入的参数

	//与服务器的连接创建后，就会被调用，这个方法是第一个被调用的 (1)
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive 被调用");
		context = ctx;//因为其他方法会用到context
	}

	//收到服务器数据后，调用方法 (4)
	@Override
	public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("channelRead 被调用");
		result = msg.toString();
		notify();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	//被代理对象调用，发送数据给服务器 -> wait -> 等待被唤醒(channelRead) -> 返回结果(3) (5)
	@Override
	public synchronized Object call() throws Exception {
		System.out.println("call1 被调用");
		context.writeAndFlush(param);
		//进行wait等待服务器返回
		wait();
		System.out.println("call2 被调用");
		return result;
	}
	
	//(2)
	void setParam(String param) {
		System.out.println("setParam 被调用");
		this.param = param;
	}
}
