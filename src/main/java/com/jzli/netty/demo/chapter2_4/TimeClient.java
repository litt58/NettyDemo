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
public class TimeClient {
    public static void main(String[] args) {
        AsyncTimeClientHandler clientHandler = new AsyncTimeClientHandler("localhost", 6666);
        new Thread(clientHandler).start();
    }
}
