package com.netty.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MyMessageEncoder
 * @Description TODO
 * @date 2020/2/23 23:55
 */
public class MyMessageEncoder extends MessageToByteEncoder<MessageProtocol> {
	@Override
	protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
		System.out.println("MyMessageEncoder encode 方法被调用");
		
		out.writeInt(msg.getLen());
		out.writeBytes(msg.getContent());
	}
}
