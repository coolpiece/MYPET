package com.example.mypet;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class FamilyActivity extends AppCompatActivity {
    ListView listView;
    FamilyListItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_family);

        listView = findViewById(R.id.familylistview); // listview 참조
        adapter = new FamilyListItemAdapter(); // adapter 참조

        adapter.addItem(new FamilyListItem("서윤")); // 가족 구성원 받아와서 추가하는 방식으로 수정해야함 ...
        adapter.addItem(new FamilyListItem("세빈"));
        adapter.addItem(new FamilyListItem("승호"));
        listView.setAdapter(adapter);
    }
}