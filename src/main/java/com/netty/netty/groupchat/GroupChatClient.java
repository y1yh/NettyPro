package com.netty.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName GroupChatClient
 * @Description TODO
 * @date 2020/2/20 18:03
 */
public class GroupChatClient {
	private final String HOST;
	private final int PORT;
	
	public GroupChatClient(String host, int port) {
		this.HOST = host;
		this.PORT = port;
	}
	
	public void run() throws Exception{
		NioEventLoopGroup group = new NioEventLoopGroup();
		
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// 得到 pipeline
							ChannelPipeline pipeline = ch.pipeline();
							
							//加入相关的handler
							pipeline.addLast("decoder", new StringDecoder());
							pipeline.addLast("encoder", new StringEncoder());
							//加入自定义的handler
							pipeline.addLast(new GroupChatClientHandler());
						}
					});
			ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();
			
			//得到channel
			Channel channel = channelFuture.channel();
			System.out.println("-------" + channel.localAddress() + "-----------");
			//客户端需要输入信息，创建一个扫描器
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNextLine()) {
				String msg = scanner.nextLine();
				channel.writeAndFlush(msg + "\r\n");
			}
		} finally {
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception{
		new GroupChatClient("127.0.0.1", 7000).run();
	}
}
