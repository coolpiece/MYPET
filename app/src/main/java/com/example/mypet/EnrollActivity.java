package com.example.mypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.WriteResult;

import java.util.HashMap;
import java.util.Map;

public class EnrollActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;
    private Button btn_enroll;
    private Map<String, String> userData = new HashMap<>();
    private String petUid, userUid, userEmail, userNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);
        btn_enroll = findViewById(R.id.btn_enroll);
        btn_enroll.setOnClickListener(new View.OnClickListener() { // 등록하기 버튼 눌렀을 때
            @Override
            public void onClick(View view) {
                enroll(view); // 동물 정보 등록 과정
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
            PetInfo petinfo = new PetInfo(); // 반려동물 프로필 등록
            mFirestore = FirebaseFirestore.getInstance();
            if (petinfo != null) {
                petinfo.setName(name);
                petinfo.setPet(pet);
                petinfo.setSex(sex);
                petinfo.setCategory(category);
                petinfo.setBirth(birth);

                DocumentReference docRef = mFirestore.collection("PetInfo").document();
                petUid = docRef.getId(); // 반려동물 Uid 설정

                mFirestore.collection("PetInfo").document(petUid).collection("profile").document(name).set(petinfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(EnrollActivity.this, "반려동물 정보 등록을 성공하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EnrollActivity.this, "반려동물 정보 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });


                mFirebaseAuth = FirebaseAuth.getInstance(); // 멤버 리스트에 사용자 등록
                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                userUid = firebaseUser.getUid();
                userEmail = firebaseUser.getEmail();
                userNickname = firebaseUser.getDisplayName();

                userData.put("petUid", petUid);
                userData.put("userUid", userUid);
                userData.put("userEmail", userEmail);
                userData.put("userNickname", userNickname);

                mFirestore.collection("PetInfo").document(petUid).collection("MemberList").document(userUid).set(userData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent intent = new Intent(EnrollActivity.this, MenuActivity.class);
                                intent.putExtra("PETUID", petUid); // ************************* 나중에 petselect로 옮겨야할듯 ??
                                startActivity(intent); //액티비티 이동
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EnrollActivity.this, "멤버 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });

            } else {
                Toast.makeText(EnrollActivity.this, "반려동물 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}