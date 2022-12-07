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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentChange;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;



import java.util.Date;
public class PetselectActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Petinfo> arrayList;
    private CustomAdapter customAdapter;
    private static final String TAG = "PetselectActivity";
    private FirebaseFirestore db;
    private DatabaseReference databaseReference;
    private Button btn_plusplus;
    private boolean updating;
    private boolean topScrolled;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_petselect);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Fetching Data");
        progressDialog.show();


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<Petinfo>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        customAdapter = new CustomAdapter(arrayList, PetselectActivity.this);

        recyclerView.setAdapter(customAdapter);

        EventChangeListener();

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

        db.collection("PetInfo").orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType()==DocumentChange.Type.ADDED){
                                arrayList.add(dc.getDocument().toObject(Petinfo.class));
                            }

                            customAdapter.notifyDataSetChanged();
                        }


                    }
                });

    }
}

//https://blog.naver.com/PostView.naver?blogId=fbfbf1&logNo=222559672787&categoryNo=55&parentCategoryNo=37&viewDate=&currentPage=1&postListTopCurrentPage=1&from=postView