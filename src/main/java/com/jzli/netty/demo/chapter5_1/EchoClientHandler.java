package com.jzli.netty.demo.chapter5_1;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class EchoClientHandler extends ChannelHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private int counter = 1;

    private String msg = "Hi 李金钊!$_";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 100; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(msg.getBytes()));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("This is " + counter++ + " times receive server:[" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
