package com.netty.nio;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName NIOClient
 * @Description TODO
 * @date 2020/2/17 0:59
 */
public class NIOClientTest {
	public static void main(String[] args) throws Exception {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);

		InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

		if (!socketChannel.connect(inetSocketAddress)) {
			while (!socketChannel.finishConnect()) {
				System.out.println("连接中...可以做其他的事");
			}
		}

		String str = "哇哈哈";
		ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
		socketChannel.write(byteBuffer);
		System.in.read();
	}
}

