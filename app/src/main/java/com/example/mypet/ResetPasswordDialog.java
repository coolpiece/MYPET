package com.example.mypet;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordDialog {
    private Context context;
    private String strEmail;
    private FirebaseAuth mFirebaseAuth;
    public ResetPasswordDialog(Context context) {
        this.context = context;
    }

    public void callFunction() {
        final Dialog dig = new Dialog(context);
        dig.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dig.setContentView(R.layout.dialog_reset_password);
        dig.show();

        mFirebaseAuth = FirebaseAuth.getInstance();
        final EditText mEtEmail = dig.findViewById(R.id.etEmail);

        final Button mBtnSendEmail = dig.findViewById(R.id.btn_send_email);
        mBtnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = mEtEmail.getText().toString();
                mFirebaseAuth.sendPasswordResetEmail(strEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(context.getApplicationContext(), "이메일을 발송하였습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        final Button mBtnCancel = dig.findViewById(R.id.btn_cancel);
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dig.dismiss();
            }
        });
    }
}



