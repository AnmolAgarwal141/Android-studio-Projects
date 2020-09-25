package com.example.naaniz.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.naaniz.retrofit.ItemRetrofit;
import com.example.naaniz.R;
import com.example.naaniz.retrofit.SnippetRetrofit;
import com.example.naaniz.retrofit.VideoIdRetrofit;
import com.example.naaniz.YouTubeConfig;
import com.example.naaniz.retrofit.YouTubeRetrofit;
import com.example.naaniz.interfaces.YouTubeApi;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class YouTubeFragment extends Fragment {
    private YouTubeApi api;
    private Button b1, b2, b3, b4, b5, s1, s2, s3, s4, s5, c1, c2, c3, c4;
    private SearchView searchView;
    private List<String> videoList, channelList;
    private String query, video;
    private static int flag = 0, check = 0;
    private TextView textView;
    private ImageView imageView;

    FirebaseDatabase database;
    DatabaseReference myRef;

    private String trand1, trand2, trand3, trand4, trand5;
    int viewCount, tranding1, tranding2, tranding3, tranding4, tranding5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_youtube, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        query = "";

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("youtube_trandings");
        viewCount = 1;
        tranding1 = 0;
        tranding2 = 0;
        tranding3 = 0;
        tranding4 = 0;
        tranding5 = 0;

        b1 = view.findViewById(R.id.button1);
        b2 = view.findViewById(R.id.button2);
        b3 = view.findViewById(R.id.button3);
        b4 = view.findViewById(R.id.button4);
        b5 = view.findViewById(R.id.button5);

        c1 = view.findViewById(R.id.punjabi);
        c2 = view.findViewById(R.id.gujarati);
        c3 = view.findViewById(R.id.bengali);
        c4 = view.findViewById(R.id.kashmiri);

        s1 = view.findViewById(R.id.bsearch1);
        s2 = view.findViewById(R.id.bsearch2);
        s3 = view.findViewById(R.id.bsearch3);
        s4 = view.findViewById(R.id.bsearch4);
        s5 = view.findViewById(R.id.bsearch5);

        textView = (TextView) view.findViewById(R.id.top_searches);
        imageView = (ImageView) view.findViewById(R.id.fire_icon);

        if (flag == 0) {
            s1.setVisibility(View.GONE);
            s2.setVisibility(View.GONE);
            s3.setVisibility(View.GONE);
            s4.setVisibility(View.GONE);
            s5.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
        }

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot child : dataSnapshot.getChildren()){

                    if(Integer.parseInt((String) child.getValue()) > tranding1){

                        tranding1 = Integer.parseInt((String) child.getValue());
                        trand1 = child.getKey();
                    }
                }

                for(DataSnapshot child : dataSnapshot.getChildren()){

                    if(Integer.parseInt((String) child.getValue()) > tranding2 && !child.getKey().equals(trand1)){

                        tranding2 = Integer.parseInt((String) child.getValue());
                        trand2 = child.getKey();
                    }
                }

                for(DataSnapshot child : dataSnapshot.getChildren()){

                    if(Integer.parseInt((String) child.getValue()) > tranding3 && !child.getKey().equals(trand1) && !child.getKey().equals(trand2)){

                        tranding3 = Integer.parseInt((String) child.getValue());
                        trand3 = child.getKey();
                    }
                }

                for(DataSnapshot child : dataSnapshot.getChildren()){

                    if(Integer.parseInt((String) child.getValue()) > tranding4 && !child.getKey().equals(trand1) && !child.getKey().equals(trand2) && !child.getKey().equals(trand3)){

                        tranding4 = Integer.parseInt((String) child.getValue());
                        trand4 = child.getKey();
                    }
                }

                for(DataSnapshot child : dataSnapshot.getChildren()){

                    if(Integer.parseInt((String) child.getValue()) > tranding5 && !child.getKey().equals(trand1)&& !child.getKey().equals(trand2) && !child.getKey().equals(trand3) && !child.getKey().equals(trand4)){

                        tranding5 = Integer.parseInt((String) child.getValue());
                        trand5 = child.getKey();
                    }
                }

                b1.setText(String.valueOf(trand1));
                b2.setText(String.valueOf(trand2));
                b3.setText(String.valueOf(trand3));
                b4.setText(String.valueOf(trand4));
                b5.setText(String.valueOf(trand5));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        searchView = (SearchView) view.findViewById(R.id.search_youtube);

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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                query = searchView.getQuery().toString().toLowerCase();
                check = 1;
                getVideos();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                video = videoList.get(0);
                playVideo();
            }
        });
        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                video = videoList.get(1);
                playVideo();
            }
        });
        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                video = videoList.get(2);
                playVideo();
            }
        });
        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                video = videoList.get(3);
                playVideo();
            }
        });
        s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                video = videoList.get(4);
                playVideo();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = b1.getText().toString();
                check = 1;
                getVideos();
                //playVideo();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = b2.getText().toString();
                check = 1;
                getVideos();
                //playVideo();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = b3.getText().toString();
                check = 1;
                getVideos();
                //playVideo();
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = b4.getText().toString();
                check = 1;
                getVideos();
                //playVideo();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = b5.getText().toString();
                check = 1;
                getVideos();
                //playVideo();
            }
        });
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = c1.getText().toString();
                check = 1;
                getVideos();
                //playVideo();
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = c2.getText().toString();
                check = 1;
                getVideos();
                //playVideo();
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = c3.getText().toString();
                check = 1;
                getVideos();
                //playVideo();
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = c4.getText().toString();
                check = 1;
                getVideos();
                //playVideo();
            }
        });
    }

    public void getVideos() {

        if (query.equals(""))
            return;
        Call<YouTubeRetrofit> call = api.getVideos("snippet",query + "recipes hindi", YouTubeConfig.getGetApiKey(), "video");

        call.enqueue(new Callback<YouTubeRetrofit>() {
            @Override
            public void onResponse(Call<YouTubeRetrofit> call, Response<YouTubeRetrofit> response) {
                if (!response.isSuccessful()) {
                    b1.setText("Code: " + response.code());
                    return;
                }

                videoList = new ArrayList<>();
                channelList = new ArrayList<>();
                YouTubeRetrofit youTubeRetrofit = response.body();
                List<ItemRetrofit> items = youTubeRetrofit.getItems();
                for (ItemRetrofit item : items) {
                    VideoIdRetrofit videoId = item.getId();
                    SnippetRetrofit snippet = item.getSnippet();

                    videoList.add(videoId.getVideoId());
                    channelList.add(snippet.getChannelTitle());
                }

                if (flag == 0) {
                    s1.setVisibility(View.VISIBLE);
                    s2.setVisibility(View.VISIBLE);
                    s3.setVisibility(View.VISIBLE);
                    s4.setVisibility(View.VISIBLE);
                    s5.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                }

                s1.setText(channelList.get(0));
                s2.setText(channelList.get(1));
                s3.setText(channelList.get(2));
                s4.setText(channelList.get(3));
                s5.setText(channelList.get(4));

                if (check == 1) {
                    video = videoList.get(0);
                    check = 0;
                    playVideo();
                }
            }

            @Override
            public void onFailure(Call<YouTubeRetrofit> call, Throwable t) {
                b1.setText(t.getMessage());
            }
        });

    }

    public void playVideo(){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(query)) {
                    // run some code

                    myRef.child(query).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            // data available in snapshot.value()
                            String count = snapshot.getValue(String.class);

                            int newCount = Integer.parseInt(count);
                            newCount++;
                            myRef.child(query).setValue(String.valueOf(newCount));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {

                    myRef.child(query).setValue(String.valueOf(viewCount));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        YouTubePlayerSupportFragmentX youTubePlayerFragment = YouTubePlayerSupportFragmentX.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.youtube_view, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(YouTubeConfig.getGetApiKey(), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(video);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                String errorMessage = youTubeInitializationResult.toString();
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });
    }
}