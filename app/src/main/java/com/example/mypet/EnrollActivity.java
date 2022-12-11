package com.example.mypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EnrollActivity extends AppCompatActivity {
    private  FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;
    private Button btn_enroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        btn_enroll = findViewById(R.id.btn_enroll);
        btn_enroll.setOnClickListener(new View.OnClickListener() { // 등록하기 버튼 눌렀을 때
            @Override
            public void onClick(View view) {
                enroll(view); // 동물 정보 등록 과정
                mFirebaseAuth = FirebaseAuth.getInstance(); // 등록한 동물 Uid 사용자 정보에 등록
                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                mFirestore = FirebaseFirestore.getInstance();
                String petUid = mFirestore.collection("PetInfo").document().getId(); // 펫 고유번호
                mFirestore.collection("User").document(firebaseUser.getEmail()).collection("PetList").document(petUid).set(petUid);
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
            PetInfo petinfo = new PetInfo();
            mFirestore = FirebaseFirestore.getInstance();
            if (petinfo != null) {
                petinfo.setName(name);
                petinfo.setPet(pet);
                petinfo.setSex(sex);
                petinfo.setCategory(category);
                petinfo.setBirth(birth);

                mFirestore.collection("PetInfo").add(petinfo) // 동물 정보 등록
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
                            }
                        });
            }
        } else {
            Toast.makeText(EnrollActivity.this, "반려동물정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }
}