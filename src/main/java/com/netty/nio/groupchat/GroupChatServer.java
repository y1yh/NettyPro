package com.netty.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName GroupChatServer
 * @Description TODO
 * @date 2020/2/17 17:00
 */
public class GroupChatServer {
	//定义属性
	private Selector selector;
	
	private ServerSocketChannel listenChannel;
	
	private static final int PORT = 6667;
	
	//构造器
	//初始化工作
	public GroupChatServer() {
		try {
			//得到选择器
			selector = Selector.open();
			// ServerSocketChannel
			listenChannel = ServerSocketChannel.open();
			//绑定端口
			listenChannel.socket().bind(new InetSocketAddress(PORT));
			//设置非阻塞模式
			listenChannel.configureBlocking(false);
			//将 listenChannel 注册到 selector
			listenChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//监听
	public void listen() {
		System.out.println("监听线程：" + Thread.currentThread().getName());
		try {
			//循环处理
			while (true) {
				int count = selector.select(2000);
				if (count > 0) { //有事件处理
					//遍历得到 SelectionKey集合
					Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
					while (iterator.hasNext()) {
						//取出 selectionKey
						SelectionKey key = iterator.next();
						//监听到accept
						if (key.isAcceptable()) {
							SocketChannel sc = listenChannel.accept();
							sc.configureBlocking(false);
							//将该 sc 注册到selector
							sc.register(selector, SelectionKey.OP_READ);
							//提示
							System.out.println(sc.getRemoteAddress() + "上线");
						}
						
						if (key.isReadable()) { //通道发送read事件，即通道是可读状态
							//处理读(专门写方法)
							readData(key);
						}
						//当前的key 删除, 防止重复处理
						iterator.remove();
					}
				} else {
					//System.out.println("等待...");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//
		}
	}

	private void readData(SelectionKey key) {
		SocketChannel channel = null;
		try {
			//取到关联的channel
			channel = (SocketChannel) key.channel();
			//创建buffer
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			int count = channel.read(byteBuffer);
			//根据count的值处理
			if (count > 0) {
				//把缓冲区的数据转化为字符串
				String msg = new String(byteBuffer.array());
				//输出消息
				System.out.println("from 客户端：" + msg);
				//向其它的客户端转发消息(去掉自己), 专门写一个方法来处理
				sendInfoToOtherClients(msg, channel);
			}
		} catch (Exception e) {
			try {
				System.out.println(channel.getRemoteAddress() + "离线了");
				//取消注册
				key.cancel();
				//关闭通道
				channel.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException{
		System.out.println("服务器转发消息中...");
		System.out.println("服务器转发数据给客户端线程：" + Thread.currentThread().getName());
		// 遍历 所有注册到selector 上的 SocketChannel,并排除 self
		for (SelectionKey key : selector.keys()) {
			SelectableChannel targetChannel = key.channel();
			if (targetChannel instanceof SocketChannel && targetChannel != self) {
				//通过 key  取出对应的 SocketChannel
				SocketChannel dest = (SocketChannel) targetChannel;
				//将msg 存储到buffer
				ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
				//将buffer 的数据写入 通道
				dest.write(buffer);
			}
		}
	}

	public static void main(String[] args) {
		GroupChatServer groupChatServer = new GroupChatServer();
		groupChatServer.listen();
	}
}
