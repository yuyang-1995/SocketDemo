package com.yuy.sample_socket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yuy.sample_socket.biz.UdpClientBiz;

public class UdpActivity extends AppCompatActivity {

    private EditText mEditText;
    private Button mButton;
    private TextView mTextView;

    private UdpClientBiz mUdpClientBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUdpClientBiz = new UdpClientBiz();
        initViews();

    }

    private void initViews() {

        mEditText = findViewById(R.id.id_et_msg);
        mButton = findViewById(R.id.id_btn_send);
        mTextView = findViewById(R.id.id_tv_content);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = mEditText.getText().toString();

                if (TextUtils.isEmpty(msg)) {
                    return;
                }

                addendMsg("client: " + msg);

                mUdpClientBiz.sendMsg(msg, new UdpClientBiz.OnMsgReturndListener() {
                    @Override
                    public void onMsgReturnd(String msg) {
                        addendMsg("server: " + msg);
                    }

                    @Override
                    public void onError(Exception e) {

                        e.printStackTrace();
                    }
                });
            }
        });

    }


    private void addendMsg(String msg){
        mTextView.append(msg + "\n");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUdpClientBiz.onDestory();
    }


}
