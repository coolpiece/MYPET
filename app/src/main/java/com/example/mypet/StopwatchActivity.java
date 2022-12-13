package com.example.mypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity {

    private Button startButton;
    private Button resetButton;
    private TextView timeText;
    private Thread timeThread = null;
    private Boolean isRunning = true;
    private Button backbtn;

    int i =0;

    int buttonCount = 0; //0일때는 시작 1일때는 정지버튼이 보임

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        startButton = findViewById(R.id.startButton);
        resetButton = findViewById(R.id.resetButton);

        timeText = findViewById(R.id.timeText);

        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StopwatchActivity.this, WalkActivity.class);
                startActivity(intent); //액티비티 이동
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonCount ==0) {
                    timeThread = new Thread(new timeThread());
                    timeThread.start();
                    isRunning = true;
                    startButton.setText("stop");
                    buttonCount ++;
                }else{
                    isRunning = false;
                    startButton.setText("start");
                    buttonCount--;

                }

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(buttonCount ==0){
                    i=0;
                    timeText.setText("00:00:00:00");

                }

            }
        });


    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg){
           int mSec = msg.arg1 %100;
           int sec = (msg.arg1 / 100)%60;
           int min = (msg.arg1 / 100)/60;
           int hour = (msg.arg1 / 100)/360;

           String result = String.format("%02d:%02d:%02d:%02d", hour, min, sec, mSec);

           timeText.setText(result);


        }
    };

    public class timeThread implements Runnable{
        public void run(){
            while(isRunning){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                Message msg = new Message();
                msg.arg1 = i++;
                handler.sendMessage(msg);
            }
        }
    }
}