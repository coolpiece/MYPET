package com.example.mypet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firestore.v1.WriteResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommunityActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;
    private ArrayList<AddPost> mArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PostListAdapter mAdapter;
    private FloatingActionButton btn_add_post;

    @Override
    public void onStart() {
        super.onStart();
        setContentView(R.layout.activity_menu_community);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("User").document(firebaseUser.getEmail()).collection("Post").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            mArrayList.clear();
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                AddPost addPost = document.toObject(AddPost.class);
                                mArrayList.add(addPost);
                            }
                            mAdapter = new PostListAdapter(mArrayList, getApplicationContext());
                            recyclerView = findViewById(R.id.postList_recyclerview);
                            recyclerView.setAdapter(mAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(CommunityActivity.this, LinearLayoutManager.VERTICAL, false));
                            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
                        } else {
                            Toast.makeText(CommunityActivity.this, "데이터를 불러오는데 실패했습니다.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        btn_add_post = findViewById(R.id.btn_add_post); // 게시글 추가 버튼
        btn_add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CommunityActivity.this, AddPostActivity.class);
                startActivity(intent);
            }
        });
    }
}