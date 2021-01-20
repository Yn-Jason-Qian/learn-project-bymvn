package net.stealthcat.test.netty.spdy;

import io.netty.channel.ChannelInboundHandler;
import io.netty.handler.codec.spdy.SpdyOrHttpChooser;
import org.eclipse.jetty.npn.NextProtoNego;

import javax.net.ssl.SSLEngine;

/**
 * Created by qianyang on 17-11-23.
 */
public class SpdyChooserHandler extends SpdyOrHttpChooser{
    protected SpdyChooserHandler(int maxSpdyContentLength, int maxHttpContentLength) {
        super(maxSpdyContentLength, maxHttpContentLength);
    }

    @Override
    protected SelectedProtocol getProtocol(SSLEngine engine) {
        DefaultSelectProvider provider = (DefaultSelectProvider) NextProtoNego.get(engine);
        String protocol = provider.getSelectedProtocol();
        if(protocol == null) {
            return SelectedProtocol.UNKNOWN;
        }
        switch (protocol) {
            case "http/1.1": return SelectedProtocol.HTTP_1_1;
            case "spdy/3.1": return SelectedProtocol.SPDY_3_1;
            default: return SelectedProtocol.UNKNOWN;
        }
    }

    @Override
    protected ChannelInboundHandler createHttpRequestHandlerForHttp() {
        return new HttpRequestHandler();
    }

    @Override
    protected ChannelInboundHandler createHttpRequestHandlerForSpdy() {
        return new SpdyRequestHandler();
    }
}
