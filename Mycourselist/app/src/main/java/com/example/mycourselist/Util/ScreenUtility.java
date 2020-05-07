package com.example.mycourselist.Util;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

public class ScreenUtility {
    private Activity activity;
    private float dpwidth;
    private float dpheight;
    public ScreenUtility(Activity activity){
        this.activity=activity;
        Display display=activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outmetrics =new DisplayMetrics();
        display.getMetrics(outmetrics);
        float density =activity.getResources().getDisplayMetrics().density;
        dpheight=outmetrics.heightPixels/density;
        dpwidth=outmetrics.widthPixels/density;
    }

    public float getDpwidth() {
        return dpwidth;
    }

    public float getDpheight() {
        return dpheight;
    }
}
