package com.example.playmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

//http://buildappswithpaulo.com/music/watch_me.mp3
public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Button playbut;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playbut=findViewById(R.id.playbutton);
        seekBar=findViewById(R.id.seekBar);
        mediaPlayer=new MediaPlayer();
        try {
            mediaPlayer.setDataSource("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
               // Toast.makeText(MainActivity.this,"duration:"+String.valueOf((mediaPlayer.getDuration()/1000)),Toast.LENGTH_SHORT).show();
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                seekBar.setMax(mediaPlayer.getDuration());
                Toast.makeText(MainActivity.this,"CAN'T OPEN",Toast.LENGTH_SHORT).show();
                playbut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mp.isPlaying()){
                            mp.pause();
                            playbut.setText("Play");
                        }
                        else {
                            mp.start();

                            playbut.setText("Pause");
                        }
                    }
                });
            }
        });
        mediaPlayer.prepareAsync();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        // mediaPlayer=MediaPlayer.create(this,R.raw.complete);
    }
    private void pausemusic(){
        if(mediaPlayer!=null){
            mediaPlayer.pause();
            playbut.setText("Play");
        }

    }
    private void playmusic(){
        if(mediaPlayer!=null){
            mediaPlayer.start();
            playbut.setText("Pause");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.pause();
            mediaPlayer.release();
        }
    }
}
