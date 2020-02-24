package com.example.instargram_copy_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MyPageActivity extends AppCompatActivity {

    Button toggleButton1;
    private static final String TAG = "MemberInfoSetting";
    Button toggleButton2;
    Button toggleButton3;
    Button toggleButton4;

    Button profileEditBtn;
    TextView name_profile;
    TextView user_name;
    TextView web_profile;
    TextView intro_profile;
    ImageView imageView;
    TextView follower;
    TextView posting;
    TextView following;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //user의 정보를 사용할것임
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("Profile").document(user.getUid()); //현재 유저의 프로필 접근





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        imageView = findViewById(R.id.imageView);

        navbar();


        profileEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPageActivity.this , ProfileEditActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.sliding_up, R.anim.stay);
            }
        });

        getName();
        getUserName();
        getWebsiteName();
        getIntroName();
        showFollower();
        showFollowing();
        showPosting();






    }

    public void getName(){    //현재 name 가지고오는 함수
        name_profile = findViewById(R.id.name_profile);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                name_profile.setText(document.getString("name"));//필드 userName의 값을 가져와서 set

            }
        });
    }
    public void getIntroName(){    //현재 intro 가지고오는 함수
        intro_profile = findViewById(R.id.intro_profile);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                intro_profile.setText(document.getString("intro"));//필드 userName의 값을 가져와서 set

            }
        });
    }
    public void getWebsiteName(){    //현재 website 가지고오는 함수
        web_profile = findViewById(R.id.web_profile);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                web_profile.setText(document.getString("website"));//필드 userName의 값을 가져와서 set

            }
        });
    }
    public void getUserName(){    //현재 userName 가지고오는 함수
        user_name = findViewById(R.id.user_name);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                user_name.setText(document.getString("userName"));//필드 userName의 값을 가져와서 set

            }
        });
    }
    public void showFollower(){ //팔로우 값을 가져옴
        follower = findViewById(R.id.textView8);
        db.collection("Follower").document(user.getUid()).collection("friends")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Integer follower_su = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                follower_su += 1;
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            follower.setText(follower_su.toString());
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
    public void showFollowing() { //following수 출력
        following = findViewById(R.id.textView9);
        db.collection("Following").document(user.getUid()).collection("friends")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Integer following_su = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                following_su += 1;
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            following.setText(following_su.toString());
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void showPosting() { //포스팅수 출력
        posting = findViewById(R.id.textView2);
        db.collection("Posting").document(user.getUid()).collection("posting")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Integer posting_su = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                posting_su += 1;
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            posting.setText(posting_su.toString());
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void navbar(){
        toggleButton1 = findViewById(R.id.toggleButton1);
        toggleButton2 = findViewById(R.id.toggleButton2);
        toggleButton3 = findViewById(R.id.toggleButton3);
        toggleButton4 = findViewById(R.id.toggleButton4);
        profileEditBtn = findViewById(R.id.profileEditBtn);


        toggleButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPageActivity.this , HomeActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);

            }
        });
        toggleButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPageActivity.this , SearchActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);

            }
        });
        toggleButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPageActivity.this , PostingActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);

            }
        });
        toggleButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyPageActivity.this , NotificationActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);

            }
        });
    }
    //private void showFollowerList()

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}