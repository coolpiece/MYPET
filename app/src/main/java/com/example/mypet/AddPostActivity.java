package com.example.mypet;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class AddPostActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;
    private FirebaseStorage mStorage;
    private Button btn_back;
    private Button btn_add_post_image;
    private Button btn_add_post_confirm;
    private ArrayList<Uri> mArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AddPostImageAdapter mAdapter;
    private PostListAdapter mpAdapter;
    String title, content;
    int count = 0; // 사진 개수 저장

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        recyclerView = findViewById(R.id.image_recyclerview);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_add_post_image = (Button) findViewById(R.id.btn_add_post_image);
        btn_add_post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setAction(Intent.ACTION_PICK);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                launcher.launch(intent);
            }
        });

        btn_add_post_confirm = (Button) findViewById(R.id.btn_add_post_confirm);
        btn_add_post_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPost(view);
                if (count > 0) uploadImage(view);
            }
        });
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override // 이미지 선택
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_CANCELED) { // 선택 취소 시
                Toast.makeText(AddPostActivity.this, "이미지 선택이 취소되었습니다.", Toast.LENGTH_SHORT).show();
            } else if (result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();
                if (intent.getClipData() != null) {
                    ClipData clipData = intent.getClipData();
                    count += clipData.getItemCount();
                    if (count > 10) {
                        Toast.makeText(AddPostActivity.this, "사진은 10개까지 선택할 수 있습니다.", Toast.LENGTH_SHORT).show();
                        count -= clipData.getItemCount();
                        return;
                    } else if (clipData.getItemCount() == 1) {
                        Uri uri = clipData.getItemAt(0).getUri();
                        mArrayList.add(uri);
                        mAdapter = new AddPostImageAdapter(mArrayList, getApplicationContext());
                        recyclerView.setAdapter(mAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(AddPostActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    } else {
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            Uri uri = clipData.getItemAt(i).getUri();
                            mArrayList.add(uri);
                            // int a = clipData.getItemCount();
                        }
                        mAdapter = new AddPostImageAdapter(mArrayList, getApplicationContext());
                        recyclerView.setAdapter(mAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(AddPostActivity.this, LinearLayoutManager.HORIZONTAL, false));
/*
        mAdapter.setOnItemClickListener(new AddPostImageAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(View v, int position) {
                mArrayList.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyDataSetChanged();
            }
        });*/
                    }

                }
            }
        }
    });

    private void addPost(View view) { // 게시글 작성
        title = ((EditText) findViewById(R.id.etTitle)).getText().toString();
        content = ((EditText) findViewById(R.id.etContent)).getText().toString();
        if (!title.equals("") && !content.equals("")) {
            AddPost addPost = new AddPost();
            mFirebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
            mFirestore = FirebaseFirestore.getInstance();
            addPost.setIdToken(firebaseUser.getUid());
            addPost.setTitle(title);
            addPost.setContent(content);
            mFirestore.collection("User").document(firebaseUser.getEmail()).collection("Post").add(addPost)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            mpAdapter.notifyDataSetChanged();                            Toast.makeText(AddPostActivity.this, "게시글 등록을 성공하였습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddPostActivity.this, "게시글 등록을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(AddPostActivity.this, "제목 및 내용을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage(View view) { // 사진 업로드
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mStorage = FirebaseStorage.getInstance();
        String filename = "post_image_" + title;
        StorageReference storageRef = mStorage.getReference();
        StorageReference imageRef = storageRef.child(firebaseUser.getUid());

        for (int i = 0; i < count; i++) {
            StorageReference nameRef = imageRef.child("PostImage/" + filename + i);
            Uri uri = mArrayList.get(i);
            UploadTask uploadTask = nameRef.putFile(uri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddPostActivity.this, "사진이 정상적으로 업로드 되지 않았습니다.", Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddPostActivity.this, "사진이 정상적으로 업로드 되었습니다.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}

