package com.example.trivia.controller;

import android.app.Application;

import android.text.TextUtils;


import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;



public class Appcontroller extends Application {
    private static Appcontroller instance;
    public static final String TAG=Appcontroller.class.getSimpleName();
    private RequestQueue requestQueue;

    public static synchronized Appcontroller getInstance() {
        return instance;
    }
    public void onCreate(){
        super.onCreate();
        instance=this;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {

            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req,String tag) {
        req.setTag(TextUtils.isEmpty(tag)?TAG:tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
    public void cancelPendingRequests(Object tag)
    {
        if(requestQueue!=null)
            requestQueue.cancelAll(tag);
    }



}

