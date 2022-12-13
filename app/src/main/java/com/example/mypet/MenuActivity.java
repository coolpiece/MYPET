package com.example.mypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    private Button btn_walk;
    private Button btn_eat;
    private Button btn_health;
    private Button btn_roulette;
    private Button btn_family;
    private Button btn_community;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
/*
        Intent intent = getIntent(); // PETUID 전달받음
        Bundle bundle = intent.getExtras();
        String petUid = bundle.getString("PETUID");*/
        String petUid = "fHnuhDOOn6hwokuDpLar";


                btn_eat = findViewById(R.id.btn_eat);
        btn_eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, com.example.mypet.EatActivity.class);
                intent.putExtra("PETUID", petUid);
                startActivity(intent); //액티비티 이동
            }
        });

        btn_walk = findViewById(R.id.btn_walk);
        btn_walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, com.example.mypet.WalkActivity.class);
                intent.putExtra("PETUID", petUid);
                startActivity(intent); //액티비티 이동
            }
        });


        btn_health = findViewById(R.id.btn_health);
        btn_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, com.example.mypet.HealthActivity.class);
                intent.putExtra("PETUID", petUid);
                startActivity(intent); //액티비티 이동
            }
        });

        btn_roulette = findViewById(R.id.btn_roulette);
        btn_roulette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, com.example.mypet.RouletteActivity.class);
                intent.putExtra("PETUID", petUid);
                startActivity(intent); //액티비티 이동
            }
        });

        btn_family = findViewById(R.id.btn_family);
        btn_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, com.example.mypet.FamilyActivity.class);
                intent.putExtra("PETUID", petUid);
                startActivity(intent); //액티비티 이동
            }
        });

        btn_community = findViewById(R.id.btn_community);
        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, com.example.mypet.CommunityActivity.class);
                intent.putExtra("PETUID", petUid);
                startActivity(intent); //액티비티 이동
            }
        });
    }
}