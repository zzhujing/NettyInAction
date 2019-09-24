package com.hujing.netty.decorate;

/**
 * @author : hujing
 * @date : 19-9-23
 */
public class Client {
    public static void main(String[] args) {
        Component component = new DecorateComponent2(new DecorateComponent1(new ComponentImpl()));
        component.doSomething();
    }
}
