package com.netty.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MyByteToLongDecoder2
 * @Description TODO
 * @date 2020/2/23 16:36
 */
public class MyByteToLongDecoder2 extends /*ByteToMessageDecoder*/ ReplayingDecoder<Void> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		System.out.println("MyByteToLongDecoder2 被调用");
		//在 ReplayingDecoder 不需要判断数据是否足够读取，内部会进行处理判断
//		if (in.readableBytes() >= 8) {
//			out.add(in.readLong());
//		}
		out.add(in.readLong());
	}
}
