package com.jzli.netty.demo.chapter2_1;

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
 * @Description ：使用线程实现时间服务器
 * ========================================================
 */
public class BIOTimeServer {

    public static void main(String[] args) throws IOException {
        int port = 6666;
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("Time server is start in port " + port);
            while (true) {
                Socket accept = server.accept();
                new Thread(new BIOTimeServerHandler(accept)).start();
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
