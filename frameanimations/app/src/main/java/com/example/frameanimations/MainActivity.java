package com.example.frameanimations;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private AnimationDrawable batAnimation;
    private ImageView batimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        batimage=findViewById(R.id.imageView);
        //batimage.setBackgroundResource(R.drawable.bat_anim);
        //batAnimation=(AnimationDrawable)batimage.getBackground();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //batAnimation.start();
        Handler mhandler = new Handler();
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //batAnimation.stop();
                Animation startanimation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein_animation);
                batimage.startAnimation(startanimation);
            }
        },50);
        return super.onTouchEvent(event);

    }
}
