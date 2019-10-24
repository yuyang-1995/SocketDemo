package com.yuy.sample_socket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yuy.sample_socket.biz.TcpClientBiz;

public class TcpActivity extends AppCompatActivity {

    private EditText mEtMsg;
    private Button mBtnSend;
    private TextView mTvContent;

    private TcpClientBiz mTcpClientBiz = new TcpClientBiz();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        mTcpClientBiz.setMsgListerer(new TcpClientBiz.OnMsgListener() {
            @Override
            public void onMsg(String msg) {
                appendMsgToContent(msg);
            }

            @Override
            public void onErroe(Exception e) {
                e.printStackTrace();
            }

        });

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = mEtMsg.getText().toString();
                if (TextUtils.isEmpty(msg)) {
                    return;
                }
                mEtMsg.setText("");
                mTcpClientBiz.sendMsg(msg);
            }
        });
    }

    private void appendMsgToContent(String msg) {
        mTvContent.append(msg + "\n");
    }

    private void initViews() {
        mEtMsg = (EditText) findViewById(R.id.id_et_msg);
        mBtnSend = (Button) findViewById(R.id.id_btn_send);
        mTvContent = (TextView) findViewById(R.id.id_tv_content);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTcpClientBiz.onDestroy();
    }
}
