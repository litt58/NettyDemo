package com.jzli.netty.demo.chapter3_2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.jzli.netty.demo.chapter2_3.MultiplexerTimeServer.QUERYORDER;
import static com.jzli.netty.demo.chapter2_3.MultiplexerTimeServer.WRONGORDER;

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
public class TimeServerHandler extends ChannelHandlerAdapter {
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String line = new String(bytes, "UTF-8");
        System.out.println("Netty time server receive order:" + line);
        String currentTime;
        if (QUERYORDER.equalsIgnoreCase(line)) {
            currentTime = dateFormat.format(new Date());
        } else {
            currentTime = WRONGORDER;
        }
        ByteBuf byteBuf = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(byteBuf);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
