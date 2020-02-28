package com.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName NIOClient
 * @Description TODO
 * @date 2020/2/17 0:33
 */
public class NIOClient {
	public static void main(String[] args) throws Exception{
		//得到一个网络通道
		SocketChannel socketChannel = SocketChannel.open();
		//设置为非阻塞
		socketChannel.configureBlocking(false);

		//提供服务器端的端口和IP
		InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
		
		//连接服务器
		if (!socketChannel.connect(inetSocketAddress)) {
			while (!socketChannel.finishConnect()) {
				System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作");
			}
		}
		// 如果连接成功，就发送数据
		String str = "hello, NIO示例";
		// Wraps a byte array into a buffer
		ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
		//发送数据，将buffer数据写入到channel
		socketChannel.write(byteBuffer);
		System.in.read();
	}
}
