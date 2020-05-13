package com.example.orderfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import util.OrderFoodApi;

public class signupchoice extends AppCompatActivity {
    private EditText emailsignup,passwordsignup;
    private Button createAccountButton;
    private CheckBox appforbusiness;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentuser;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference collectionReference=db.collection("Users");
    private String AccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupchoice);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        firebaseAuth = FirebaseAuth.getInstance();

        emailsignup=findViewById(R.id.emailsignup);
        passwordsignup=findViewById(R.id.passwordsignup);
        createAccountButton=findViewById(R.id.createaccountbutton);
        appforbusiness=findViewById(R.id.checkBox);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentuser = firebaseAuth.getCurrentUser();
                if (currentuser != null) {

                } else {

                }
            }
        };
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(emailsignup.getText().toString())&& !TextUtils.isEmpty(passwordsignup.getText().toString())) {
                    String email = emailsignup.getText().toString().trim();
                    String password = passwordsignup.getText().toString().trim();
                    if (appforbusiness.isChecked()) {
                        AccountType = "Restaurant";
                    } else {
                        AccountType = "Customer";
                    }
                    createuseremailAccount(email, password, AccountType);
                }
                else {
                    Toast.makeText(signupchoice.this,"Empty Fields Not Allowed",Toast.LENGTH_SHORT).show();

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
    private void createuseremailAccount(String email, String password, final String Accountype){
        if(!TextUtils.isEmpty(emailsignup.getText().toString())&& !TextUtils.isEmpty(passwordsignup.getText().toString())) {
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        currentuser=firebaseAuth.getCurrentUser();
                        final String currentuserid=currentuser.getUid();
                        Map<String,String> userobj=new HashMap<>();
                        userobj.put("userid",currentuserid);
                        userobj.put("Accounttype",Accountype);
                        collectionReference.add(userobj).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(Objects.requireNonNull(task.getResult()).exists()){
                                            String Account=task.getResult().getString("Accounttype");
                                            OrderFoodApi orderFoodApi= OrderFoodApi.getInstance();
                                            orderFoodApi.setAccountype(Account);
                                            orderFoodApi.setUserid(currentuserid);
                                            if(Account.equals("Restaurant")){
                                                startActivity(new Intent(signupchoice.this,Restaurant.class));
                                                finish();
                                            }
                                            else if(Account.equals("Customer")){
                                                startActivity(new Intent(signupchoice.this,Customer.class));
                                                finish();
                                            }

                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }

    }
}
