package net.stealthcat.test.netty.spdy;

/**
 * Created by qianyang on 17-11-23.
 */
public class SpdyRequestHandler extends HttpRequestHandler{

    @Override
    protected String getContent() {
        return "Transfer message by spdy/3.1";
    }
}
