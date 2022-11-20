package com.example.mypet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;

public class EatActivity extends AppCompatActivity {

    private CheckBox one, two, third;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_eat);

        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        third = findViewById(R.id.third);


    }
}