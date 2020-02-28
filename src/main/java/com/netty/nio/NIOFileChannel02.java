package com.netty.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName NIOFileChannel02
 * @Description TODO
 * @date 2020/2/16 10:42
 */
public class NIOFileChannel02 {
	public static void main(String[] args) throws Exception {
		//创建文件的输入流
		File file = new File("e://file01.txt");
		FileInputStream fileInputStream = new FileInputStream(file);

		//通过 FileInputStream 获取对应的 FileChannel 实际类型->FileChannelImpl
		FileChannel fileChannel = fileInputStream.getChannel();

		//创建缓冲区
		ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());
		
		//将通道的数据写入buffer
		fileChannel.read(byteBuffer);

		fileInputStream.close();
		//将byteBuffer字节数据 转化成字符串
		System.out.println(new String(byteBuffer.array()));
		
	}
}
