package com.example.naaniz.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemRetrofit {
    @SerializedName("id")
    @Expose
    private VideoIdRetrofit id;

    @SerializedName("snippet")
    @Expose
    private SnippetRetrofit snippet;

    public VideoIdRetrofit getId() {
        return id;
    }

    public void setId(VideoIdRetrofit id) {
        this.id = id;
    }

    public SnippetRetrofit getSnippet() {
        return snippet;
    }

    public void setSnippet(SnippetRetrofit snippet) {
        this.snippet = snippet;
    }
}
