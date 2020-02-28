package com.netty.nio;

import java.nio.ByteBuffer;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName ReadOnlyBuffer
 * @Description TODO
 * @date 2020/2/16 12:20
 */
public class ReadOnlyBuffer {
	public static void main(String[] args) {
		//创建一个buffer
		ByteBuffer byteBuffer = ByteBuffer.allocate(64);

		for (int i = 0; i < 64; i++) {
			byteBuffer.put((byte) i);
		}
		
		//读取
		byteBuffer.flip();

		//得到一个只读的Buffer
		ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
		System.out.println(readOnlyBuffer.getClass());
		
		//读取
		while (readOnlyBuffer.hasRemaining()) {
			System.out.println(readOnlyBuffer.get());
		}
		
		//Exception in thread "main" java.nio.ReadOnlyBufferException
		readOnlyBuffer.put((byte) 100); //ReadOnlyBufferException
	}
}
