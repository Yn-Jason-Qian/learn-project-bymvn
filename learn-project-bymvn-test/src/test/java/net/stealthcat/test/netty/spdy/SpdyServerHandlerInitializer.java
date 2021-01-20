package net.stealthcat.test.netty.spdy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.ssl.SslHandler;
import org.eclipse.jetty.npn.NextProtoNego;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * Created by qianyang on 17-11-23.
 */
public class SpdyServerHandlerInitializer extends ChannelInitializer {

    private final SSLContext context;

    public SpdyServerHandlerInitializer(SSLContext context) {
        this.context = context;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        SSLEngine engine = context.createSSLEngine();
        engine.setUseClientMode(false);
        NextProtoNego.put(engine, new DefaultSelectProvider());
        NextProtoNego.debug = true;

        pipeline.addLast("sslHandler", new SslHandler(engine));
        pipeline.addLast("spdyOrHttpChooser", new SpdyChooserHandler(1024*1024, 1024*1024));
    }
}
