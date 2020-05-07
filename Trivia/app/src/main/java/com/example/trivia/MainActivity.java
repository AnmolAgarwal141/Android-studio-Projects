package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;
import com.example.trivia.util.Prefs;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView Question;
    private TextView Questioncounter;
    private Button Truebutton;
    private Button Falsebutton;
    private ImageButton prevbutton;
    private ImageButton nextbutton;
    private int indexcounter=0;
    private List<Question> questionlist;
    private TextView ScoreValue;
    private TextView HighestScoreValue;
    private int ScoreSum=0;
    private int maxScore=0;
    private static final String MESSAGE_ID = "message_pref";
    private Prefs prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs=new Prefs(MainActivity.this);

        Truebutton=findViewById(R.id.True_Button);
        Falsebutton=findViewById(R.id.False_Button);
        prevbutton=findViewById(R.id.prev_button);
        nextbutton=findViewById(R.id.Next_button);
        Questioncounter=findViewById(R.id.Question_counterno);
        Question=findViewById(R.id.Question_text);
         ScoreValue=findViewById(R.id.Score_value);
         HighestScoreValue=findViewById(R.id.HighestScore);


        nextbutton.setOnClickListener(this);
        prevbutton.setOnClickListener(this);
        Truebutton.setOnClickListener(this);
        Falsebutton.setOnClickListener(this);

        indexcounter=prefs.getstate();
        HighestScoreValue.setText(String.valueOf(prefs.GetHighestScore()));

        questionlist= new QuestionBank().getQuestion(new AnswerListAsyncResponse() {
           @Override
           public void processfinished(ArrayList<Question> questionArrayList) {
              Question.setText(questionArrayList.get(indexcounter).getAnswer());
              Questioncounter.setText(indexcounter+"/"+(questionlist.size()-1));
               //  Log.d("INSIDE","ON CREATE"+questionArrayList.get(questionlist.size()).getAnswer());
           }
       });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.Next_button:
                indexcounter=(indexcounter+1)%questionlist.size();
                updatequestion();

                break;
            case R.id.False_Button:
                checkAnswer(false);
                updatequestion();

                break;
            case R.id.prev_button:
                if(indexcounter==0)
                    indexcounter=questionlist.size()-1;
                else
                    indexcounter=indexcounter-1;
                updatequestion();

                break;
            case R.id.True_Button:
                checkAnswer(true);
                updatequestion();

                break;
                default:
                    break;
        }
    }
    private void SharedPrefScore(){
        SharedPreferences sharedPreferences=getSharedPreferences(MESSAGE_ID,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("message",maxScore);
        editor.apply();
    }
    private  void checkAnswer(boolean usercorrectAnswer) {
        Boolean correct =questionlist.get(indexcounter).getAnswerTrue();
        int toastMessageid=0;
        if(correct==usercorrectAnswer) {
            toastMessageid = R.string.correct_answer;
            ScoreSum+=10;
            ScoreValue.setText(String.valueOf(ScoreSum));
            fadeAnimation();
        }
        else {
            ShakeAnimation();
            ScoreSum-=10;
            if(ScoreSum<0)
                ScoreSum=0;
            ScoreValue.setText(String.valueOf(ScoreSum));
            toastMessageid = R.string.wrong_answer;
        }
        Toast.makeText(MainActivity.this,toastMessageid,Toast.LENGTH_SHORT).show();
       // Toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);

    }

    private void updatequestion() {
        Question.setText(questionlist.get(indexcounter).getAnswer());
        Questioncounter.setText(indexcounter+"/"+(questionlist.size()-1));
    }
    private void fadeAnimation(){
        final CardView cardView=findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation=new AlphaAnimation(1.0f,0.0f);
                alphaAnimation.setDuration(350);
                 alphaAnimation.setRepeatCount(1);
                 alphaAnimation.setRepeatMode(Animation.REVERSE);
                cardView.setAnimation(alphaAnimation);
                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                       cardView.setCardBackgroundColor(Color.GREEN);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        cardView.setCardBackgroundColor(Color.WHITE);
                        indexcounter=(indexcounter+1)%questionlist.size();
                        updatequestion();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
    }
    private void ShakeAnimation(){
        Animation shake= AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake_animation);
        final CardView cardView=findViewById(R.id.cardView);
        cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
              cardView.setCardBackgroundColor(Color.WHITE);
                indexcounter=(indexcounter+1)%questionlist.size();
                updatequestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        prefs.SaveHighestScore(ScoreSum);
        prefs.setstate(indexcounter);
    }

}