package com.hujing.netty.decorate;

/**
 * @author : hujing
 * @date : 19-9-23
 * 一个实例组件能完成功能A
 */
public class ComponentImpl implements Component {
    @Override
    public void doSomething() {
        System.out.println("功能A");
    }
}
