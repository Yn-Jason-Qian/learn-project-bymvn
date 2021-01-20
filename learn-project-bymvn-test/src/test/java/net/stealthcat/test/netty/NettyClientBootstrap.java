package net.stealthcat.test.netty;

import com.common.utils.CommonUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;

/**
 * Created by qianyang on 17-11-16.
 */
public class NettyClientBootstrap {

    private static Channel channel;

    public static void main(String[] args) {
        NettyClientBootstrap bootstrap = new NettyClientBootstrap();
        bootstrap.init();
        CommonUtil.handleInput(input -> {
            if(input.equals("over")) {
                System.exit(0);
            }
            channel.writeAndFlush(Unpooled.copiedBuffer(input.getBytes(Charset.defaultCharset())));
        });
    }

    public void init() {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.channel(NioSocketChannel.class).group(group);
        bootstrap.handler(new NettyClientHandler());
        ChannelFuture future = bootstrap.connect("localhost", 8080);
        try {
            future.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channel = future.channel();
    }
}
