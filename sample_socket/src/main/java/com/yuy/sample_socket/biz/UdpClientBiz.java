package com.yuy.sample_socket.biz;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;


import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Author: yuyang
 * Date:2019/10/24 10:54
 * Description:
 * Version:
 */
public class UdpClientBiz {

    private String mServerIp = "192.168.80.13";
    private InetAddress mServerAddress;
    private int mServerPort = 7777;
    private DatagramSocket mSocket;

    private MyHandler mMyHandler = new MyHandler(this);

    public static class MyHandler extends Handler {
    
            private  WeakReference<UdpClientBiz> mWeakReference;
    
            public MyHandler(UdpClientBiz activity) {

                //创建主线程中的 Handler
//                super(Looper.getMainLooper());
                mWeakReference = new WeakReference<UdpClientBiz>(activity);
    
            }
    
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                UdpClientBiz activity = mWeakReference.get();
                switch (msg.what) {
    
                        default:break;
                }
            }
        }


    public UdpClientBiz() {
        try {

            mServerAddress = InetAddress.getByName(mServerIp);
            mSocket = new DatagramSocket();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

      public interface OnMsgReturndListener{

      void onMsgReturnd(String msg);

          void onError(Exception e);
      }

      //异步方法调用 ： 可以将这个异常try catch通过 Listener 回调出去
     //同步方法调用 ： 尽可能的不要直接在 方法中try catch 最好 throw 出去让调用者感知错误异常
    // 此处异常方法 是异步调用
    public void sendMsg(final String msg, final OnMsgReturndListener msgReturndListener) {

        new Thread(){

            @Override
            public void run() {
                try {
                    byte[] bytes = msg.getBytes();

                    DatagramPacket clientPacket = new DatagramPacket(bytes, bytes.length, mServerAddress, mServerPort);

                    mSocket.send(clientPacket);

                    //接收 消息
                    byte[] buf = new byte[1024];
                    DatagramPacket serverMsgPacket = new DatagramPacket(buf ,buf.length);
                    mSocket.receive(serverMsgPacket);

                    final String serverMsg = new String(serverMsgPacket.getData(),
                            0, serverMsgPacket.getLength());


                    mMyHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            msgReturndListener.onMsgReturnd(serverMsg);
                        }
                    });


                } catch (final IOException e) {

                    mMyHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            msgReturndListener.onError(e);
                        }
                    });

                }
            }
        }.start();
    }


  public  void onDestory(){

      if (null != mSocket) {
          mSocket.close();
      }
    }

}
