package net.stealthcat.test.netty.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * Created by qianyang on 17-11-24.
 */
public class HttpClientHandler extends SimpleChannelInboundHandler<FullHttpResponse>{

    private HttpClient client;

    public HttpClientHandler(HttpClient client) {
        this.client = client;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse msg) throws Exception {
        byte[] data = new byte[msg.content().readableBytes()];
        msg.content().readBytes(data);
        client.addResponse(data);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        client.closeChannel();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        client.closeChannel();
    }
}