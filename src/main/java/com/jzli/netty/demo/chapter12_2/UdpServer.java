package com.jzli.netty.demo.chapter12_2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

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
public class UdpServer {
    public void bind(int port) throws InterruptedException {
        //配置服务器端的NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group).option(ChannelOption.SO_BROADCAST, true);
            //绑定端口，同步等待成功
            ChannelFuture future = serverBootstrap.bind(port).sync();
            //等待服务器端监听端口关闭
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 6666;
        UdpServer server = new UdpServer();
        server.bind(port);
    }
}
