package com.netty.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MyLongToByteEncoder
 * @Description TODO
 * @date 2020/2/23 14:23
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {

	//编码方法
	@Override
	protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
		System.out.println("MyLongToByteEncoder encode 被调用");
		System.out.println("msg = " + msg);
		//具体根据业务需求实现
		out.writeLong(msg);
	}
}
