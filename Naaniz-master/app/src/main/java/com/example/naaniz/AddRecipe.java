package com.example.naaniz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddRecipe extends AppCompatActivity {

    EditText title, servings,  min, ingredient_name, description,ingredient_qnt;
    TextView show_ingredient_name;
    private SwitchCompat foodSwitch;
    private ArrayList<UserRecipeIngredients> a=new ArrayList<>();
    String all_ingredients;
    private int SELECT_PICTURE=1;
    private Uri uri=null;


    public void add(View view){

        if(show_ingredient_name.getText().toString().equals("Ingredient List") && !ingredient_name.getText().toString().equals("")){

            show_ingredient_name.setText("");
        }

        if(!ingredient_name.getText().toString().equals("") && !ingredient_qnt.getText().toString().equals("")){

            all_ingredients = ingredient_name.getText().toString();
            show_ingredient_name.append("+ " + all_ingredients + " : " + ingredient_qnt.getText().toString().trim() +"\n");
            a.add(new UserRecipeIngredients(ingredient_name.getText().toString().trim(),ingredient_qnt.getText().toString().trim()));
            ingredient_name.setText("");
            ingredient_qnt.setText("");
        }
        else {
            Toast.makeText(this, "Enter ingredient name first", Toast.LENGTH_SHORT).show();
        }
    }

    public void submit(View view){
        String phone_user = getIntent().getStringExtra("phone");
        Log.i("DSFdsf", phone_user);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference parentRef = database.getReference("user_recipes");
        DatabaseReference pref = parentRef.child(getIntent().getStringExtra("phone")).getRef();
        DatabaseReference ref = pref.child(title.getText().toString().trim()).getRef();


        DatabaseReference ref2 = ref.child("ingredients").getRef();

        String des = description.getText().toString().trim();
        String tit = title.getText().toString().trim();
        String mi = min.getText().toString().trim();
        String serv = servings.getText().toString().trim();

        if (des.equals("") || tit.equals("") || mi.equals("") || serv.equals("") || uri == null) {
            Toast.makeText(AddRecipe.this, "Fill all Details first.", Toast.LENGTH_SHORT).show();
            return;
        }

        ref.child("title").setValue(tit);
        ref.child("instructions").setValue(des);
        ref.child("readyInMinutes").setValue(mi);
        ref.child("servings").setValue(serv);
        ref.child("name").setValue(getIntent().getStringExtra("name"));
        ref.child("status").setValue("uploaded");
        ref.child("phone").setValue(getIntent().getStringExtra("phone"));
        ref.child("views").setValue(0);
        ref.child("choice").setValue(foodSwitch.getText().toString());


        int i;
        Log.i("213123",getIntent().getStringExtra("name") );
        for(i=0;i<a.size();i++)
        {
            Log.i("213123",getIntent().getStringExtra("name") );
            String name = a.get(i).getNameUser();
            String qnt = a.get(i).getQnt();
            DatabaseReference ref3 = ref2.child(String.valueOf(i)).getRef();
            ref3.child("name").setValue(name);
            ref3.child("qty").setValue(qnt);
        }

        FirebaseStorage storageReference = FirebaseStorage.getInstance();
        StorageReference imgRef = storageReference.getReference();
        imgRef.child("users/"+getIntent().getStringExtra("phone")+"/"+tit).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });


        Intent intent = new Intent(AddRecipe.this, ShowRecipe.class);
        intent.putExtra("dish",title.getText().toString().trim());
        intent.putExtra("parentRef","user_recipes");
        intent.putExtra("phone",getIntent().getStringExtra("phone"));
        startActivity(intent);
       finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        Log.d("IntentNow","AddRecipe : "+ getIntent().getStringExtra("phone"));
        title = findViewById(R.id.title);
        servings = findViewById(R.id.servings);
        min = findViewById(R.id.min);
        ingredient_name = findViewById(R.id.ingredient_name);
        ingredient_qnt = findViewById(R.id.ingredient_qnt);
        description = findViewById(R.id.description);

        show_ingredient_name = findViewById(R.id.show_ingredient_name);
        show_ingredient_name.setText("Ingredient List");

        Button button = (Button)findViewById(R.id.recipe_upload_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Photo"),SELECT_PICTURE);
            }
        });
        foodSwitch = findViewById(R.id.food_choice_add);
        foodSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    foodSwitch.setText(R.string.non_veg);
                } else {
                    foodSwitch.setText(R.string.veg);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            if(requestCode==SELECT_PICTURE) {
                uri=data.getData();
                ImageView imageView = (ImageView)findViewById(R.id.recipe_image);
                Picasso.get().load(data.getData()).noPlaceholder().centerCrop().fit().into((ImageView) imageView);
            }
        }
    }
}
