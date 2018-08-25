package net.stealthcat.test;

public class InterfaceDefaultMethodDemo {

    public static void main(String[] args) {
        new D().hello();
    }

}

interface A {
    default void hello(){
        System.out.println("hello a");
    }
}

interface B extends A{
    @Override
    default void hello() {
        System.out.println("hello b");
    }
}

interface C extends A{
    @Override
    default void hello() {
        System.out.println("hello c");
    }
}

class D implements B, C{

    @Override
    public void hello() {
        B.super.hello();
    }
}