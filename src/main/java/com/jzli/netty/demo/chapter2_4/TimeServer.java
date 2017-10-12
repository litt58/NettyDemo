package com.jzli.netty.demo.chapter2_4;

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
    public static void main(String[] args) {
        int port = 6666;

        AsyncTimeServerHandler asyncTimeServerHandler = new AsyncTimeServerHandler(port);
        new Thread(asyncTimeServerHandler).start();
    }
}
