package com.example.naaniz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.naaniz.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IntroScreen extends AppCompatActivity {
    private Button login_button;
    private Button signup_button;
    private EditText phoneText;
    private boolean ans;
    private String phoneNumber;

    /*
     * TODO:
     *  Create check to see whether Log in Phone number is already in database
     * TODO:
     *   Create Auto-Log in */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);

        SharedPreferences preferences = getSharedPreferences("NAANIZ_PREFERENCES", MODE_PRIVATE);
        if ((preferences.getBoolean("current_log_in", false) == Boolean.TRUE)) {

            Intent intent = new Intent(IntroScreen.this, RestaurantProfile.class);
            intent.putExtra("name", "Default");
            intent.putExtra("phone", preferences.getString("current_phone_number", "0"));
            startActivity(intent);
        }
        phoneText = findViewById(R.id.editText);
        login_button = findViewById(R.id.intro_login_button);
        signup_button = findViewById(R.id.intro_signup_button);
        login_button.setOnClickListener(v -> {
            checkLogin();
        });
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    signup_button.setClickable(false);
//                login_button.setClickable(false);
                  checkSignUp();
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        signup_button.setClickable(true);
        login_button.setClickable(true);
    }
    private void checkLogin() {
        // TODO: Complete checkLogin
        String phoneNumber = validate(phoneText.getText().toString());
        if(phoneNumber.equals("INVALID")) {
            Toast.makeText(IntroScreen.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(phoneNumber)) {
                    //Intent intent = new Intent(IntroScreen.this, Home.class);
//                    Intent intent = new Intent(IntroScreen.this, NewHome.class);
                    Intent intent = new Intent(IntroScreen.this, RestaurantProfile.class);
                    SharedPreferences sharedPreferences = getSharedPreferences("NAANIZ_PREFERENCES", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    intent.putExtra("name","Default");
                    intent.putExtra("phone",phoneNumber);
                    User user = new User();
                    user.setPhoneNumber(phoneNumber);
                    intent.putExtra("user_details",user);
                    intent.putExtra("login",true);
                    editor.putString("current_phone_number", phoneNumber);
                    editor.putBoolean("current_log_in", Boolean.TRUE);
                    editor.apply();
                    startActivity(intent);
                } else {
                    Toast.makeText(IntroScreen.this, "No User with Entered Number", Toast.LENGTH_SHORT).show();
                    signup_button.setClickable(true);
                    login_button.setClickable(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkSignUp() {
        // TODO: Check if the user pre-exists
        phoneNumber = validate(phoneText.getText().toString());
        if(phoneNumber.equals("INVALID")) {
            Toast.makeText(IntroScreen.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(phoneNumber)) {
                    Toast.makeText(IntroScreen.this, "User with Entered Number Already Exists", Toast.LENGTH_SHORT).show();
//                    signup_button.setClickable(true);
//                    login_button.setClickable(true);
                }
                else
                    startSignUp();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                signup_button.setClickable(true);
//                login_button.setClickable(true);
            }
        });
    }

    private void startSignUp() {
        User user = new User();
        user.setPhoneNumber(phoneNumber);
//        Intent intent = new Intent(IntroScreen.this, SignUp1.class);
        Intent intent = new Intent(IntroScreen.this, VerificationActivity.class);
        intent.putExtra("user_details", user);
        intent.putExtra("login",false);
        startActivity(intent);
    }

    private String validate(String number)
    {
        String number1 = number.trim();
        String number2;
        if(number1.indexOf("+91")==0)
            number2=number1.substring(3).trim();
        else
            number2=number1;
        if(number2.length()!=10)
            return "INVALID";
        else {
            for(char c : number2.toCharArray()) {
                if(c >'9' && c <'0')
                    return "INVALID";
            }
        }
        return number2;
    }
}
