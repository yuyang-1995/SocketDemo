package com.yuy.sample_socket.tcp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Author: yuyang
 * Date:2019/10/24 13:28
 * Description:
 * Version:
 */
public class ClientTask extends Thread implements MsgPool.MsgComingListener{

    private Socket mSocket;
    private InputStream mIs;
    private OutputStream mOs;

    public ClientTask(Socket socket) {

        try {
            mSocket = socket;
            mIs = socket.getInputStream();
            mOs = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        BufferedReader br = new BufferedReader(new InputStreamReader(mIs));
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                System.out.println("read  = " + line);
                // 转发消息至其他Socket
                MsgPool.getInstance().sendMsg(mSocket.getPort()+" : " +line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMsgComing(String msg) {
        try {
            mOs.write(msg.getBytes());
            mOs.write("\n".getBytes());
            mOs.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
