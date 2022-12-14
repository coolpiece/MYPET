package com.example.mypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class PetselectActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;
    private ArrayList<String> mPetList = new ArrayList<>();
    private ArrayList<PetInfo> mPetInfo = new ArrayList<>();
    private RecyclerView recyclerView;
    private PetListItemAdapter mAdapter;
    private Button btn_plusplus;

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_petselect);

        mFirebaseAuth = FirebaseAuth.getInstance(); // 인스턴스 초기화
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
        String myUid = firebaseUser.getUid();

        mPetList.clear();
        mPetInfo.clear();
        mFirestore.collectionGroup("MemberList").whereEqualTo("userUid", myUid).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String getPetUid = document.getData().get("petUid").toString(); // 현재 사용자의 Uid가 들어있는 반려동물 목록 불러오기
                            mPetList.add(getPetUid);
                        }
                        for (String petUid : mPetList) {
                            mFirestore.collection("PetInfo").document(petUid).collection("profile").get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    PetInfo petInfo = document.toObject(PetInfo.class);
                                                    mPetInfo.add(petInfo);
                                                }
                                                mAdapter = new PetListItemAdapter(mPetInfo, petUid, getApplicationContext());
                                                recyclerView = findViewById(R.id.petList_recyclerView);
                                                recyclerView.setAdapter(mAdapter);
                                                recyclerView.setLayoutManager(new LinearLayoutManager(PetselectActivity.this, LinearLayoutManager.VERTICAL, false));
                                                mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
                                            } else {
                                                Toast.makeText(PetselectActivity.this, "반려동물 목록을 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PetselectActivity.this, "반려동물 목록을 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });


        btn_plusplus = findViewById(R.id.btn_plusplus);
        btn_plusplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetselectActivity.this, EnrollActivity.class);
                startActivity(intent); //액티비티 이동
            }
        });
    }
}
