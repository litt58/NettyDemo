package com.jzli.netty.demo.chapter7_1;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2017/10/30
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
public class SubReqClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 1; i < 11; i++) {
            ctx.write(create(i));
        }
        ctx.flush();
    }

    private SubscribeReq create(int i) {
        SubscribeReq subscribeReq = new SubscribeReq();
        subscribeReq.setSubReqID(i);
        subscribeReq.setUserName("ljz");
        subscribeReq.setPhoneNumber("11111111");
        subscribeReq.setAddress("离开家酸辣粉可莱丝");
        subscribeReq.setProductName("klk");
        return subscribeReq;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeResp resp = (SubscribeResp) msg;
        System.out.println(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
