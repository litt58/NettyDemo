package com.jzli.netty.demo.chapter2_2;

import com.jzli.netty.demo.chapter2_1.BIOTimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2017/9/7
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：使用线程池实现时间服务器
 * ========================================================
 */
public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 6666;
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("BIO time server is start in port " + port);

            TimeServerHandlerExecutePool pool = new TimeServerHandlerExecutePool(10, 100);
            while (true) {
                Socket accept = server.accept();
                pool.execute(new BIOTimeServerHandler(accept));
            }
        } finally {
            if (null != server) {
                server.close();
                server = null;
            }

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("最后运行的钩子程序");
                System.exit(0);
            }));
        }
    }
}
