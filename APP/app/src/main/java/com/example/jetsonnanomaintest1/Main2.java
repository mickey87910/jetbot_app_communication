package com.example.jetsonnanomaintest1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main2 extends AppCompatActivity {

    private Button btn_remote;
    private Button button_break;
    private TextView textView2;
    private String[] mod = {"導覽","還書","跟隨","回家","control"};
    private String now;
    public String ip = MainActivity.getIp();
    public static Socket socket = MainActivity.socket;
    public static DataOutputStream dataOutputStream = MainActivity.dataOutputStream;
    public static Boolean debug = MainActivity.debug;//密技用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn_remote = findViewById(R.id.btn_remote);
        button_break = findViewById(R.id.button_break);
        textView2 = findViewById(R.id.textView2);

        btn_remote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                now = mod[4];
                textView2.setVisibility(View.VISIBLE);
                textView2.setText("目前模式:"+now);
                setMod();
                Intent intent = new Intent();
                intent.setClass(Main2.this  , Guide.class);
                startActivity(intent);
            }
        });
        button_break.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClose();
                Intent intent = new Intent(Main2.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void setMod(){
        if (!debug){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //連線到伺服器ip,port
                        //socket = new Socket(ip, 9999);
                        dataOutputStream =new DataOutputStream(socket.getOutputStream());
                        //傳送數據到伺服器端
                        dataOutputStream.writeUTF("jetbot:"+now);
                        //dataOutputStream.close();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    public void setClose(){
        if (!debug) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //連線到伺服器ip,port
                        //socket = new Socket(ip, 9999);
                        dataOutputStream =new DataOutputStream(socket.getOutputStream());
                        //傳送數據到伺服器端
                        dataOutputStream.writeUTF("jetbot:close");

                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
