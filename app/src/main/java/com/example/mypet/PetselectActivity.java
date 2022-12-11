package com.example.mypet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PetselectActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<PetInfo> petInfoList;
    private CustomAdapter customAdapter;
    private static final String TAG = "PetselectActivity";
    private FirebaseFirestore db;
    private Button btn_plusplus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_petselect);

    petInfoList = new ArrayList<>();
    customAdapter = new CustomAdapter(petInfoList);
    recyclerView = findViewById(R.id.recyclerView);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(customAdapter);

   /*     progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Fetching Data");
        progressDialog.show();


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<Petinfo>();*/
       db= FirebaseFirestore.getInstance();

      //  customAdapter = new CustomAdapter(arrayList, PetselectActivity.this);

       // recyclerView.setAdapter(customAdapter);*/

        db.collection("PetInfo").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    Log.e("Firestore error", error.getMessage());
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType()==DocumentChange.Type.ADDED){
                        PetInfo petinfo = dc.getDocument().toObject(PetInfo.class);
                        petInfoList.add(petinfo);

                        customAdapter.notifyDataSetChanged();
                    }


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

    private void EventChangeListener() {



    }
}

//https://blog.naver.com/PostView.naver?blogId=fbfbf1&logNo=222559672787&categoryNo=55&parentCategoryNo=37&viewDate=&currentPage=1&postListTopCurrentPage=1&from=postView