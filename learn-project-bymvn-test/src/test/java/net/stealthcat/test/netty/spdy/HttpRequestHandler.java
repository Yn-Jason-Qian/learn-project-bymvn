package net.stealthcat.test.netty.spdy;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

/**
 * Created by qianyang on 17-11-23.
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        if (HttpHeaders.is100ContinueExpected(msg)) {
            FullHttpResponse rsp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
            ctx.writeAndFlush(rsp);
        }
        FullHttpResponse response = new DefaultFullHttpResponse(msg.getProtocolVersion(), HttpResponseStatus.OK);
        response.content().writeBytes(getContent().getBytes());
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain;charset=utf-8");
        boolean isKeepAlive = HttpHeaders.isKeepAlive(msg);
        if(isKeepAlive) {
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
            response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
        ChannelFuture future= ctx.writeAndFlush(response);
        if(!isKeepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    protected String getContent() {
        return "Transfer message by http/1.1";
    }
}
