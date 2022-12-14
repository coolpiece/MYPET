package com.example.mypet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouletteActivity extends AppCompatActivity {
    private FirebaseFirestore mFirestore;
    private ArrayList<String> memberList = new ArrayList<>();
    LuckyWheel luckyWheel;
    List<WheelItem> wheelItems;
    String point;
    int family_number = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent(); // PETUID 전달받음
        Bundle bundle = intent.getExtras();
        String petUid = bundle.getString("PETUID");
        /*mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("PetInfo").document(petUid).collection("MemberList").get() // 파이어베이스에서 가족 목록 불러오기
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String member = document.getData().get("userNickname").toString();
                            memberList.add(member);
                            family_number++;
                        }
                    }
                });*/

        if(family_number % 2 != 0) { // 홀수명일 때 *2해서 짝수로. 실제로는 (family_number - 1)명
            family_number *= 2;
        }

        setContentView(R.layout.activity_menu_roulette);

        generateWheelItems(memberList); // 점수판 데이터 생성

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

    private void generateWheelItems(ArrayList<String> memberList) { // 데이터 넣기
        Bitmap.Config conf = Bitmap.Config.ARGB_4444;
        Bitmap bitmap = Bitmap.createBitmap(100, 100, conf);

        memberList.add("a");
        memberList.add("b");
        memberList.add("c");
        memberList.add("d");
        memberList.add("e");
        memberList.add("ㅅㅇ");

        wheelItems = new ArrayList<>();
        for(int i = 0; i < family_number; i++) {
            if(i % 2 == 0)
                wheelItems.add(new WheelItem(Color.parseColor("#F44366"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_blank), memberList.get(i)));
            else
                wheelItems.add(new WheelItem(Color.parseColor("#00E6FF"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_blank), memberList.get(i)));
        }
    }
}
