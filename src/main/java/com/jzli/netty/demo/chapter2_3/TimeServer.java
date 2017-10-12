package com.jzli.netty.demo.chapter2_3;

import java.io.IOException;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2017/10/12
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 6666;
        try {
            MultiplexerTimeServer multiplexerTimeServer = new MultiplexerTimeServer(port);
            new Thread(multiplexerTimeServer).start();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("最后运行的钩子程序");
                System.exit(0);
            }));
        }
    }
}
