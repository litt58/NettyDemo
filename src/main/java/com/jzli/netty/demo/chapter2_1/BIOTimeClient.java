package com.jzli.netty.demo.chapter2_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.jzli.netty.demo.chapter2_1.BIOTimeServerHandler.QUERYORDER;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2017/9/7
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
public class BIOTimeClient {

    public static void main(String[] args) {
        int port = 6666;
        BufferedReader reader = null;
        PrintWriter writer = null;
        Socket socket = null;
        try {
            socket = new Socket("localhost", port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            writer.println(QUERYORDER);
            String line = reader.readLine();
            System.out.println("Now is " + line);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    reader = null;
                } catch (IOException e) {
                }
            }
            if (writer != null) {
                writer.close();
                writer = null;
            }
            if (socket != null) {
                try {
                    socket.close();
                    socket = null;
                } catch (IOException e) {
                }
            }
        }
    }
}
