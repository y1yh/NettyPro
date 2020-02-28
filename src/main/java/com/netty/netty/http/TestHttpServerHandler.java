package com.netty.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName TestHttpServerHandler
 * @Description 
 * 		说明
 * 			1. 	SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter的子类
 * 			2.	HttpObject：客户端和服务端通讯的数据被封装为HttpObject	
 * @date 2020/2/19 20:03
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
	
	// channelRead0 读取客户端数据
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		System.out.println("对应的channel=" + ctx.channel() + ", pipeline=" + ctx.pipeline() + "通过pipeline获取channel=" + ctx.channel().pipeline());
		System.out.println("当前ctx的handler=" + ctx.handler());
		
		if (msg instanceof HttpRequest) {
			System.out.println("ctx 类型= " + ctx.getClass());
			System.out.println("pipeline hashcode=" + ctx.pipeline().hashCode() + ", TestHttpServerHandler hashcode=" + this.hashCode());

			System.out.println("msg类型=" + msg.getClass());
			System.out.println("客户端地址：" + ctx.channel().remoteAddress());
			
			HttpRequest request = (HttpRequest) msg;
			//获取uri,过滤指定的资源
			URI uri = new URI(request.uri());
			if ("/favicon.ico".equals(uri.getPath())) {
				System.out.println("请求了 favicon.ico, 不做响应");
				return;
			}
			
			//回复信息给浏览器[http协议]
			ByteBuf content = Unpooled.copiedBuffer("hello, 我是服务器", CharsetUtil.UTF_8);
			//构造一个http响应,即httpResponse
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
			
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
			response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
			
			//将构建好response返回
			ctx.writeAndFlush(response);
		}
	}
}
