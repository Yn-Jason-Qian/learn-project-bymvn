package net.stealthcat.test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * Created by qianyang on 17-11-20.
 */
@ChannelHandler.Sharable
public class NettyEchoHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf byteBuf = (ByteBuf) msg;
            System.out.println(byteBuf.toString(Charset.defaultCharset()));
            System.out.println(byteBuf.refCnt());
            ctx.channel().writeAndFlush(msg);
            System.out.println(byteBuf.refCnt());
        } finally {
//            ReferenceCountUtil.release(msg);
        }
    }
}
