package com.example.mypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;


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

import java.util.List;



public class PetselectActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Petinfo> petinfoList;
    private CustomAdapter customAdapter;
    private static final String TAG = "PetselectActivity";
    private FirebaseFirestore db;
    private Button btn_plusplus;
    private FirebaseAuth mFirebaseAuth;



   recyclerView = findViewById(R.id.recyclerView);
    petinfoList = new ArrayList<>();
    customAdapter = new CustomAdapter(petinfoList);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(customAdapter);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petselect);


        mFirebaseAuth = FirebaseAuth.getInstance(); // 인스턴스 초기화

    Petinfo petinfo = new Petinfo();
    db= FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        UserAccount account = new UserAccount();
        account.setEmailId(firebaseUser.getEmail());
        db.collection("User")
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
                        Petinfo petinfo = dc.getDocument().toObject(Petinfo.class);
                        petinfoList.add(petinfo);
                        customAdapter.notifyDataSetChanged();
                    }


                }

            }
        });
        db.collection("User")
                .document(firebaseUser.getEmail())
                .collection("Petlist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                petinfoList.add((Petinfo) document.getData());
                            }

                            // RecyclerView 생성
                            RecyclerViewCreate();
                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
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

//https://blog.naver.com/PostView.naver?blogId=fbfbf1&logNo=222559672787&categoryNo=55&parentCategoryNo=37&viewDate=&currentPage=1&postListTopCurrentPage=1&from=postView