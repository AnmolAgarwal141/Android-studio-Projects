package com.example.self;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.Objects;

import model.Journal;
import util.JournalApi;

public class PostJournalActivity extends AppCompatActivity implements View.OnClickListener {
    private Button savebutton;
    private ProgressBar progressBar;
    private ImageView addphotobutton,imageView;
    private EditText titleedittext,thoughtedittext;
    private String currentuserid;
    private String currentusername;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private TextView CurrentUserTextView;
    private CollectionReference collectionReference=db.collection("Journal");
    private static  final int GALLERY_CODE=1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_journal);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.postprogressBar);
        titleedittext=findViewById(R.id.posttitleet);
        thoughtedittext=findViewById(R.id.postthoughtet);
        CurrentUserTextView=findViewById(R.id.postusernametextview);
        imageView=findViewById(R.id.postimageView);
        storageReference= FirebaseStorage.getInstance().getReference();

        savebutton=findViewById(R.id.postsavejournalbutton);
        savebutton.setOnClickListener(this);
        addphotobutton=findViewById(R.id.postcamerabutton);
        addphotobutton.setOnClickListener(this);
        progressBar.setVisibility(View.INVISIBLE);

        if(JournalApi.getInstance()!=null){
            currentuserid=JournalApi.getInstance().getUserid();
            currentusername=JournalApi.getInstance().getUsername();
            CurrentUserTextView.setText(currentusername);
        }
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               user=firebaseAuth.getCurrentUser();
               if(user!=null){

               }
               else {

               }
            }
        };


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.postcamerabutton:
                Intent galleryintent=new Intent(Intent.ACTION_GET_CONTENT);
                galleryintent.setType("image/*");
                startActivityForResult(galleryintent,GALLERY_CODE);
                break;
            case R.id.postsavejournalbutton:
                saveJournal();
                break;
        }
    }
    private void saveJournal(){
        final String Title=titleedittext.getText().toString().trim();
        final String Thoughts=thoughtedittext.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(Title)&& !TextUtils.isEmpty(Thoughts)&&imageUri!=null){
            final StorageReference filepath=storageReference.child("journal_images").child("image_"+ Timestamp.now().getSeconds());
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageurl=uri.toString();
                            Journal journal=new Journal();
                            journal.setTitle(Title);
                            journal.setThought(Thoughts);
                            journal.setImageUri(imageurl);
                            journal.setTimeadded(new Timestamp(new Date()));
                            journal.setUsername(currentusername);
                            journal.setUserid(currentuserid);


                            collectionReference.add(journal).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    startActivity(new Intent(PostJournalActivity.this,JournalListActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("activitypostjournal","on failure"+e.toString());
                                }
                            });
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
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        user=firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseAuth!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_CODE && resultCode==RESULT_OK){
            if(data!=null){
                imageUri=data.getData();
                imageView.setImageURI(imageUri);
            }
        }
    }
}
