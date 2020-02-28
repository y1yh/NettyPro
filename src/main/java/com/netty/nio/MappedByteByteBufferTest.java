package com.netty.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MappedByteByteBufferTest
 * @Description 说明：MappedByteByteBuffer 可让文件直接在内存(堆外内存)修改,操作系统不需要拷贝一次
 * @date 2020/2/16 15:06
 */
public class MappedByteByteBufferTest {
	public static void main(String[] args) throws Exception{
		RandomAccessFile randomAccessFile = new RandomAccessFile("out.txt", "rw");

		FileChannel channel = randomAccessFile.getChannel();

		/**
		 * 参数1：FileChannel.MapMode.READ_WRITE 使用的读写模式
		 * 参数2：0	：可以直接修改的起始位置
		 * 参数3：5	：是映射到内存的大小(不是索引位置),将out.txt的多少个字节映射到内存 可以直接修改的范围是0-5
		 * 	实际类型 DirectByteBuffer
		 */
		MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

		mappedByteBuffer.put(0, (byte)'H');
		mappedByteBuffer.put(3, (byte)'9');
		//mappedByteBuffer.put(5, (byte)'Y');//java.lang.IndexOutOfBoundsException
		
		randomAccessFile.close();
		System.out.println("修改成功~");
	}
}
