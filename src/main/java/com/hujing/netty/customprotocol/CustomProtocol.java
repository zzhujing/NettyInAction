package com.hujing.netty.customprotocol;

/**
 * @author : hujing
 * @date : 2019/10/8
 */
public class CustomProtocol {
    private int length;
    private byte[] content;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
