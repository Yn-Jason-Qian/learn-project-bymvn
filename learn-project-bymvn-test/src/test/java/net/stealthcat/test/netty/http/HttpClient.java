package net.stealthcat.test.netty.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by qianyang on 17-11-24.
 */
public class HttpClient{

    private Channel channel;
    private URL url;
    private BlockingQueue<byte[]> responses = new LinkedBlockingQueue<>();
    private static Bootstrap bootstrap;
    private static NioEventLoopGroup group;

    static {
        bootstrap = new Bootstrap();
        group = new NioEventLoopGroup();
        bootstrap.group(group).channel(NioSocketChannel.class);
    }

    public HttpClient(String url) throws MalformedURLException {
        this.url = new URL(url);
        if(!this.url.getProtocol().toLowerCase().startsWith("http")) {
            throw new IllegalArgumentException(String.format("Url[%s] is not  http protocol.", url));
        }
    }

    public byte[] request(byte[] data) throws MalformedURLException, InterruptedException {
        if (channel == null || !channel.isActive()) {
            bootstrap.handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new HttpRequestEncoder(), new HttpResponseDecoder(), new HttpObjectAggregator(Integer.MAX_VALUE));
                    pipeline.addLast(new HttpClientHandler(HttpClient.this));
                }
            });
            ChannelFuture future = bootstrap.connect(url.getHost(), url.getPort());
            future.syncUninterruptibly();
            channel = future.channel();
        }

        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, url.getPath(), Unpooled.wrappedBuffer(data));
        request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        ChannelFuture future = channel.writeAndFlush(request);
        future.syncUninterruptibly();
        return responses.take();
    }

    void addResponse(byte[] resp) {
        responses.add(resp);
    }

    void closeChannel() {
        this.channel.close();
        this.channel = null;
    }
}
