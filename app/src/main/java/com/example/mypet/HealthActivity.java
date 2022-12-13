package com.example.mypet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;


public class HealthActivity extends AppCompatActivity {
    private LineChart lineChart;
    private EditText editTextNumber;
    private Button btnplus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_health);

        btnplus = findViewById(R.id.btnplus);
        editTextNumber = findViewById(R.id.editTextNumber);
        Intent intent = getIntent(); // PETUID 전달받음
        Bundle bundle = intent.getExtras();
        String petUid = bundle.getString("PETUID");

        ArrayList<Entry> entry_chart1 = new ArrayList<>(); // 데이터를 담을 Arraylist

        lineChart = (LineChart) findViewById(R.id.chart);

        LineData chartData = new LineData(); // 차트에 담길 데이터

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = editTextNumber.getText().toString();
                int result = Integer.parseInt(s1);
                entry_chart1.add(new Entry(1, result)); //entry_chart1에 좌표 데이터를 담는다.
            }
        });



        LineDataSet lineDataSet1 = new LineDataSet(entry_chart1, "LineGraph1"); // 데이터가 담긴 Arraylist 를 LineDataSet 으로 변환한다.;

        lineDataSet1.setColor(Color.RED); // 해당 LineDataSet의 색 설정 :: 각 Line 과 관련된 세팅은 여기서 설정한다.

        chartData.addDataSet(lineDataSet1); // 해당 LineDataSet 을 적용될 차트에 들어갈 DataSet 에 넣는다.

        lineChart.setData(chartData); // 차트에 위의 DataSet을 넣는다.

        lineChart.invalidate(); // 차트 업데이트
        lineChart.setTouchEnabled(false); // 차트 터치 disable

    }
}