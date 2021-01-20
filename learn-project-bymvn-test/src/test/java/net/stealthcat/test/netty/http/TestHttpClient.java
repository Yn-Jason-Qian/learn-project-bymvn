package net.stealthcat.test.netty.http;

import java.net.MalformedURLException;

/**
 * Created by qianyang on 17-11-24.
 */
public class TestHttpClient {

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        HttpClient client = new HttpClient("http://127.0.0.1:8080");
        System.out.println(new String(client.request(new byte[0])));
    }
}
