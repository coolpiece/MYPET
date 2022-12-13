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
import java.util.List;


public class PetselectActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    //private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PetInfo> mInfo = new ArrayList<PetInfo>();;
    private PetselectAdapter mAdapter;
    private FirebaseFirestore db;
    private Button btn_plusplus;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petselect);
        mFirebaseAuth = FirebaseAuth.getInstance(); // 인스턴스 초기화
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        PetInfo petInfo = new PetInfo();
        db= FirebaseFirestore.getInstance();
        UserAccount account = new UserAccount();
        account.setEmailId(firebaseUser.getEmail());

        /* 서윤 추가 ! 방식 바꿨는데 여기서 써먹을 수 있을지도... ㅋㅎ
        mFirebaseFirestore.collectionGroup("MemberList").whereEqualTo("userUid", myUid).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String petUid = document.getData().get("petUid").toString(); // 현재 사용자의 Uid가 들어있는 반려동물 목록 불러오기
                            mArrayList.add(petUid);

                            Iterator<String> iterator = map.keySet().iterator();
                            while(iterator.hasNext()) {
                                String key = (String) iterator.next(); //
                                Log.d("********", map.get(key).toString());
                                }
                        }
                    }
                });*/



 /*      db.collection("User")
                .document(firebaseUser.getEmail())
                .collection("Petlist")
               // .orderBy("name",Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    Log.e("Firestore error", error.getMessage());
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        PetInfo petinfo = dc.getDocument().toObject(PetInfo.class);
                        mInfo.add(petinfo);
                        PetselectAdapter.notifyDataSetChanged();
                    }
                }
            }
        });*/
        
        db.collection("User")
                .document(firebaseUser.getEmail())
                .collection("Petlist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AddPost addPost = document.toObject(AddPost.class);
                                mInfo.add(petInfo);
                                mAdapter = new PetselectAdapter(mInfo, getApplicationContext());
                                recyclerView = findViewById(R.id.postList_recyclerview);
                                recyclerView.setAdapter(mAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(PetselectActivity.this, LinearLayoutManager.VERTICAL, false));

                            }
                        } else {
                            Toast.makeText(PetselectActivity.this, "데이터를 불러오는데 실패했습니다.", Toast.LENGTH_LONG).show();
                        }
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
