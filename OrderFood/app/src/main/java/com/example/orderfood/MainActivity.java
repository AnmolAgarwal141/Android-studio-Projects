package com.example.orderfood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import util.OrderFoodApi;

public class MainActivity extends AppCompatActivity {
    private EditText email,password;
    private Button login_button,signup_button;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference collectionReference=db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        firebaseAuth= FirebaseAuth.getInstance();
        email=findViewById(R.id.emaileditText);
        password=findViewById(R.id.passwordeditText);
        login_button=findViewById(R.id.loginbutton);
        signup_button=findViewById(R.id.signupbutton);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,signupchoice.class);
                startActivity(intent);
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginemailandpassword(email.getText().toString(),password.getText().toString());
            }
        });
    }

    private void loginemailandpassword(String email, final String password) {
        if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)){
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    user=firebaseAuth.getCurrentUser();
                    final String currentuserid=user.getUid();
                    collectionReference.whereEqualTo("userid",currentuserid).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if(e!=null){

                            }
                            else{
                                if(!queryDocumentSnapshots.isEmpty()){
                                    for(QueryDocumentSnapshot snapshot:queryDocumentSnapshots){
                                        OrderFoodApi orderFoodApi=OrderFoodApi.getInstance();
                                        orderFoodApi.setAccountype(snapshot.getString("Accounttype"));
                                        orderFoodApi.setUserid(currentuserid);
                                        if(orderFoodApi.getAccountype().equals("Restaurant")){
                                            startActivity(new Intent(MainActivity.this,Restaurant.class));
                                        }
                                        else if(orderFoodApi.getAccountype().equals("Customer")){
                                            startActivity(new Intent(MainActivity.this,Customer.class));
                                        }

                                    }

                                }

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
        else {
            Toast.makeText(this, "EMPTY FIELDS NOT ALLOWED", Toast.LENGTH_SHORT).show();
        }
    }
}
