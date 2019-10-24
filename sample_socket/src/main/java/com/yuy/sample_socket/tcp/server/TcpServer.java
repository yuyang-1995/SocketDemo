package com.yuy.sample_socket.tcp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author: yuyang
 * Date:2019/10/24 13:10
 * Description:多个客户端连接这个Server
 * Version:
 */
public class TcpServer {

    public void start() {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(9091);
            //创建MSgPool 对象异步开启传输
            MsgPool.getInstance().start();

            while (true) {

                Socket socket = serverSocket.accept();

                System.out.println("ip = " + socket.getInetAddress().getHostAddress()
                        + " , port = " + socket.getPort() + " is online...");


                ClientTask clientTask = new ClientTask(socket);
                MsgPool.getInstance().addMsgComingListener(clientTask);
                clientTask.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        new TcpServer().start();
    }

}
