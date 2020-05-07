package com.example.truecitizenquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button truebutton;
    private Button falsebutton;
    private TextView question_text;
    private ImageButton Nextbutton;
    private ImageButton Prevbutton;
    private int Counter=0;
    private Question[] question=new Question[]{
        new Question(R.string.Answer_text,false),
        new Question(R.string.Que2,true),
            new Question(R.string.Que3,true),

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        truebutton=findViewById(R.id.True_Button);
        falsebutton=findViewById(R.id.False_Button);
        question_text=findViewById(R.id.answer_text);
        Nextbutton=findViewById(R.id.Next_button);
        Prevbutton=findViewById(R.id.Prev_button);

        truebutton.setOnClickListener(this);
        falsebutton.setOnClickListener(this);
        Nextbutton.setOnClickListener(this);
        Prevbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.True_Button:
                checkAnswer(true);
                break;
            case R.id.False_Button:
                checkAnswer(false);
                break;
            case R.id.Next_button:
                Counter=(Counter+1)%question.length;
                question_text.setText( question[Counter].getAnswerResid());
                break;
            case R.id.Prev_button:
                if(Counter==0)
                    Counter=question.length-1;
                else
                Counter=Counter-1;
                question_text.setText( question[Counter].getAnswerResid());
                break;
                default:
                    break;
        }
    }
    public void checkAnswer(Boolean Selected)
    {
         Boolean CorrectAnswers=question[Counter].getAnswerTrue();
        int toastMessageid;
        if(Selected==CorrectAnswers)
        {
            toastMessageid=R.string.Correct;
        }
        else
        {
            toastMessageid=R.string.Incorrect;
        }
        Toast.makeText(MainActivity.this,toastMessageid,Toast.LENGTH_SHORT).show();
    }
}
