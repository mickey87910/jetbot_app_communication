package com.example.jetsonnanomaintest1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import zhuyuguang.com.verticalseekbar.view.VerticalSeekBar;

public class Guide extends AppCompatActivity{

    private VerticalSeekBar VBL;//左滑桿
    private VerticalSeekBar VBR;//右滑桿
    private TextView TVL;//左馬達轉速顯示
    private TextView TVR;//右馬達轉速顯示
    private float x = 0f;//左馬達轉速
    private float y = 0f;//右馬達轉速
    private Button stop;//停止馬達按鈕
    private Button close;//離線按鈕
    private Button cut;//拍照按鈕
    private Socket socket = Main2.socket;//連線
    private Socket socket2 = null;//拍照用連線
    public String ip = MainActivity.getIp();//取得IP
    public String imgstr="";//用於存base64解碼字串
    private ImageView img;//照片顯示
    private Boolean blImg = false;
    public static DataOutputStream dataOutputStream = Main2.dataOutputStream;
    public static boolean debug = Main2.debug;
    public int IH;
    public int IW;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //將各物件定義
        VBL = (VerticalSeekBar) findViewById(R.id.VerticalSeekBarL);
        VBR = (VerticalSeekBar) findViewById(R.id.VerticalSeekBarR);
        TVL = findViewById(R.id.textViewL);
        TVR = findViewById(R.id.textViewR);
        stop = findViewById(R.id.button_stop);
        close = findViewById(R.id.btn_break);
        cut = findViewById(R.id.btn_cut);
        img = findViewById(R.id.imageView);
        //設定圖片大小
        img.setMaxWidth(448);
        img.setMaxHeight(448);
        //設定滑條位置(我將滑條設定為0~200，所以100是中間)
        VBL.setProgress(100);
        VBR.setProgress(100);
        //歸零馬達
        x = 0;
        y = 0;
        //顯示馬達數值
        TVL.setText(String.valueOf(x));
        TVR.setText(String.valueOf(y));
        //設定滑條點的大小
        VBL.setThumbSize(100,100);
        VBR.setThumbSize(100,100);
        //設定滑條粗細
        VBL.setmInnerProgressWidth(50);
        VBR.setmInnerProgressWidth(50);
        IH = img.getMaxHeight();
        IW = img.getMaxWidth();

        //左滑桿監聽器
        VBL.setOnSlideChangeListener(new VerticalSeekBar.SlideChangeListener() {
            @Override//當滑桿點被按下
            public void onStart(VerticalSeekBar slideView, int progress) { }
            @Override//當滑桿點移動
            public void onProgress(VerticalSeekBar slideView, int progress) {
                x = ((int)progress-100)/100f;//左馬達轉速=(滑桿數值-100)/100，由於滑桿數值為0~200，所以要先減100(歸零)除以100(馬達參數為-1~1)
                TVL.setText(String.valueOf(x));//顯示左馬達轉速
                setSpeed();//將轉速傳至伺服器
            }
            @Override//當滑桿點被放開
            public void onStop(VerticalSeekBar slideView, int progress) { }
        });
        //右滑桿監聽器
        VBR.setOnSlideChangeListener(new VerticalSeekBar.SlideChangeListener() {
            @Override//當滑桿點被按下
            public void onStart(VerticalSeekBar slideView, int progress) { }
            @Override//當滑桿點移動
            public void onProgress(VerticalSeekBar slideView, int progress) {
                y = ((int)progress-100)/100f;//右馬達轉速=(滑桿數值-100)/100，由於滑桿數值為0~200，所以要先減100(歸零)除以100(馬達參數為-1~1)
                TVR.setText(String.valueOf(y));//顯示左馬達轉速
                setSpeed();//將轉速傳至伺服器
            }
            @Override//當滑桿點被放開
            public void onStop(VerticalSeekBar slideView, int progress) { }
        });
        //停止按鈕監聽器
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //將左右滑桿置中
                VBL.setProgress(100);
                VBR.setProgress(100);
                //將左右馬達轉速歸零
                x = 0;
                y = 0;
                //顯示馬達數值
                TVL.setText(String.valueOf(x));
                TVR.setText(String.valueOf(y));
                setSpeed();//將歸零訊息傳至伺服器
            }
        });
        //離線按鈕監聽器
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClose();
                finish();//關掉此頁面
            }
        });
        //拍照按鈕監聽器
        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImage();
            }
        });

    }

    public void setSpeed(){
        if (!debug){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //連線到伺服器ip,port
                        //socket = new Socket(ip, 9999);
                        dataOutputStream =new DataOutputStream(socket.getOutputStream());
                        //傳送數據到伺服器端
                        dataOutputStream.writeUTF("jetbot:setSpeed:"+x+":"+y);
                        System.out.println("jetbot:setSpeed:"+x+":"+y);
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
        if (!debug){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //連線到伺服器ip,port
                        //socket = new Socket(ip, 9999);
                        dataOutputStream =new DataOutputStream(socket.getOutputStream());
                        //傳送數據到伺服器端
                        dataOutputStream.writeUTF("jetbot:stop");
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
    public void saveImage(){
        if (!debug){
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    try {
                        //連線到伺服器ip,port
                        //socketI = new Socket(ip, 9999);
                        dataOutputStream =new DataOutputStream(socket.getOutputStream());
                        //傳送數據到伺服器端
                        dataOutputStream.writeUTF("jetbot:saveImage");
                        //dataOutputStream.close();
                        System.out.println("拍照");
                        connect();//開啟等待照片程序
                        System.out.println("connect");
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean GenerateImage(String imgStr)
    {   //對位元組陣列字串進行Base64解碼並生成圖片
        System.out.println("開始解碼");
        if (imgStr == null) { //影象資料為空
            System.out.println("解碼為空");
            return false;
        }
        //final Base64.Decoder decoder = Base64.getMimeDecoder();
        try
        {
            //Base64解碼
            byte[] b = Base64.decode(imgStr.getBytes(), Base64.DEFAULT);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//調整異常資料
                    b[i]+=256;
                }
            }
            //生成jpeg圖片
            System.out.println("開始生成");
            String imgFilePath = "/mnt/sdcard/test.jpg";//新生成的圖片
            FileOutputStream out = new FileOutputStream(imgFilePath);
            System.out.println("生成完成");
            blImg = true;
            out.write(b);
            out.flush();
            out.close();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = getLoacalBitmap("/mnt/sdcard/test.jpg");
                    img.setImageBitmap(bitmap);//設定圖片
                    img.setMaxHeight(IH);
                    img.setMaxWidth(IW);
                    img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    blImg = false;
                }
            });
            return true;
        }
        catch (Exception e)
        { return false; }
    }
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void connect(){
        if (!debug){
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    try {
                        //連線到伺服器ip,port
                        socket2 = new Socket(ip, 7777);
                        System.out.println("開始等待接收");
                        //接收資料
                        DataInputStream dataInputStream =new DataInputStream(socket2.getInputStream());
                        imgstr =dataInputStream.readLine();
                        System.out.println("接收完成");
                        //dataInputStream.close();
                        GenerateImage(imgstr);//解碼圖片
                        //blImg = true;
                        System.out.println("imgstr:"+imgstr);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

}


