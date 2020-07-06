package com.example.jetsonnanomaintest1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import static android.graphics.Color.RED;

public class MainActivity extends AppCompatActivity {

    private Button btn_IPenter;//輸入按鈕
    private Button btn_exit;//離開按鈕
    private Button btn_ok;//確認按鈕
    private EditText editText0;//IP輸入框
    private TextView textView_IP;//確認IP文字框

    public static String ip;//IP
    public static Socket socket = null;
    public static DataOutputStream dataOutputStream = null;
    public boolean ip_ok = false;
    public static boolean debug = false;//密技用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //定義元件
        btn_IPenter = findViewById(R.id.btn_IPenter);
        btn_exit = findViewById(R.id.btn_exit);
        btn_ok = findViewById(R.id.btn_ok);
        editText0 = findViewById(R.id.editText0);
        textView_IP = findViewById(R.id.textView_IP);
        //定義IP
        //ip = "192.168.43.223";
        //輸入按鈕事件
        btn_IPenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(editText0.getText()) != null){
                    textView_IP.setText("IP:"+editText0.getText());
                    btn_ok.setVisibility(View.VISIBLE);

                    if(String.valueOf(editText0.getText()).equals("9527")){//debug ip
                        ip_ok = true;
                        debug = true;
                    }else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    //連線到伺服器ip,port
                                    socket = new Socket(String.valueOf(editText0.getText()), 9999);
                                    dataOutputStream =new DataOutputStream(socket.getOutputStream());
                                    //傳送數據到伺服器端
                                    dataOutputStream.writeUTF("jetbot:"+"in");
                                    //System.out.println("in");
                                    ip_ok = true;
                                    ip = String.valueOf(editText0.getText());

                                } catch (UnknownHostException e) {
                                    e.printStackTrace();
                                    //System.out.println("UHE");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    //System.out.println("IDN");
                                }
                            }
                        }).start();
                    }
                }else {
                    textView_IP.setText(editText0.getText());
                    btn_ok.setVisibility(View.INVISIBLE);
                }
            }
        });
        //離開按鈕事件
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        //確認按鈕事件
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ip_ok){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this  , Main2.class);
                    startActivity(intent);
                    finish();
                }else{
                    textView_IP.setText("WRONG IP");
                    textView_IP.setTextColor(RED);
                    btn_ok.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public static String getIp(){
        return ip;
    }

    public void setStr(String str){

    }
}
