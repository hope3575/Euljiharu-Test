package com.example.stop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView timeText;
    private Thread timeThread=null;
    private Boolean isRunning=true;

    int i=0;
    int buttonCount=0;//0일때는 시작버튼, 1일때는 정지 버튼
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton=(Button) findViewById(R.id.startButton);
        Button resetButton=(Button) findViewById(R.id.resetButton);

        timeText=(TextView)findViewById(R.id.timeText);
    //시작, 정지 버튼 눌렀을 때
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonCount == 0) {
                    isRunning = true;
                    timeThread = new Thread(new timeThread());
                    timeThread.start();
                    startButton.setText("정지");
                    buttonCount++;

                } else {
                    isRunning = false;
                    startButton.setText("시작");
                    buttonCount--;
                }
            }
        });
        ////////////////////초기화 눌렀을 때
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonCount==0){
                    i=0;
                    timeText.setText("00:00:00:00");
                }
            }
        });
    }
     Handler handler=new Handler(){
        public void handleMessage(Message msg){
            int mSec= msg.arg1%100;
            int sec=(msg.arg1/100)%60;
            int min=(msg.arg1/100)/60;
            int hour=(msg.arg1/100)/100/360;

            String result=String.format("%02d:%02d:%02d:%02d",hour,min,sec,mSec);
            timeText.setText(result);
        }
     };


    public class timeThread implements Runnable{
        @Override
        public void run() {
            while(isRunning){
                try{
                    Thread.sleep(10
                    );
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                Message msg=new Message();
                msg.arg1=i++;
                handler.sendMessage(msg);

            }
        }
    }
}