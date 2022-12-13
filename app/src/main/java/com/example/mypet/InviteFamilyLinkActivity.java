package com.example.mypet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InviteFamilyLinkActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFireStore;
    private String userUid, userEmail, userNickname, petUid;
    private Map<String, String> userData = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_family_link);

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        if (pendingDynamicLinkData != null) {
                            Uri deepLink = pendingDynamicLinkData.getLink();
                            String segment = deepLink.getLastPathSegment();
                            switch(segment) {
                                case "invite" :
                                    petUid = deepLink.getQueryParameter("petUid");
                                    Log.d("***********", petUid);
                            }
                        }

                        mFirebaseAuth = FirebaseAuth.getInstance(); // 멤버 리스트에 사용자 등록
                        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                        mFireStore = FirebaseFirestore.getInstance();
                        userUid = firebaseUser.getUid();
                        userEmail = firebaseUser.getEmail();
                        userNickname = firebaseUser.getDisplayName();

                        userData.put("petUid", petUid);
                        userData.put("userUid", userUid);
                        userData.put("userEmail", userEmail);
                        userData.put("userNickname", userNickname);

                        Log.d("***********", "hi?");
                        //mFirestore.collection("PetInfo").document(petUid).collection("MemberList").document(userUid).set(userData);
                        Toast.makeText(InviteFamilyLinkActivity.this, "가족 등록을 성공했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(InviteFamilyLinkActivity.this, "가족 등록을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}