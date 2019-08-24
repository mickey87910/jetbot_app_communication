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

    private Button btn_Navigation;
    private Button btn_back;
    private Button btn_follow;
    private Button btn_home;
    private Button btn_remote;
    private Button button_break;
    private TextView textView2;
    private String[] mod = {"導覽","還書","跟隨","回家","control"};
    private String now;
    public String ip = MainActivity.getIp();
    public static Socket socket = MainActivity.socket;
    public static DataOutputStream dataOutputStream = MainActivity.dataOutputStream;
    public static Boolean ok87 = MainActivity.ok87;//密技用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn_Navigation = findViewById(R.id.btn_Navigation);
        btn_back = findViewById(R.id.btn_back);
        btn_follow = findViewById(R.id.btn_follow);
        btn_home = findViewById(R.id.btn_home);
        btn_remote = findViewById(R.id.btn_remote);
        button_break = findViewById(R.id.button_break);
        textView2 = findViewById(R.id.textView2);

        btn_Navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                now = mod[0];
                textView2.setVisibility(View.VISIBLE);
                textView2.setText("目前模式:"+now);
                setMod();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                now = mod[1];
                textView2.setVisibility(View.VISIBLE);
                textView2.setText("目前模式:"+now);
                setMod();
            }
        });

        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                now = mod[2];
                textView2.setVisibility(View.VISIBLE);
                textView2.setText("目前模式:"+now);
                setMod();
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                now = mod[3];
                textView2.setVisibility(View.VISIBLE);
                textView2.setText("目前模式:"+now);
                setMod();
            }
        });

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
        if (ok87){

        }else {
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
        if (ok87){

        }else {
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
