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

import java.util.HashMap;
import java.util.Map;

public class EnrollActivity extends AppCompatActivity {
    private static final String TAG = "Petinfo";
    int REQUEST_IMAGE_CODE = 1001;
    private RadioGroup radio_gender;
    private RadioButton radio_girl;
    private RadioButton radio_boy;
    private Button btn_enroll;

    public String getSex(View v){

        RadioButton boy = (RadioButton)findViewById(R.id.radio_boy);
        RadioButton girl = (RadioButton)findViewById(R.id.radio_girl);
        RadioButton mid = (RadioButton)findViewById(R.id.radio_mid);
        String Sex = null;

        if(boy.isChecked()){
            Sex = boy.getText().toString();
        }else if(mid.isChecked()){
            Sex = mid.getText().toString();
        }
        else {
            Sex= girl.getText().toString();
        }
        return  Sex;
    }

    public String getPet(View v){

        RadioButton cat = (RadioButton)findViewById(R.id.radio_cat);
        RadioButton dog = (RadioButton)findViewById(R.id.radio_dog);
        String Pet = null;
        if(cat.isChecked()){
            Pet = cat.getText().toString();
        }
        else {
            Pet=dog.getText().toString();
        }
        return  Pet;
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        radio_gender = findViewById(R.id.radio_gender);
        radio_boy = findViewById(R.id.radio_boy);
        radio_girl = findViewById(R.id.radio_girl);
        findViewById(R.id.btn_enroll).setOnClickListener(onClickListener);
        btn_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnrollActivity.this, PetselectActivity.class);
                startActivity(intent); //액티비티 이동
            }

        });
    }

        private void enroll(View v){
            String name = ((EditText)findViewById(R.id.Name_Text)).getText().toString();
            String category =((EditText)findViewById(R.id.Category_Text)).getText().toString();
            String birth = ((EditText)findViewById(R.id.Birth_Text)).getText().toString();
            String Sex = getSex(v);
            String Pet = getPet(v);

            if(name.length()>0&&birth.length()>5&&category.length()>0&&Sex!=null&&Pet!=null) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                Map<String, Object> user = new HashMap<>();
                user.put("first", "Ada");
                user.put("last", "Lovelace");
                user.put("born", 1815);

                Petinfo petinfo = new Petinfo(name, category, birth);
                if (user!=null) {
                    db.collection("users")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    startToast("반려동물정보 등록을 성공하였습니다.");
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    startToast("반려동물정보 등록에 실패하였습니다.");
                                    Log.w(TAG, "Error writing document", e);
                                }
                            });

                }
            }else{
                startToast("반려동물정보를 입력해주세요.");
            }
        }



    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_enroll:
                    break;
        }
    }

    };

    private void startToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}