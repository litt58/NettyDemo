package com.jzli.netty.demo.chapter14_4.codec;

import com.jzli.netty.demo.chapter14_4.bean.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;
import java.util.Map;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2017/12/11
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {
    private MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() throws IOException {
        marshallingEncoder = new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg,
                          ByteBuf out) throws Exception {
        if (msg == null || msg.getHeader() == null) {
            throw new Exception("The encode message is null");
        }
        out.writeInt(msg.getHeader().getCrcCode());
        out.writeInt(msg.getHeader().getLength());
        out.writeLong(msg.getHeader().getSessionID());
        out.writeByte(msg.getHeader().getType());
        out.writeByte(msg.getHeader().getPriority());
        out.writeInt((msg.getHeader().getAttachment().size()));
        String key;
        byte[] keyArray;
        Object value;
        for (Map.Entry<String, Object> param : msg.getHeader().getAttachment().entrySet()) {
            key = param.getKey();
            keyArray = key.getBytes("UTF-8");
            out.writeInt(keyArray.length);
            out.writeBytes(keyArray);
            value = param.getValue();
            marshallingEncoder.encode(value, out);
        }
        if (msg.getBody() != null) {
            marshallingEncoder.encode(msg.getBody(), out);
        } else {
            out.writeInt(0);
        }
        out.setInt(4, out.readableBytes() - 8);
    }
}
