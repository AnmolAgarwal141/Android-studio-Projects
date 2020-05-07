package com.example.androiddrawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class CustomTextVIew extends TextView {
    private Paint textcolor;

    public CustomTextVIew(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public void init(){
        textcolor=new Paint(Paint.ANTI_ALIAS_FLAG);
       // textcolor.setTextAlign(textcolor.Align.CENTER);
        textcolor.setColor(Color.parseColor("blue"));
        textcolor.setTextSize(125f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText("HELLO WORLD",((canvas.getWidth()/2)-(getMeasuredWidth()/2)),((canvas.getHeight()/2)-(getMeasuredHeight()/2)),textcolor);
        canvas.save();
        super.onDraw(canvas);
    }
}
