package com.netty.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MyClientHandler
 * @Description TODO
 * @date 2020/2/23 14:26
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
		System.out.println("服务器的ip = " + ctx.channel().remoteAddress());
		System.out.println("收到服务器消息 = " + msg);
	}

	//重写 channelActive 发送数据
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("MyClientHandler 发送数据");
		
		ctx.writeAndFlush(123456L);
		
		/*
		 * 分析
		 * 	1. "abcdabcdabcdabcd" 是16个字节
		 * 	2.该处理器的前一个handler是 MyLongToByteEncoder
		 * 	3.MyLongToByteEncoder父类是 MessageToByteEncoder
		 * 	 
		 * 	MessageToByteEncoder
		 * public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		 *         ByteBuf buf = null;
		 *         try {
		 *             if (acceptOutboundMessage(msg)) { //判断当前msg是不是应该处理的类型,如果是就处理,不是就跳过
		 *                 @SuppressWarnings("unchecked")
		 *                 I cast = (I) msg;
		 *                 buf = allocateBuffer(ctx, cast, preferDirect);
		 *                 try {
		 *                     encode(ctx, cast, buf);
		 *                 } finally {
		 *                     ReferenceCountUtil.release(cast);
		 *                 }
		 *
		 *                 if (buf.isReadable()) {
		 *                     ctx.write(buf, promise);
		 *                 } else {
		 *                     buf.release();
		 *                     ctx.write(Unpooled.EMPTY_BUFFER, promise);
		 *                 }
		 *                 buf = null;
		 *             } else {
		 *                 ctx.write(msg, promise);
		 *             }
		 *         } catch (EncoderException e) {
		 *             throw e;
		 *         } catch (Throwable e) {
		 *             throw new EncoderException(e);
		 *         } finally {
		 *             if (buf != null) {
		 *                 buf.release();
		 *             }
		 *         }
		 *     }
		 * 	4. 因此我们在编写Encoder 是要注意传入的数据类型和处理的数据类型一致
		 */
		//ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabcd", CharsetUtil.UTF_8));
	}
}
