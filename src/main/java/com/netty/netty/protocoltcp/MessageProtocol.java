package com.netty.netty.protocoltcp;

/**
 * @author yiyh
 * @version v1.0.0
 * @ClassName MessageProtocol
 * @Description TODO
 * @date 2020/2/23 23:54
 */
public class MessageProtocol {
	private int len;
	private byte[] content;

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
}
