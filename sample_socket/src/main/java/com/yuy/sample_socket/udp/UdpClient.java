package com.yuy.sample_socket.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Author: yuyang
 * Date:2019/10/23 22:58
 * Description:
 * Version:
 */
public class UdpClient {

//    private String mServerIp = "192.168.1.102";
    private String mServerIp = "192.168.80.13"; //服务端ip  字符串
    private InetAddress mServerAddress; //IP 地址  Java对 IP地址的封装
    private int mServerPort = 7778;   //端口
    private DatagramSocket mSocket;   //UDP 用于接受和发送数据报包的的套接字
    private Scanner mScanner;

    public UdpClient() {
        try {
            mServerAddress = InetAddress.getByName(mServerIp); //，注入ip 创建服务端ip对象 InetAddress
            mSocket = new DatagramSocket(); //创建 服务端的套接字 绑定到本地主机上的任意可用端口

            //
            mScanner = new Scanner(System.in);
            mScanner.useDelimiter("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        while (true) {
            try {
                //发送
                String clientMsg = mScanner.next();  //
                byte[] clientMsgBytes = clientMsg.getBytes();

                //将数据打包 到DatagramPacket
                DatagramPacket clientPacket = new DatagramPacket(clientMsgBytes,
                        clientMsgBytes.length, mServerAddress, mServerPort);
                //调用DatagramSocket 的发送方法
                mSocket.send(clientPacket);

                //接受
                byte[] buf = new byte[1024];
                //
                DatagramPacket serverMsgPacket = new DatagramPacket(buf ,buf.length);
                //
                mSocket.receive(serverMsgPacket);

                String serverMsg = new String(serverMsgPacket.getData(),
                        0, serverMsgPacket.getLength());

                System.out.println("msg = " + serverMsg);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        new UdpClient().start();
    }

}
