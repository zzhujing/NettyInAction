package com.hujing.netty.handler;

public class CustomProtocol {
    private int header;
    private String body;

    public CustomProtocol() {

    }

    public CustomProtocol(int header, String body) {
        this.header = header;
        this.body = body;
    }

    public int getHeader() {
        return header;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return header+body;
    }
}
