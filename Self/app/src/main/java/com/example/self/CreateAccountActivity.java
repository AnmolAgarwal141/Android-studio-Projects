package com.example.self;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import util.JournalApi;

public class CreateAccountActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentuser;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference collectionReference=db.collection("Users");
    private EditText emailedittext,passwordedittext,usernameedittext;
    private ProgressBar progressBar;
    private Button createaccountbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        firebaseAuth = FirebaseAuth.getInstance();

        createaccountbutton = findViewById(R.id.createaccountbutton);
        progressBar = findViewById(R.id.create_acc_progress);
        emailedittext = findViewById(R.id.email_account);
        passwordedittext = findViewById(R.id.password_account);
        usernameedittext = findViewById(R.id.username_account);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentuser = firebaseAuth.getCurrentUser();
                if (currentuser != null) {

                } else {

                }
            }
        };
        createaccountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(emailedittext.getText().toString())&&!TextUtils.isEmpty(passwordedittext.getText().toString())&&!TextUtils.isEmpty(usernameedittext.getText().toString())) {
                    String email=emailedittext.getText().toString().trim();
                    String password=passwordedittext.getText().toString().trim();
                    String username=usernameedittext.getText().toString().trim();
                    createuseremailAccount(email, password, username);
                }else{
                    Toast.makeText(CreateAccountActivity.this,"Empty Fields Not Allowed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentuser=firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
    private void createuseremailAccount(String email, String password, final String username){
        if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(username)){
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        currentuser=firebaseAuth.getCurrentUser();
                        assert currentuser != null;
                        final String currentuserid=currentuser.getUid();
                        Map<String,String> userobj=new HashMap<>();
                        userobj.put("userid",currentuserid);
                        userobj.put("username",username);
                        collectionReference.add(userobj).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(Objects.requireNonNull(task.getResult()).exists()){
                                            progressBar.setVisibility(View.INVISIBLE);
                                            String name =task.getResult().getString("username");
                                            JournalApi journalApi=JournalApi.getInstance();
                                            journalApi.setUserid(currentuserid);
                                            journalApi.setUsername(name);
                                            Intent intent=new Intent(CreateAccountActivity.this,PostJournalActivity.class);
                                            intent.putExtra("username",name);
                                            intent.putExtra("userid",currentuserid);
                                            startActivity(intent);

                                        }
                                        else{
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                    else{

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
        else{

        }
    }
}
