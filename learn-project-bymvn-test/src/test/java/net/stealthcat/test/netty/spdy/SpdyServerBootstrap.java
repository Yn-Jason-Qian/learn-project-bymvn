//package net.stealthcat.test.netty.spdy;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//
//import javax.net.ssl.SSLContext;
//import java.security.GeneralSecurityException;
//
///**
// * Created by qianyang on 17-11-23.
// */
//public class SpdyServerBootstrap {
//
//    private static int port = 8080;
//
//    public static void main(String[] args) throws GeneralSecurityException {
//        NioEventLoopGroup work = new NioEventLoopGroup();
//
//        ServerBootstrap bootstrap = new ServerBootstrap();
//        bootstrap.group(work);
//        bootstrap.channel(NioServerSocketChannel.class);
//
//        SSLContext sslContext = BogusSslContextFactory.getInstance(true);
//
//        bootstrap.childHandler(new SpdyServerHandlerInitializer(sslContext));
//        ChannelFuture future = bootstrap.bind(port);
//        future.syncUninterruptibly();
//        final Channel channel = future.channel();
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//
//            @Override
//            public void run() {
//                channel.close();
//                work.shutdownGracefully();
//            }
//        });
//        future.channel().closeFuture().syncUninterruptibly();
//    }
//
//
//
//}
