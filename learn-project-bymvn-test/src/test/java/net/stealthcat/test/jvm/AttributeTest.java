package net.stealthcat.test.jvm;

import javax.annotation.Resource;

/**
 * Created by qianyang on 18-2-12.
 */
public class AttributeTest<T> extends SignatureParent<T>{

    @Deprecated
    @Resource(name="abc", type = AttributeTest.class)
    T t;

    public AttributeTest(T t){
        this.t = t;
    }
}
