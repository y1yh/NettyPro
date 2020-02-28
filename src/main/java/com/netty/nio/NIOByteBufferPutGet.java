package com.netty.nio;

import java.nio.ByteBuffer;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName NIOByteBufferPutGet
 * @Description TODO
 * @date 2020/2/16 12:16
 */
public class NIOByteBufferPutGet {
	public static void main(String[] args) {
		//创建一个byteBuffer
		ByteBuffer byteBuffer = ByteBuffer.allocate(64);
		
		//类型化放入数据
		byteBuffer.putInt(100);
		byteBuffer.putLong(9);
		byteBuffer.putChar('尚');
		byteBuffer.putShort((short)4);
		
		//取出
		byteBuffer.flip();

		System.out.println(byteBuffer.getInt());
		System.out.println(byteBuffer.getLong());
		System.out.println(byteBuffer.getChar());
		System.out.println(byteBuffer.getShort());
	}
}
