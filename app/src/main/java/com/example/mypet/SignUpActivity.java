package com.example.mypet;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    private FirebaseFirestore mFirestore; // 파이어스토어 데이터베이스
    private EditText mEtEmail, mEtNickname, mEtPassword, mEtPasswordCheck;
    private Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirebaseAuth = FirebaseAuth.getInstance(); // 인스턴스 초기화
        mEtEmail = findViewById(R.id.etEmail);
        mEtPassword = findViewById(R.id.etPassword);
        mEtPasswordCheck = findViewById(R.id.etPassword_check);
        mEtNickname = findViewById(R.id.etNickname);

        mBtnRegister = findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 회원가입 처리 시작
                String strEmail = mEtEmail.getText().toString();
                String strPassword = mEtPassword.getText().toString();
                String strPasswordCheck = mEtPasswordCheck.getText().toString();
                String strNickname = mEtNickname.getText().toString();

                if (!strEmail.equals("") && !strPassword.equals("") && !strPasswordCheck.equals("") && !strNickname.equals("")) { // 이메일과 비밀번호가 공백이 아닌 경우
                    if(strPassword.equals(strPasswordCheck)) { // Firebase Auth 진행
                        mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                    mFirestore = FirebaseFirestore.getInstance();
                                    UserAccount account = new UserAccount();
                                    account.setIdToken(firebaseUser.getUid());
                                    account.setEmailId(firebaseUser.getEmail());
                                    account.setPassword(strPassword);
                                    account.setNickname(strNickname);

                                    // 파이어베이스에 연동
                                    mFirestore.collection("User").document(firebaseUser.getEmail()).collection("UserAccount").document(firebaseUser.getUid()).set(account);

                                    Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else {
                                    Toast.makeText(SignUpActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else { Toast.makeText(SignUpActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show(); }
                } else { Toast.makeText(SignUpActivity.this, "정보를 모두 입력하세요.", Toast.LENGTH_LONG).show(); }
            }
        });
    }
}
