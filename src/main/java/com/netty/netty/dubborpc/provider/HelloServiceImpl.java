package com.netty.netty.dubborpc.provider;

import com.netty.netty.dubborpc.publicinterface.HelloService;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName HelloServiceImpl
 * @Description TODO
 * @date 2020/2/26 20:28
 */
public class HelloServiceImpl implements HelloService {

	private int count = 0;
	
	@Override
	public String hello(String msg) {
		System.out.println("接到客户端消息");
		if (msg != null) {
			return String.format("你好客户端, 我已经收到你的消息 [%s] 第 %d 次", msg, (++count));
		} else {
			return "你好客户端, 我已经收到你的消息";
		}
	}
}
