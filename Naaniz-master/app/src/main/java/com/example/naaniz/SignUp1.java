package com.example.naaniz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.naaniz.models.User;

public class SignUp1 extends AppCompatActivity {
    private EditText name_text;
    private EditText phone_text;
    private EditText pass_text;
    private Button nextButton, signUpAsVender;
    private SwitchCompat foodSwitch;
    private EditText repass_text;

    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up1);
        name_text = findViewById(R.id.name_text);
        foodSwitch = findViewById(R.id.food_choice_switch);
        nextButton = findViewById(R.id.button);
        signUpAsVender = findViewById(R.id.vendorSignUp);
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
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name_text.getText().toString().length() > 0 ) {
                    User user = (User) getIntent().getSerializableExtra("user_details");
                    if(user==null) return;
                    flag = 0;
                    Intent intent = new Intent(SignUp1.this, SignUp2.class);
                    user.setUsername(name_text.getText().toString());
                    user.setFood_choice(foodSwitch.getText().toString());
                    intent.putExtra("user_details",user);
                    intent.putExtra("user_type",flag);
                    startActivity(intent);
                }
                else {
                    Toast toast = Toast.makeText(SignUp1.this,"Invalid Name", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        signUpAsVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name_text.getText().toString().length() > 0 ) {
                    User user = (User) getIntent().getSerializableExtra("user_details");
                    if(user==null) return;
                    flag = 1;
                    Intent intent = new Intent(SignUp1.this, SignUp2.class);
                    user.setUsername(name_text.getText().toString());
                    user.setFood_choice(foodSwitch.getText().toString());
                    intent.putExtra("user_details",user);
                    intent.putExtra("user_type",flag);
                    startActivity(intent);
                }
                else {
                    Toast toast = Toast.makeText(SignUp1.this,"Invalid Name", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }
}
