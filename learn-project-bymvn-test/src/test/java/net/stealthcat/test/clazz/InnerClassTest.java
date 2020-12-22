package net.stealthcat.test.clazz;

public class InnerClassTest {
    public static void main(String[] args) {
        Outer outer = new Outer();
        outer.field = "test";
        Outer.Inner inner = outer.new Inner();
        inner.print();
    }
}

class Outer {
    String field;

    public class Inner {

        void print() {
            System.out.println(field);
        }
    }
}

