package com.hujing.netty.decorate;

/**
 * @author : hujing
 * @date : 19-9-23
 */
public class Decorate implements Component {

    private Component component;

    public Decorate(Component component) {
        this.component = component;
    }

    @Override
    public void doSomething() {
        component.doSomething();
    }
}
