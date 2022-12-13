package com.example.mypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;

public class EatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_eat);

        Intent intent = getIntent(); // PETUID 전달받음
        Bundle bundle = intent.getExtras();
        String petUid = bundle.getString("PETUID");
    }
}