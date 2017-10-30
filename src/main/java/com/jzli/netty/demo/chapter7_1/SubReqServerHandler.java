package com.jzli.netty.demo.chapter7_1;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class SubReqServerHandler extends ChannelHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq req = (SubscribeReq) msg;
        System.out.println(msg);
        if (req.getUserName().equalsIgnoreCase("ljz")) {
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    private SubscribeResp resp(int subReqId) {
        SubscribeResp subscribeResp = new SubscribeResp();
        subscribeResp.setSubReqID(subReqId);
        subscribeResp.setRespCode(0);
        subscribeResp.setDesc("order success!");
        return subscribeResp;
    }
}
