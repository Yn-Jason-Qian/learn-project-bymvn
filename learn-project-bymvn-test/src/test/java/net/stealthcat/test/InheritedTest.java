package net.stealthcat.test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by qianyang on 18-2-26.
 */
public class InheritedTest {

    static class C {
        protected void test() {
            System.out.println("test c");
        }
    }

    static class A extends C{
        protected void test() {
            System.out.println("test");
        }
    }

    static class B extends A {
        void execute() {
            test();
            System.out.println("execute");
        }
    }

    static class D implements MethodInterceptor {

        public <T> T proxy(Class<T> clazz) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(clazz);
            enhancer.setCallback(this);
            return (T) enhancer.create();
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            if (method.getName().equals("test")) {
                System.out.println("test proxy");
            }

            return  proxy.invokeSuper(obj, args);
        }
    }

    public static void main(String[] args) {
        B a = new D().proxy(B.class);
        a.execute();
    }


}
