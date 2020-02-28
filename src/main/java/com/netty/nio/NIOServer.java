package com.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName NIOServer
 * @Description TODO
 * @date 2020/2/16 23:54
 */
public class NIOServer {
	public static void main(String[] args) throws Exception{

		// 创建 ServerSocketChannel -> SocketChannel
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		// 得到一个Selector对象
		Selector selector = Selector.open();
		
		//绑定一个端口 6666 , 在服务器监听
		serverSocketChannel.socket().bind(new InetSocketAddress(6666));
		//设置为非阻塞
		serverSocketChannel.configureBlocking(false);
		
		//把 serverSocketChannel 注册到 selector 关心为 OP_ACCEPT 事件。
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		System.out.println("注册后selectionKey数量=" + selector.keys().size());
		
		// 循环等待客户端连接
		while (true) {
			//我们这里等待1秒,如果没有事件发生，返回
			if (selector.select(1000) == 0) { //没有事件
				System.out.println("服务器等待了1秒,无连接");
				continue;
			}

			/**
			 * 如果返回的 > 0, 就获取到相关的 selectionKey集合
			 * 1. 如果返回的 > 0, 表示已经获取到关注的事件
			 * 2. selector.selectedKeys() 返回关注的事件集合
			 * 		通过 selectedKeys 反向获取通道
 			 */
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			System.out.println("selectionKeys 数量=" + selectionKeys);

			//遍历 Set<SelectionKey> ,使用迭代器遍历
			Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
			
			while (keyIterator.hasNext()) {
				// 获取到 SelectionKey
				SelectionKey key = keyIterator.next();
				// 根据key 对应的通道发生的事件做相应的处理
				if (key.isAcceptable()) { //如果时OP_ACCEPT,有新的客户端连接
					// 该客户端生成一个 SocketChannel
					SocketChannel socketChannel = serverSocketChannel.accept();
					System.out.println("客户端连接成功 生成一个 socketChannel " + socketChannel.hashCode());
					
					//将 SocketChannel 设置为非阻塞
					socketChannel.configureBlocking(false);
					
					//将 socketChannel 注册到selector,关注事件为 OP_READ,同时给 socketChannel 关联一个buffer
					socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

					System.out.println("客户端连接后, 注册 selectionKey 数量=" + selector.keys().size());
				}
				
				if (key.isReadable()) { //OP_READ
					//通过key 反向获取到对应的channel
					SocketChannel channel = (SocketChannel) key.channel();
					//获取到该channel关联的buffer
					ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
					channel.read(byteBuffer);
					System.out.println("from 客户端" + new String(byteBuffer.array()));
				}
				//手动从集合中移出当前的selectionKey,防止重复
				keyIterator.remove();
			}
		}
	}
}
