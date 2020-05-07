package com.example.trivia.util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Activity activity) {
        this.preferences = activity.getPreferences(activity.MODE_PRIVATE);
    }
    public void SaveHighestScore(int score)
    {
        int CurrentScore=score;
       int lastscore= preferences.getInt("high_score",0);
        if(CurrentScore>lastscore){
            preferences.edit().putInt("high_score",CurrentScore).apply();
        }


    }
    public int GetHighestScore()
    {
        return preferences.getInt("high_score",0);
    }
    public void setstate(int index){
        preferences.edit().putInt("index_state",index).apply();
    }
    public int getstate(){
        return preferences.getInt("index_state",0);
    }
}
