package com.example.mypet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouletteActivity extends AppCompatActivity {
    private LuckyWheel luckyWheel;
    List<WheelItem> wheelItems;
    String point;
    int family_number = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_roulette);

        generateWheelItems(); // 점수판 데이터 생성

        luckyWheel = findViewById(R.id.luck_wheel); // 변수에 담기
        luckyWheel.addWheelItems(wheelItems); // 점수판에 데이터 넣기

        luckyWheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                WheelItem wheelItem = wheelItems.get(Integer.parseInt(point)-1); // 아이템 변수에 담기
                String member = wheelItem.text; // 아이템 텍스트 변수에 담기
                Toast.makeText(RouletteActivity.this, member, Toast.LENGTH_SHORT).show(); // 메시지
            }
        });

        Button start = findViewById(R.id.spin_btn); // 시작 버튼
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                point = String.valueOf(random.nextInt(family_number)+1); // 랜덤 범위
                luckyWheel.rotateWheelTo(Integer.parseInt(point));
            }
        });
    }
    private void generateWheelItems() { // 데이터 넣기
        Bitmap.Config conf = Bitmap.Config.ARGB_4444;
        Bitmap bitmap = Bitmap.createBitmap(100, 100, conf);
        ArrayList<String> memberList = new ArrayList<String>();
        memberList.add("a");
        memberList.add("b");
        memberList.add("c");
        memberList.add("d");
        memberList.add("e");
        memberList.add("ㅅㅇ");

        wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.parseColor("#F44366"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_blank), memberList.get(0))); // 일단 이렇게 해놓고 가족 목록 불러오고, 색상 어떻게 넣을지 고민해볼 것 ..
        wheelItems.add(new WheelItem(Color.parseColor("#00E6FF"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_blank), memberList.get(1)));
        wheelItems.add(new WheelItem(Color.parseColor("#F44366"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_blank), memberList.get(2)));
        wheelItems.add(new WheelItem(Color.parseColor("#00E6FF"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_blank), memberList.get(3)));
        wheelItems.add(new WheelItem(Color.parseColor("#F44366"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_blank), memberList.get(4)));
        wheelItems.add(new WheelItem(Color.parseColor("#00E6FF"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_blank), memberList.get(5)));
    }
}
