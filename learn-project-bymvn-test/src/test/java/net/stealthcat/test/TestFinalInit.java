package net.stealthcat.test;

/**
 * Created by qianyang on 18-1-31.
 */
public class TestFinalInit {

    static class A {
        final String a;
        A () {
            this("a");
        }

        A (String b) {
            a = b;
        }

    }

}
