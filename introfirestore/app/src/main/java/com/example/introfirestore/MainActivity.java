package com.example.introfirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyboardShortcutGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText entertitle, enterthought;
    private Button savebutton, showbutton,updatebutton,deletebutton;
    private TextView rectitle;// recthought;
    public static final String KEY_TITLE = "title";
    public static final String KEY_THOUGHT = "thought";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //    private DocumentReference journalref= db.document("Journal/First Thought");
    private CollectionReference collectionReference=db.collection("Journal");
    private DocumentReference journalref = db.collection("Journal").document("First Thought");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entertitle = findViewById(R.id.editTextTitle);
        enterthought = findViewById(R.id.editTextThoughts);
        savebutton = findViewById(R.id.save_button);
        updatebutton=findViewById(R.id.update_button);
        rectitle = findViewById(R.id.showtitle);
        //recthought = findViewById(R.id.showthought);
        showbutton = findViewById(R.id.show_button);
        deletebutton=findViewById(R.id.delete_button);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletemythought();
            }
        });
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatemytitle();
            }
        });

        showbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getthought();
//                journalref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if (documentSnapshot.exists()) {
//                            journal jou = documentSnapshot.toObject(journal.class);
////                            String title = documentSnapshot.getString(KEY_TITLE);
////                            String thought = documentSnapshot.getString(KEY_THOUGHT);
//                            if(jou!=null){
//                            rectitle.setText(jou.getTitle());
//                            recthought.setText(jou.getThought());}
//                        } else {
//                            Toast.makeText(MainActivity.this, "NO DATA EXISTS", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("mainActivity", "ON FAILURE: " + e.toString());
//                    }
//                });
            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addthought();
//                String title = entertitle.getText().toString().trim();
//                String thought = enterthought.getText().toString().trim();
//
//                journal jou=new journal();
//                jou.setTitle(title);
//                jou.setThought(thought);
//
//                //Map<String, Object> data = new HashMap<>();
//                //data.put(KEY_TITLE, title);
//                //data.put(KEY_THOUGHT, thought);
//                journalref.set(jou).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("mainActivity", "ON FAILURE: " + e.toString());
//
//                    }
//                });
            }
        });

    }
    private void deletemythought(){
//        Map<String,Object>data=new HashMap<>();
//        data.put(KEY_THOUGHT,FieldValue.delete());
//        journalref.update(data);
        journalref.update(KEY_THOUGHT, FieldValue.delete());
        //journalref.delete(); for delete all
    }
    private void updatemytitle(){
        String title=entertitle.getText().toString().trim();
        //String thought=enterthought.getText().toString().trim();
        Map<String ,Object> data=new HashMap<>();
        data.put(KEY_TITLE,title);
        //data.put(KEY_THOUGHT,thought);
        journalref.update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this,"Updated",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,"Update Fails",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addthought(){
        String title = entertitle.getText().toString().trim();
        String thought = enterthought.getText().toString().trim();

        journal jou=new journal();
        jou.setTitle(title);
        jou.setThought(thought);
        collectionReference.add(jou);
    }
    private void getthought(){
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data="";
                for(QueryDocumentSnapshot snapshots:queryDocumentSnapshots){
                    journal jou =snapshots.toObject(journal.class);


                    data+="Title: "+jou.getTitle()+"\nThought: "+jou.getThought()+"\n\n";
                    rectitle.setText(data);
                    //rectitle.setText(jou.getTitle());
                    //recthought.setText(jou.getThought());
                }
                rectitle.setText(data);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        collectionReference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
                String data="";
                assert queryDocumentSnapshots != null;
                for(QueryDocumentSnapshot snapshots:queryDocumentSnapshots){
                    journal jou =snapshots.toObject(journal.class);


                    data+="Title: "+jou.getTitle()+"\nThought: "+jou.getThought()+"\n\n";
                    rectitle.setText(data);
                    //rectitle.setText(jou.getTitle());
                    //recthought.setText(jou.getThought());
                }
                rectitle.setText(data);

            }

        });
//        journalref.addSnapshotListener(this,new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                if (e != null) {
//                    Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
//                }
//                if (documentSnapshot.exists()) {
////                    String title = documentSnapshot.getString(KEY_TITLE);
////                    String thought = documentSnapshot.getString(KEY_THOUGHT);
////                    rectitle.setText(title);
////                    recthought.setText(thought);
//                    journal jou = documentSnapshot.toObject(journal.class);
//                    String data="";
//                    if (jou != null) {
//                        data+="Title: "+jou.getTitle()+"Thought: "+jou.getThought();
//                        rectitle.setText(data);
//                        // recthought.setText(jou.getThought());}
//
//                    } else {
//
//                        data+="Title: "+jou.getTitle()+"Thought: "+jou.getThought();
//                        rectitle.setText(data);
//                        //recthought.setText("");
//                    }
//                }
//            }
//        });
    }
}


