package com.hujing.netty.decorate;

/**
 * @author : hujing
 * @date : 19-9-23
 */
public class DecorateComponent1 extends Decorate {

    public DecorateComponent1(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doFeatureB();
    }

    private void doFeatureB() {
        System.out.println("功能B");
    }
}
