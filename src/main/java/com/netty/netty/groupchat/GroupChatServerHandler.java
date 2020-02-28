package com.netty.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName GroupChatServerHandler
 * @Description TODO
 * @date 2020/2/20 17:33
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

	//public static List<Channel> channels = new ArrayList<>();
	// 使用一个hashMap管理 私聊使用
	//public static Map<String, Channel> channelMap = new HashMap<>();
	
	/**
	 * 定义一个channel组,管理所有的channel
	 * 	GlobalEventExecutor.INSTANCE 是全局的事件执行器，是一个单例
	 */
	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	/**
	 * handlerAdded 表示连接建立,一旦连接，第一个被执行
	 * 	将当前的channel加入到channelGroup
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		//将该客户加入聊天的信息推送给其他在线的客户端
		
		//channelGroup.writeAndFlush 会将其中的所有channel遍历, 并发送消息, 我们就不需要自己遍历
		channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "加入聊天\n");
		channelGroup.add(channel);
		//channelMap.put(channel.remoteAddress().toString().split(":")[1], channel);
	}

	/**
	 * 断开连接,将xx客户离开信息推送给当前在线的客户端
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		
		//客户端断开连接 channelGroup会自动删除此channel
		channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "离开了\n");
		System.out.println("channelGroup size = " + channelGroup.size());
	}

	/**
	 * 表示channel处于活动状态, 提示xx上线
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + "上线了");
	}

	/**
	 *	表示channel处于不活动状态, 提示 xx 离线了
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + "离线了");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		//关闭通道
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//		String[] key = msg.split(":");
//		if (channelMap.containsKey(key[0])) {
//			//私聊
//			Channel channel = channelMap.get(key[0]);
//			channel.writeAndFlush("[客户]" + channel.remoteAddress() + " 私聊消息: " + key[1] + "\n");
//		} else {
//			//获取到当前channel
//			Channel channel = ctx.channel();
//			//这时我们遍历channelGroup,根据不同的情况回复不同的消息
//			channelGroup.forEach(ch -> {
//				if (channel != ch) {
//					ch.writeAndFlush("[客户]" + ch.remoteAddress() + " 发送了消息: " + msg + "\n");
//				} else {
//					ch.writeAndFlush("[自己] 发送了消息" + msg + "\n");
//				}
//			});
//		}
		//获取到当前channel
		Channel channel = ctx.channel();
		//这时我们遍历channelGroup,根据不同的情况回复不同的消息
		channelGroup.forEach(ch -> {
			if (channel != ch) {
				ch.writeAndFlush("[客户]" + ch.remoteAddress() + " 发送了消息: " + msg + "\n");
			} else {
				ch.writeAndFlush("[自己] 发送了消息" + msg + "\n");
			}
		});
	}
}
