package com.hujing.netty.decorate;

/**
 * @author : hujing
 * @date : 19-9-23
 */
public class DecorateComponent2 extends Decorate{
    public DecorateComponent2(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doFeatureC();
    }

    private void doFeatureC() {
        System.out.println("功能C");
    }
}
