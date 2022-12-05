package com.example.mypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class EnrollActivity extends AppCompatActivity {
    private static final String TAG = "Petinfo";
    int REQUEST_IMAGE_CODE = 1001;
    private RadioButton radio_girl;
    private RadioButton radio_boy;
    private Button btn_enroll;

    public String getSex(View v) {

        RadioButton boy = (RadioButton) findViewById(R.id.radio_boy);
        RadioButton girl = (RadioButton) findViewById(R.id.radio_girl);
        RadioButton mid = (RadioButton) findViewById(R.id.radio_mid);
        String Sex = null;

        if (boy.isChecked()) {
            Sex = boy.getText().toString();
        } else if (mid.isChecked()) {
            Sex = mid.getText().toString();
        } else {
            Sex = girl.getText().toString();
        }
        return Sex;
    }

    public String getPet(View v) {
        RadioButton cat = (RadioButton) findViewById(R.id.radio_cat);
        RadioButton dog = (RadioButton) findViewById(R.id.radio_dog);
        String Pet = null;
        if (cat.isChecked()) {
            Pet = cat.getText().toString();
        } else {
            Pet = dog.getText().toString();
        }
        return Pet;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        radio_boy = findViewById(R.id.radio_boy);
        radio_girl = findViewById(R.id.radio_girl);
        btn_enroll = findViewById(R.id.btn_enroll);
        btn_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enroll(view);
            }
        });
    }

    private void enroll(View v) {
        String name = ((EditText) findViewById(R.id.Name_Text)).getText().toString();
        String category = ((EditText) findViewById(R.id.Category_Text)).getText().toString();
        String birth = ((EditText) findViewById(R.id.Birth_Text)).getText().toString();
        String Sex = getSex(v);
        String Pet = getPet(v);

        if (name.length() > 0 && birth.length() == 8 && category.length() > 0 && Sex != null && Pet != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Petinfo petinfo = new Petinfo(name, category, birth);
            if (user != null) {
                db.collection("users").document(user.getUid()).set(petinfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EnrollActivity.this, "반려동물정보 등록을 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EnrollActivity.this, "반려동물정보 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                Log.w(TAG, "Error writing document", e);
                            }
                        });

            }
        } else {
            Toast.makeText(EnrollActivity.this, "반려동물정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
}