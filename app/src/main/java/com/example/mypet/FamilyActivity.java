package com.example.mypet;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FamilyActivity extends AppCompatActivity {
    ListView listView;
    FamilyListItemAdapter adapter;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_family);

        listView = findViewById(R.id.familylistview); // listview 참조
        adapter = new FamilyListItemAdapter(); // adapter 참조

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
            mFirebaseFirestore.collection("PetInfo").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                            }
                        }
                    }
                });



        adapter.addItem(new FamilyListItem("서윤")); // 가족 구성원 받아와서 추가하는 방식으로 수정해야함 ...
        adapter.addItem(new FamilyListItem("세빈"));
        adapter.addItem(new FamilyListItem("승호"));
        listView.setAdapter(adapter);
    }
}