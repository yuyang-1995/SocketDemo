package com.yuy.sample_socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Author: yuyang
 * Date:2019/10/23 22:43
 * Description:
 * Version:
 */
public class UdpServer {

    private InetAddress mInetAddress;
    private int mPort = 7778;
    private DatagramSocket mSocket;

    private Scanner mScanner;

    public UdpServer() {
        try {
            mInetAddress = InetAddress.getLocalHost();

            String s1 = mInetAddress.getHostName();
            String s2 = mInetAddress.getHostAddress();
//
            System.out.println("LocalHost = " + s1 + "HostAddress = " +  s2);

            //
            mSocket = new DatagramSocket(mPort, mInetAddress);

            mScanner = new Scanner(System.in);
            mScanner.useDelimiter("\n");


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }


    }


    public void start() {

        while (true) {

            try {
                //udp 每次通信数据的大小有限制 通常为1024 byte
                byte[] buf = new byte[1024];
                //接受数据
                DatagramPacket receiverdPacket = new DatagramPacket(buf, buf.length);
                //receiver 方法会阻塞
                mSocket.receive(receiverdPacket);

                //客户端地址
                InetAddress address = receiverdPacket.getAddress();

                //客户端端口号
                int port = receiverdPacket.getPort();
                byte[] data = receiverdPacket.getData();
                String clientMsg = new String(data, 0, data.length);

                System.out.println("address = " + address + "port = " + port
                        + " msg = " + clientMsg);

                //监听终端输入的返回数据
                String returnMsg = mScanner.next();

                //构建返回数据 和端口
                byte[] returnMsgBytesytes = returnMsg.getBytes();

                DatagramPacket sendPackage = new DatagramPacket(returnMsgBytesytes, returnMsgBytesytes.length,
                        receiverdPacket.getSocketAddress());

                //
                mSocket.send(sendPackage);


            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }

    public static void main(String[] args) {

        new UdpServer().start();

    }


//    private InetAddress mInetAddress; //Java提供了 InetAddress  类来代表IP 地址
//    private int mPort = 7777;  //端口
//    private DatagramSocket mSocket; // UDP 套接字, UDP Socket通过 DatagramSocket 发送及接受数据
//
//    private Scanner mScanner;
//
//    public UdpServer() {
//        try {
//
//            mInetAddress = InetAddress.getLocalHost(); //本地主机
//
//            String s = mInetAddress.getHostName();
//
//            System.out.println("LocalHost = " + s);
//
//            //创建绑定到指定本地地址的数据报套接字
//            mSocket = new DatagramSocket(mPort, mInetAddress); //创建 DatagramSocket
//
//            //创建输入
//            mScanner = new Scanner(System.in);
//            mScanner.useDelimiter("\n");
//
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    public void start() {
//        while (true) {
//            try {
//                byte[] buf = new byte[1024];
//                //构建 接收区的 数据报包
//                DatagramPacket receivedPacket = new DatagramPacket(buf, buf.length);
//                //Socket 接收 数据报包
//                mSocket.receive(receivedPacket);
//
//                //根据数据报包 获取发送者的 InetAddress 和端口
//                InetAddress address = receivedPacket.getAddress();
//                int port = receivedPacket.getPort();
//                String clientMsg = new String(receivedPacket.getData(),
//                        0, receivedPacket.getLength());
//                //
//                System.out.println("address = " + address
//                        + " , port = " + port + " , msg = " + clientMsg);
//
//                //获取返回消息
//                String returnedMsg = mScanner.next();
//                byte[] returnedMsgBytes = returnedMsg.getBytes();
//                //创建返回数据报包
//                DatagramPacket sendPacket = new DatagramPacket(returnedMsgBytes,
//                        returnedMsgBytes.length, receivedPacket.getSocketAddress());
//                //发送
//                mSocket.send(sendPacket);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    public static void main(String[] args){
//        new UdpServer().start();
//    }
//
//}


}