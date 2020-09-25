package com.example.naaniz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.naaniz.interfaces.YouTubeApi;
import com.example.naaniz.retrofit.ItemRetrofit;
import com.example.naaniz.retrofit.SnippetRetrofit;
import com.example.naaniz.retrofit.VideoIdRetrofit;
import com.example.naaniz.retrofit.YouTubeRetrofit;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YouTubeShowRecipe extends YouTubeBaseActivity {
    private YouTubeApi api;
    private Button button;
    private List<String> videoList;
    private String query;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    private YouTubePlayerView youTubePlayerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_show_reipe);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        api = retrofit.create(YouTubeApi.class);
        button = findViewById(R.id.play_button);
        youTubePlayerView = findViewById(R.id.youtube_view);
        query = getIntent().getStringExtra("query");

        Call<YouTubeRetrofit> call = api.getVideos("snippet",query + "recipes hindi", YouTubeConfig.getGetApiKey(), "video");

        call.enqueue(new Callback<YouTubeRetrofit>() {
            @Override
            public void onResponse(Call<YouTubeRetrofit> call, Response<YouTubeRetrofit> response) {
                if (!response.isSuccessful()) {
                    button.setText("Code: " + response.code());
                    return;
                }

                videoList = new ArrayList<>();
                YouTubeRetrofit youTubeRetrofit = response.body();
                List<ItemRetrofit> items = youTubeRetrofit.getItems();
                for (ItemRetrofit item : items) {
                    VideoIdRetrofit videoId = item.getId();
                    SnippetRetrofit snippet = item.getSnippet();

                    videoList.add(videoId.getVideoId());
                }


            }

            @Override
            public void onFailure(Call<YouTubeRetrofit> call, Throwable t) {
                button.setText(t.getMessage());
            }
        });

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.setShowFullscreenButton(false);
                youTubePlayer.loadVideos(videoList);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youTubePlayerView.initialize(YouTubeConfig.getGetApiKey(), onInitializedListener);
            }
        });

    }
}
