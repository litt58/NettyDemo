package com.jzli.netty.demo.chapter2_3;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2017/10/12
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：NIO 客户端
 * ========================================================
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 6666;
        new Thread(new TimeClientHandler("localhost", port), "TimeClient-001").start();
    }
}
