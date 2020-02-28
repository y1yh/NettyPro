package com.netty.netty.dubborpc.provider;

import com.netty.netty.dubborpc.netty.NettyServer;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName ServerBootstrap
 * @Description TODO
 * @date 2020/2/26 21:52
 */
public class ServerBootstrap {
	public static void main(String[] args) {
		NettyServer.startServer("127.0.0.1", 7000);
	}
}
