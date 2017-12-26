package com.jzli.netty.demo.chapter14_4.handler;

import com.jzli.netty.demo.chapter14_4.common.MessageType;
import com.jzli.netty.demo.chapter14_4.bean.Header;
import com.jzli.netty.demo.chapter14_4.bean.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2017/12/11
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：服务器端心跳处理器
 * ========================================================
 */
public class HeartBeatRespHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
			NettyMessage message =(NettyMessage) msg;
			// 返回心跳应答消息
			if (message.getHeader() != null
				&& message.getHeader().getType() == MessageType.HEARTBEAT_REQ
					.value()) {
			    System.out.println("Receive client heart beat message : ---> "
				    + message);
			    NettyMessage heartBeat = buildHeatBeat();
			    System.out
				    .println("Send heart beat response message to client : ---> "
					    + heartBeat);
			    ctx.writeAndFlush(heartBeat);
			} else
			    ctx.fireChannelRead(msg);
	  }
	
	private NettyMessage buildHeatBeat() {
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setType(MessageType.HEARTBEAT_RESP.value());
		message.setHeader(header);
		return message;
	 }
}
