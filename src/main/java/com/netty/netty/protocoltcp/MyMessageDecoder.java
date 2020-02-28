package com.netty.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MyMessageDecoder
 * @Description TODO
 * @date 2020/2/23 23:57
 */
public class MyMessageDecoder extends /*ByteToMessageDecoder*/ ReplayingDecoder<Void> {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		System.out.println("MyMessageDecoder decode 方法被调用");
		
		//需要将得到的二进制字节码 -> MessageProtocol 数据包(对象)
		int len = in.readInt();
		byte[] content = new byte[len];
		in.readBytes(content);
		
		//封装成 MessageProtocol 对象,放入out, 传递下一个handler业务处理

		MessageProtocol messageProtocol = new MessageProtocol();
		messageProtocol.setLen(len);
		messageProtocol.setContent(content);
		
		out.add(messageProtocol);
	}
}
