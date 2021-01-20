package net.stealthcat.test.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServerBootstrap {

	public static void main(String[] args) {
		NettyServerBootstrap bootstrap = new NettyServerBootstrap();
		bootstrap.init();
	}

	public void init() {
		ServerBootstrap bootstrap = new ServerBootstrap();
		EventLoopGroup parentGroup = new NioEventLoopGroup(1);
		EventLoopGroup childGroup = new NioEventLoopGroup();
		bootstrap.group(parentGroup, childGroup);
		bootstrap.channel(NioServerSocketChannel.class);

		NettyEchoHandler serverHandler = new NettyEchoHandler();
		bootstrap.childHandler(serverHandler);
		
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.bind(8080);
	}
}
