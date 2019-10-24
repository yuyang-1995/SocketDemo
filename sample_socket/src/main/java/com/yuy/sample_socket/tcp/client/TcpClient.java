package com.yuy.sample_socket.tcp.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author: yuyang
 * Date:2019/10/24 14:09
 * Description:
 * Version:
 */
public class TcpClient {

    private Scanner mScanner;

    public TcpClient() {
        mScanner = new Scanner(System.in);
        mScanner.useDelimiter("\n");
    }


    public void start() {
        try {
            Socket socket = new Socket("192.168.80.13", 9091);
            //获取socket 输入流
            InputStream is = socket.getInputStream();

            //创建socket 输出流
            OutputStream os = socket.getOutputStream();

            final BufferedReader br = new BufferedReader(new InputStreamReader(is));

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

            // 输出服务端发送的数据
            new Thread() {
                @Override
                public void run() {
                    try {
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                    }

                }
            }.start();

            while (true) {
                String msg = mScanner.next();
                bw.write(msg);
                bw.newLine();
                bw.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new TcpClient().start();
    }

}
