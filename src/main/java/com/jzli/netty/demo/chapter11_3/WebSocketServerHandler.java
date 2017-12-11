package com.jzli.netty.demo.chapter11_3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.Date;

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
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    private String webSocketUrl = "ws://localhost:6666/websocket";
    private WebSocketServerHandshaker webSocketServerHandshaker;

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        //传统Http
        if (msg instanceof FullHttpRequest) {
            handlerHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            //WebSocket
            handlerWebSocketRequest(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handlerHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        DecoderResult decoderResult = req.decoderResult();
        HttpHeaders headers = req.headers();
        //Http解码失败，返回Http异常
        if (!decoderResult.isSuccess() ||
                !"webSocket".equalsIgnoreCase(headers.get("Upgrade") != null ? headers.get("Upgrade").toString() : null)) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(webSocketUrl, null, false);
        webSocketServerHandshaker = factory.newHandshaker(req);
        if (webSocketServerHandshaker == null) {
            factory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            webSocketServerHandshaker.handshake(ctx.channel(), req);
        }
    }


    private void handlerWebSocketRequest(ChannelHandlerContext ctx, WebSocketFrame frame) {
        //判断是否为关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            webSocketServerHandshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        //判断是否为ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        //判断是否为文本消息
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedMessageTypeException(String.format("%s frame types not supported", frame.getClass().getName()));
        }
        TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) frame;
        String text = textWebSocketFrame.text();
        System.out.println(String.format("%s received %s", ctx.channel(), text));
        ctx.channel().write(new TextWebSocketFrame(text + " ,欢迎使用Netty WebSocket服务，现在时刻" + new Date().toGMTString()));
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse resp) {
        //返回应答给客户端
        if (resp.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(resp.status().toString(), CharsetUtil.UTF_8);
            resp.content().writeBytes(buf);
            buf.release();
            HttpHeaderUtil.setContentLength(resp, resp.content().readableBytes());
        }
        //非keep-alive，则关闭连接
        ChannelFuture channelFuture = ctx.channel().writeAndFlush(resp);
        if (!HttpHeaderUtil.isKeepAlive(req) || resp.status().code() != 200) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
