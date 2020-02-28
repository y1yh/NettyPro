package com.netty.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName FileChannel01
 * @Description 本地文件写数据
 * @date 2020/2/16 0:57
 */
public class NIOFileChannel01 {
	public static void main(String[] args) throws Exception{
		String str = "hello,哇哈哈";

		//创建一个输出流 -> channel
		FileOutputStream outputStream = new FileOutputStream("e://file01.txt");
		
		//通过 FileOutputStream 获取对应的 FileChannel
		//这个 fileChannel 的真实类型是 FileChannelImpl
		FileChannel fileChannel = outputStream.getChannel();

		//创建一个缓冲区 ByteBuffer
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		// 将str放入到byteBuffer
		byteBuffer.put(str.getBytes());
		
		//对buffer进行 flip
		byteBuffer.flip();
		
		//将byteBuffer数据写入到fileChannel
		fileChannel.write(byteBuffer);
		outputStream.close();
	}
}
