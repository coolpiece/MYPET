package com.example.mypet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FamilyActivity extends AppCompatActivity {
    private FamilyListItemAdapter adapter;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;
    private ListView listView;
    private Button btn_add_family;
    private ArrayList<String> mMemberList = new ArrayList<>();
    private Map<String, Object> userData = new HashMap<>();
    private String userUid, userEmail, userNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_family);

        Intent intent = getIntent(); // PETUID 전달받음
        Bundle bundle = intent.getExtras();
        String petUid = bundle.getString("PETUID");

        listView = findViewById(R.id.familylistview); // listview 참조
        adapter = new FamilyListItemAdapter(); // adapter 참조

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();
        mFirestore.collection("PetInfo").document(petUid).collection("MemberList").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String member = document.getData().get("userNickname").toString();
                            String memberemail = document.getData().get("userEmail").toString();

                            mMemberList.add(member);
                            adapter.addItem(new FamilyListItem(member, memberemail));
                            listView.setAdapter(adapter);
                        }
                    }
                });

        btn_add_family = findViewById(R.id.btn_add_family);
        btn_add_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeInviteLink(petUid);
            }
        });
    }

    private void makeInviteLink(String petUid) {
        FirebaseDynamicLinks.getInstance().createDynamicLink() // 동적 링크 만들기
                .setLink(Uri.parse("https://www.mypetsebseo.page.link/invite?petUid=" + petUid))
                .setDomainUriPrefix("https://mypetsebseo.page.link")
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder("com.example.mypet").build())
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if(task.isSuccessful()) {
                            Uri link = task.getResult().getShortLink();
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT, link.toString());
                            intent.setType("text/plain");
                            startActivity(Intent.createChooser(intent, "Share Link"));
                        }
                    }
                });
    }
}