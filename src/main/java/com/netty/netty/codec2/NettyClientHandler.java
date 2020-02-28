package com.netty.netty.codec2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName NettyClientHandler
 * @Description TODO
 * @date 2020/2/22 15:20
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
	//当通道就绪就会触发该方法
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		int random = new Random().nextInt(3);
		
		MyDataInfo.MyMessage myMessage;
		if (0 == random) {
			myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.StudentType).setStudent(MyDataInfo.Student.newBuilder().setId(1).setName("李四").build()).build();
		} else {
			myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.WorkerType).setWorker(MyDataInfo.Worker.newBuilder().setAge(31).setName("工人").build()).build();
		}
		ctx.writeAndFlush(myMessage);
	}

	//当通道有读取事件时，会触发
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		ByteBuf buf = (ByteBuf) msg;
		System.out.println("服务器回复的消息:" + buf.toString(CharsetUtil.UTF_8));
		System.out.println("服务器的地址： "+ ctx.channel().remoteAddress());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
}
