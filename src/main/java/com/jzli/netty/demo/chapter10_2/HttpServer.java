package com.jzli.netty.demo.chapter10_2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2017/11/6
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
public class HttpServer {
    private static final String DEFAULT_URL = "/src/";

    public void run(final int port, final String url) throws InterruptedException {
        //配置服务器端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            socketChannel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                            socketChannel.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            socketChannel.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
                        }
                    });
            //绑定端口，同步等待成功
            ChannelFuture future = serverBootstrap.bind(port).sync();
            //等待服务器端监听端口关闭
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 6666;
        HttpServer server = new HttpServer();
        server.run(port, DEFAULT_URL);
    }
}
