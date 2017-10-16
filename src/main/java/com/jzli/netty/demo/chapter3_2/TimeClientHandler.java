package com.jzli.netty.demo.chapter3_2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jzli.netty.demo.chapter2_3.MultiplexerTimeServer.QUERYORDER;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2017/10/16
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
public class TimeClientHandler extends ChannelHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ByteBuf message;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        message = Unpooled.copiedBuffer(QUERYORDER.getBytes());
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);

        String body = new String(bytes, "UTF-8");
        logger.info("Now is " + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
