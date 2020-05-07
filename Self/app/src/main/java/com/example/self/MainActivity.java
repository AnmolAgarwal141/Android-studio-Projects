package com.example.self;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import model.Journal;
import util.JournalApi;

public class MainActivity extends AppCompatActivity {
    private Button getstarted;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentuser;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference collectionReference=db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        firebaseAuth=FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               currentuser=firebaseAuth.getCurrentUser();
               if(currentuser!=null){
                   currentuser=firebaseAuth.getCurrentUser();
                   String currentuserid=currentuser.getUid();
                   collectionReference.whereEqualTo("userid",currentuserid).addSnapshotListener(new EventListener<QuerySnapshot>() {
                       @Override
                       public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                           if(e!=null){

                           }
                           String name;
                           if(!queryDocumentSnapshots.isEmpty()){
                               for(QueryDocumentSnapshot snapshot:queryDocumentSnapshots){
                                   JournalApi journalApi= JournalApi.getInstance();
                                   journalApi.setUserid(snapshot.getString("userid"));
                                   journalApi.setUsername(snapshot.getString("username"));
                                   startActivity(new Intent(MainActivity.this,JournalListActivity.class));
                                   finish();
                               }
                           }
                       }
                   });
               }
               else {

               }
            }
        };
        getstarted=findViewById(R.id.startbutton);
        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentuser=firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(firebaseAuth!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}
