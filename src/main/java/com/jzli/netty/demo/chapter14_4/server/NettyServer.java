package com.jzli.netty.demo.chapter14_4.server;

import com.jzli.netty.demo.chapter14_4.codec.NettyMessageDecoder;
import com.jzli.netty.demo.chapter14_4.codec.NettyMessageEncoder;
import com.jzli.netty.demo.chapter14_4.common.NettyConstant;
import com.jzli.netty.demo.chapter14_4.handler.HeartBeatRespHandler;
import com.jzli.netty.demo.chapter14_4.handler.LoginAuthRespHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

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
public class NettyServer {

    private void bind() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.SO_BACKLOG, 100)
                .childHandler(new ChannelInitializer<Channel>() {

                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
                        ch.pipeline().addLast("MessageEncoder", new NettyMessageEncoder());
                        ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(50));
                        ch.pipeline().addLast(new LoginAuthRespHandler());
                        ch.pipeline().addLast("HeartBeatHandler", new HeartBeatRespHandler());
                    }

                });

        //绑定端口，同步等待成功
        ChannelFuture f = b.bind(NettyConstant.REMOTEIP, NettyConstant.PORT).sync();
        System.out.println("netty server start ok:" + (NettyConstant.REMOTEIP + ":" + NettyConstant.PORT));
        f.channel().closeFuture().sync();
    }

    public static void main(String[] args) throws Exception {
        new NettyServer().bind();
    }
}
