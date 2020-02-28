package com.netty.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MyByteToLongDecoder
 * @Description TODO
 * @date 2020/2/23 11:09
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
	/**
	 * 	decode 会根据接收的数据被调用多次，直到确定没有新的元素被添加到 List, 或者是ByteBuf没有更多的可读字节为止
	 * 	如果 List<Object> out 不为空，就会将list的内容传递给下一个 ChannelInBoundHandler 处理, 该处理器的方法也会被调用多次
	 * 	
	 * @param ctx 上下文对象
	 * @param in	入站的ByteBuf
	 * @param out	List集合,将解码后的数据传给下一个Handler
	 * @throws Exception
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		System.out.println("MyByteToLongDecoder 被调用");
		
		if (in.readableBytes() >= 8) {
			//因为long有8个字节，需要判断有8个字节，才能读取一个long
			out.add(in.readLong());
		}
	}
}
