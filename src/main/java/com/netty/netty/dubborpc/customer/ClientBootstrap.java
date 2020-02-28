package com.netty.netty.dubborpc.customer;

import com.netty.netty.dubborpc.netty.NettyClient;
import com.netty.netty.dubborpc.publicinterface.HelloService;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName ClientBootstrap
 * @Description TODO
 * @date 2020/2/26 21:50
 */
public class ClientBootstrap {

	//这里定义协议头
	public static final String providerName = "HelloService#hello#";
	
	public static void main(String[] args) {
		NettyClient customer = new NettyClient();
		
		HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);

		String res = service.hello("你好  dubbo~");
		System.out.println("调用的结果 res = " + res);
	}
}
