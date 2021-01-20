package net.stealthcat.test;

/**
 * Created by qianyang on 17-11-23.
 */
public class TestResource {

    public static void main(String[] args) {
        System.out.println(TestResource.class.getResourceAsStream("/bogus.cert"));
    }
}
