package com.example.mypet;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddPostActivity extends AppCompatActivity {
    private FirebaseUser user;
    private Button btn_back;
    private Button btn_add_post_image;
    private Button btn_add_post_confirm;
    private ArrayList<Uri> mArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AddPostImageAdapter mAdapter;
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

            }
        });

        mAdapter.setOnItemClickListener(new AddPostImageAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(View v, int position) {
                mArrayList.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        });
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_CANCELED) { // 선택 취소 시
                Toast.makeText(AddPostActivity.this, "이미지 선택이 취소되었습니다.", Toast.LENGTH_SHORT).show();
            }
            else if(result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();
                if(intent.getClipData() != null) {
                    ClipData clipData = intent.getClipData();
                    count += clipData.getItemCount();
                    if(count > 10) {
                        Toast.makeText(AddPostActivity.this, "사진은 10개까지 선택할 수 있습니다.", Toast.LENGTH_SHORT).show();
                        count -= clipData.getItemCount();
                        return;
                    } else if(clipData.getItemCount() == 1) {
                        Uri uri = clipData.getItemAt(0).getUri();
                        mArrayList.add(uri);
                        mAdapter = new AddPostImageAdapter(mArrayList, getApplicationContext());
                        recyclerView.setAdapter(mAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(AddPostActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    } else {
                        for(int i = 0; i < clipData.getItemCount(); i++) {
                            Uri uri = clipData.getItemAt(i).getUri();
                            mArrayList.add(uri);
                            int a = clipData.getItemCount();
                            String s = Integer.toString(a);
                        }
                        mAdapter = new AddPostImageAdapter(mArrayList, getApplicationContext());
                        recyclerView.setAdapter(mAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(AddPostActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    }

                }
            }
        }
    });

/*
        private void storageUpload() {
            final String title = ((EditText) findViewById(R.id.etTitle)).getText().toString();
            if(title.length()>0) {
                loaderLayout.setVisibility(View.VISIBLE);
                final ArrayList<String> contentsList = new ArrayList<>();
                final ArrayList<String> formatList = new ArrayList<>();
                user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseFirestore storage = FirebaseFirestore.getInstance();
                final DocumentReference documentReference = postInfo == null ? firebaseFirestore.collection
            }
        }*/
}

