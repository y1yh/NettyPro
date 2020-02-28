package com.netty.nio;

import javax.swing.event.TreeWillExpandListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName NIOFileChannel03
 * @Description TODO
 * @date 2020/2/16 10:51
 */
public class NIOFileChannel03 {

	public static void main(String[] args) throws Exception{
		File file = new File("e://file01.txt");
		FileInputStream fileInputStream = new FileInputStream(file);
		FileChannel inputStreamChannel = fileInputStream.getChannel();
		
		FileOutputStream  fileOutputStream = new FileOutputStream("out.txt");
		FileChannel outputStreamChannel = fileOutputStream.getChannel();

//		ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());
//		inputStreamChannel.read(byteBuffer);
//
//		byteBuffer.flip();
//		outputStreamChannel.write(byteBuffer);
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(512);
		while (true) { //循环读取
			//这里有一个重要的操作，一定不要忘了
			/**
			 * public final Buffer clear() {
			 *         position = 0;
			 *         limit = capacity;
			 *         mark = -1;
			 *         return this;
			 * }
			 */
			byteBuffer.clear();//清空buffer
			
			int read = inputStreamChannel.read(byteBuffer);
			if (read == -1) {//表示已经读完
				break;
			}
			byteBuffer.flip();
			//将buffer中的数据写入到 outputStreamChannel -> out.txt
			outputStreamChannel.write(byteBuffer);
		}
		
		fileInputStream.close();
		fileOutputStream.close();
		
		fileInputStream.close();
		fileOutputStream.close();
	}
}
