package com.netty.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName GroupChatClient
 * @Description TODO
 * @date 2020/2/17 19:40
 */
public class GroupChatClient {
	// 定义相关的属性
	private final String HOST = "127.0.0.1";
	
	private final int PORT = 6667;
	
	private Selector selector;
	private SocketChannel socketChannel;
	private String userName;
	
	//构造器, 完成初始化工作
	public GroupChatClient() throws IOException {
		selector = Selector.open();
		//Opens a socket channel and connects it to a remote address
		//连接服务器
		socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
		//设置非阻塞
		socketChannel.configureBlocking(false);
		// 将channel 注册到selector
		socketChannel.register(selector, SelectionKey.OP_READ);
		//得到userName
		userName = socketChannel.getLocalAddress().toString().substring(1);
		System.out.println(userName + "  is ok");
	}
	
	//读取从服务器端回复的消息
	public void readInfo() {
		try {
			int read = selector.select();
			
			if (read > 0) {
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					if (key.isReadable()) {
						//得到相关的通道
						SocketChannel sc = (SocketChannel) key.channel();
						//得到一个buffer
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						//读取
						sc.read(buffer);
						//把读到的缓冲区的数据转化成字符串
						String msg = new String(buffer.array());
						System.out.println(msg.trim());
					}
					//删除当前的selectionKey, 防止重复操作
					iterator.remove();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendInfo(String info) {
		info = userName + "说：" + info;
		try {
			socketChannel.write(ByteBuffer.wrap(info.getBytes()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		//启动我们客户端
		GroupChatClient chatClient = new GroupChatClient();
		//启动一个线程, 每个3秒，读取从服务器发送数据
		new Thread(() -> {
			while (true) {
				chatClient.readInfo();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		//发送数据给服务器端
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			String s = scanner.nextLine();
			chatClient.sendInfo(s);
		}
	}
}
