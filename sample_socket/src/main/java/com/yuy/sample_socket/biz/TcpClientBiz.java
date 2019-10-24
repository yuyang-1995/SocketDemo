package com.yuy.sample_socket.biz;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.yuy.sample_socket.tcp.client.TcpClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author: yuyang
 * Date:2019/10/24 20:39
 * Description:
 * Version:
 */
public class TcpClientBiz {

     private Socket socket;

     private InputStream is;

     private OutputStream os;

     public interface OnMsgListener{

         void onMsg(String msg);
         void onErroe(Exception e);

     }

    private MyHandler mMyHandler = new MyHandler(this);

     public static class MyHandler extends Handler {

         private WeakReference<TcpClientBiz> mWeakReference;

             public MyHandler(TcpClientBiz activity) {

                 mWeakReference = new WeakReference<TcpClientBiz>(activity);

             }

             @Override
             public void handleMessage(@NonNull Message msg) {
                 super.handleMessage(msg);
                 TcpClientBiz activity = mWeakReference.get();
                 switch (msg.what) {


                         default:break;
                 }
             }
         }

     private OnMsgListener mOnMsgListener;

     public void setMsgListerer(OnMsgListener onMsgListener){
         this.mOnMsgListener = onMsgListener;
     }

    public TcpClientBiz() {

        new Thread(){

            @Override
            public void run() {
                try {

                     socket = new Socket("192.168.80.13", 9091);
                     is = socket.getInputStream();
                     os = socket.getOutputStream();

                    final BufferedReader br = new BufferedReader(new InputStreamReader(is));
//                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

                     String lien = null;

                    while ((lien = br.readLine()) != null) {

                        final String finalLien = lien;
                        mMyHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mOnMsgListener != null) {

                                    mOnMsgListener.onMsg(finalLien);
                                }
                            }
                        });


                    }

                } catch (final IOException e) {

                    mMyHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mOnMsgListener != null) {

                                mOnMsgListener.onErroe(e);
                            }
                        }
                    });
                    e.printStackTrace();
                }
            }
        }.start();


    }


//    public void start() {
//        try {
//
//
//            // 输出服务端发送的数据
//            new Thread() {
//                @Override
//                public void run() {
//                    try {
//                        String line = null;
//                        while ((line = br.readLine()) != null) {
//                            System.out.println(line);
//                        }
//                    } catch (IOException e) {
//                    }
//
//                }
//            }.start();
//
//            while (true) {
//                String msg = mScanner.next();
//                bw.write(msg);
//                bw.newLine();
//                bw.flush();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



    public void sendMsg(final String msg) {

        new Thread(){

            @Override
            public void run() {
                try {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

                    bw.write(msg);
                    bw.newLine();
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public void onDestroy() {

        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (os != null) {
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
