package com.netty.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName NettyByteBuf02
 * @Description TODO
 * @date 2020/2/20 14:28
 */
public class NettyByteBuf02 {
	public static void main(String[] args) {
		//创建ByteBuf
		ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", CharsetUtil.UTF_8);
		
		//使用相关的方法
		if (byteBuf.hasArray()) { //true
			byte[] content = byteBuf.array();

			//将 content转化为字符串
			System.out.println(new String(content, CharsetUtil.UTF_8));

			System.out.println("byteBuf = " + byteBuf);
			System.out.println(byteBuf.arrayOffset());// byteBuf中字节偏移量  0
			System.out.println(byteBuf.readerIndex()); // 0
			System.out.println(byteBuf.writerIndex());	//12
			System.out.println(byteBuf.capacity());		//36

			//System.out.println(byteBuf.readByte()); // 会改变readerIndex
			System.out.println(byteBuf.readBytes(0));	//不会改变readerIndex

			int len = byteBuf.readableBytes(); //实际可读的字节数 12
			System.out.println("len = " + len);
			
			//使用for取出各个字节
			for (int i = 0; i < len; i++) {
				System.out.println((char) byteBuf.getByte(i));
			}
			
			//按照某个范围读取
			System.out.println(byteBuf.getCharSequence(0, 4, CharsetUtil.UTF_8));
			System.out.println(byteBuf.getCharSequence(4, 6, CharsetUtil.UTF_8));
			
		}
	}
}
