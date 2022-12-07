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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EnrollActivity extends AppCompatActivity {
    private static final String TAG = "Petinfo";
    private FirebaseFirestore db;
    private Button btn_enroll;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        btn_enroll = findViewById(R.id.btn_enroll);
        mFirebaseAuth = FirebaseAuth.getInstance(); // 인스턴스 초기화
        btn_enroll.setOnClickListener(new View.OnClickListener() { // 등록하기 버튼 눌렀을 때
            @Override
            public void onClick(View view) {
                enroll(view);
            }
        });
    }

    public String selectPet(View v) { // 강아지 || 고양이 선택
        RadioButton cat = (RadioButton) findViewById(R.id.radio_cat);
        RadioButton dog = (RadioButton) findViewById(R.id.radio_dog);
        String pet = null;
        if (cat.isChecked()) {
            pet = cat.getText().toString();
        } else {
            pet = dog.getText().toString();
        }
        return pet;
    }

    public String selectSex(View v) { // 성별 선택
        RadioButton boy = (RadioButton) findViewById(R.id.radio_boy);
        RadioButton girl = (RadioButton) findViewById(R.id.radio_girl);
        RadioButton mid = (RadioButton) findViewById(R.id.radio_mid);
        String sex = null;

        if (boy.isChecked()) {
            sex = boy.getText().toString();
        } else if (mid.isChecked()) {
            sex = mid.getText().toString();
        } else {
            sex = girl.getText().toString();
        }
        return sex;
    }

    private void enroll(View v) { // 파이어베이스에 등록
        String name = ((EditText) findViewById(R.id.Name_Text)).getText().toString();
        String pet = selectPet(v);
        String sex = selectSex(v);
        String category = ((EditText) findViewById(R.id.Category_Text)).getText().toString();
        String birth = ((EditText) findViewById(R.id.Birth_Text)).getText().toString();

        if (name.length() > 0 && birth.length() == 8 && category.length() > 0 && sex != null && pet != null) {
            Petinfo petinfo = new Petinfo();
            db = FirebaseFirestore.getInstance();
            if (petinfo != null) {
                petinfo.setName(name);
                petinfo.setPet(pet);
                petinfo.setSex(sex);
                petinfo.setCategory(category);
                petinfo.setBirth(birth);


                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                UserAccount account = new UserAccount();
                account.setEmailId(firebaseUser.getEmail());
                db.collection("User")
                        .document(firebaseUser.getEmail())
                        .collection("Petlist")
                        .add(petinfo)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(EnrollActivity.this, "반려동물정보 등록을 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EnrollActivity.this, PetselectActivity.class);
                                startActivity(intent); //액티비티 이동
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