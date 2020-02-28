package com.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName BIOServer
 * @Description bio实例
 * @date 2020/2/14 11:32
 */
public class BIOServer {
	public static void main(String[] args) throws Exception{
		//线程池机制
		
		//思路
		//1.创建一个线程池
		//2.如果有客户端连接，就创建一个线程，与之通讯(单独写一个方法)
		
		//创建线程池
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		//创建ServerSocket 监听6666端口
		ServerSocket serverSocket = new ServerSocket(6666);

		System.out.println("服务端启动...");
		while (true) {
			System.out.println("线程信息 id = " + Thread.currentThread().getId() + ", 线程名: " + Thread.currentThread().getName());
			//阻塞在此，等待连接
			//监听，等待客户端连接
			System.out.println("等待连接");
			//监听 等待服务器端连接
			Socket socket = serverSocket.accept();
			//开辟一个线程与之客户端进行通信
			cachedThreadPool.execute(() -> handle(socket));
		}
	}

	//编写一个handle方法，和客户端通信
	private static void handle(Socket socket) {
		try {
			System.out.println("线程信息 id = " + Thread.currentThread().getId() + ", 线程名: " + Thread.currentThread().getName());
			//创建byte数组存放数据
			byte[] bytes = new byte[1024];
			//通过socket获取输入流
			InputStream inputStream = socket.getInputStream();
			
			//循环读取客户端发送的数据
			while (true) {
				System.out.println("线程信息 id = " + Thread.currentThread().getId() + ", 线程名: " + Thread.currentThread().getName());
				System.out.println("read...");
				//会阻塞在此，等待数据
				int read = inputStream.read(bytes);
				if (read != -1) {
					System.out.println(new String(bytes, 0, read));//输出客户端发送数据
				} else {
					break;
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("关闭和Client的连接.");
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
class BIOClient{
	public static void main(String[] args) throws Exception{
		Socket socket = new Socket("127.0.0.1", 6666);
		
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write("hello".getBytes());
		
		outputStream.close();
		socket.close();
	}
}