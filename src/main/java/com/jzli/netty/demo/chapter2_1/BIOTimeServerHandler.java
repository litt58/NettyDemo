package com.jzli.netty.demo.chapter2_1;

import sun.misc.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
public class BIOTimeServerHandler implements Runnable {
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Socket socket;
    public static final String QUERYORDER = "query time order";
    public static final String WRONGORDER = "wrong order";

    public BIOTimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            String line;
            String result;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                System.out.println("BIO time server receive order:" + line);
                if (QUERYORDER.equalsIgnoreCase(line)) {
                    result = dateFormat.format(new Date());
                } else {
                    result = WRONGORDER;
                }
                writer.println(result);
            }
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
