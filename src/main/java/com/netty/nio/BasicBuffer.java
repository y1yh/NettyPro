package com.netty.nio;

import java.nio.Buffer;
import java.nio.IntBuffer;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName BasicBuffer
 * @Description 缓冲区
 * @date 2020/2/14 17:50
 */
public class BasicBuffer {
	public static void main(String[] args) {
		//举例说明buffer的使用(简单说明).
		//创建一个buffer, 大小为5, 即可以存放5个Int
		IntBuffer intBuffer = IntBuffer.allocate(5);
		
		//buffer 存放数据
//		intBuffer.put(10);
//		intBuffer.put(11);
//		intBuffer.put(12);
//		intBuffer.put(13);
//		intBuffer.put(14);

		for (int i = 0; i < intBuffer.capacity(); i++) {
			intBuffer.put(i * 2);
		}
		//如何从buffer读取数据
		//将buffer转换, 读写切换(!!!)
		intBuffer.flip();
//		public final Buffer flip() {
//			limit = position;
//			position = 0;
//			mark = -1;
//			return this;
//		}
		while (intBuffer.hasRemaining()) {
			System.out.println(intBuffer.get());
		}
	}
}
