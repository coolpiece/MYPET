package com.example.mypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class HealthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_health);

        Intent intent = getIntent(); // PETUID 전달받음
        Bundle bundle = intent.getExtras();
        String petUid = bundle.getString("PETUID");
    }
}