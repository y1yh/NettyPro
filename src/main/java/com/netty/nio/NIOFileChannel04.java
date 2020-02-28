package com.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName NIOFileChannel04
 * @Description TODO
 * @date 2020/2/16 11:42
 */
public class NIOFileChannel04 {
	public static void main(String[] args) throws Exception{
		//创建相关流
		FileInputStream fileInputStream = new FileInputStream("e://a.jpeg");
		FileOutputStream fileOutputStream = new FileOutputStream("e://a2.jpeg");

		//获取各个流对应的fileChannel
		FileChannel sourceChannel = fileInputStream.getChannel();
		FileChannel destChannel = fileOutputStream.getChannel();
		
		//使用 transferFrom 完成拷贝
		destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
		
		//关闭相关通道和流
		sourceChannel.close();
		destChannel.close();
		
		fileInputStream.close();
		fileOutputStream.close();
	}
}
