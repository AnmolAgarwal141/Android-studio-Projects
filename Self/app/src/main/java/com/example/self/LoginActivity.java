package com.example.self;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import util.JournalApi;

public class LoginActivity extends AppCompatActivity {
    private Button createaccbutton,loginbutton;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference collectionReference=db.collection("Users");
    private AutoCompleteTextView emailaddress;
    private EditText password;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createaccbutton=findViewById(R.id.createaccountbuttonlogin);
        progressBar=findViewById(R.id.login_progress);
        firebaseAuth=FirebaseAuth.getInstance();
        emailaddress=findViewById(R.id.email);
        password=findViewById(R.id.password);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        loginbutton=findViewById(R.id.email_sign_in);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginemailandpassword(emailaddress.getText().toString().trim(),password.getText().toString().trim());
            }
        });
        createaccbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,CreateAccountActivity.class));
            }
        });
    }
    private void loginemailandpassword(String email,String pwd){
        progressBar.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(pwd)){
            firebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    FirebaseUser user=firebaseAuth.getCurrentUser();
                    assert user != null;
                    final String currentuserid=user.getUid();

                    collectionReference.whereEqualTo("userid",currentuserid).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                           if(e!=null){

                           }
                           else {
                               assert queryDocumentSnapshots != null;
                               if(!queryDocumentSnapshots.isEmpty()) {
                                   progressBar.setVisibility(View.INVISIBLE);
                                   for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                       JournalApi journalApi = JournalApi.getInstance();
                                       journalApi.setUsername(snapshot.getString("username"));
                                       journalApi.setUserid(currentuserid);
                                       startActivity(new Intent(LoginActivity.this,PostJournalActivity.class));
                                   }
                               }
                           }
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
        else {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(LoginActivity.this,"Please Enter Email and Password",Toast.LENGTH_SHORT).show();
        }
    }
}
