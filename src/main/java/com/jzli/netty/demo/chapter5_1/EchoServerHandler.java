package com.jzli.netty.demo.chapter5_1;

import io.netty.buffer.ByteBuf;
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
public class EchoServerHandler extends ChannelHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    int counter=1;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        logger.info("This is "+counter++ +" times receive client: ["+body+"]");
        body +="$_";
        ByteBuf buf = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(buf);
    }

}
