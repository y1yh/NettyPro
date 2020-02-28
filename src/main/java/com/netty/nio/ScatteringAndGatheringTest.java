package com.netty.nio;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName ScatteringAndGatheringTest
 * @Description TODO
 * @date 2020/2/16 16:11
 */

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 	Scattering:	将数据写入buffer时，可以采用buffer数组，依次写入	[分散]
 * 	Gathering：	从buffer读取数据时，可以采用buffer数组，依次读取	[聚合]
 */
public class ScatteringAndGatheringTest {
	public static void main(String[] args) throws Exception{
		
		//使用ServerSocketChannel 和SocketChannel网络
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
		
		//绑定端口到socket，并启动
		serverSocketChannel.socket().bind(inetSocketAddress);
		
		//创建buffer数组
		ByteBuffer[] byteBuffers = new ByteBuffer[2];
		//分别初始化
		byteBuffers[0] = ByteBuffer.allocate(5);
		byteBuffers[1] = ByteBuffer.allocate(3);
		
		//等待连接
		SocketChannel socketChannel = serverSocketChannel.accept();
		int messageLength = 8;//假定从客户端接收8个字节
		while (true) {
			int byteRead = 0;
			
			while (byteRead < messageLength) {
				long read = socketChannel.read(byteBuffers);
				byteRead += read;//累计读取的字节数

				System.out.println("byteRead= " + byteRead);
				//使用流打印, 看看当前的这个buffer的position 和 limit
				Arrays.stream(byteBuffers).map(buffer -> "position= " + buffer.position() + ", limit= " + buffer.limit()).forEach(System.out::println);
			}
			Arrays.asList(byteBuffers).forEach(Buffer::flip);
			//将数据读出显示到客户端
			long byteWrite = 0;
			while (byteWrite < messageLength) {
				long write = socketChannel.write(byteBuffers);
				byteWrite += write;
			}
			
			//将所有的buffer进行clear
			Arrays.asList(byteBuffers).forEach(Buffer::clear);
			
			System.out.println("byteRead:=" + byteRead + " byteWrite=" + byteWrite + ", messageLength=" + messageLength);
		}
	}
}
