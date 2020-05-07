package com.example.androiddrawing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.view.View;


public class Drawing extends View {
    private Paint brush;
    private Paint redbrush;
    private LinearGradient linearGradient;
    private RadialGradient radialGradient;
    private SweepGradient sweepGradient;
    private Bitmap bitmap;
    public Drawing(Context context) {
        super(context);
        init();
    }
    public void init(){
        //sweepGradient=new SweepGradient()
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.download);
        radialGradient=new RadialGradient(0,0,20f,Color.GREEN, Color.BLUE,Shader.TileMode.MIRROR);
        linearGradient=new LinearGradient(0,0,200,200,Color.RED,Color.BLACK, Shader.TileMode.REPEAT);
        brush=new Paint(Paint.ANTI_ALIAS_FLAG);
        brush.setColor(Color.parseColor("green"));
        redbrush=new Paint(Paint.ANTI_ALIAS_FLAG);
        redbrush.setColor(Color.RED);
        redbrush.setStrokeWidth(13f);
       brush.setShader(linearGradient);
       // brush.setShader(radialGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(bitmap,((getMeasuredWidth()/2)-(bitmap.getWidth()/2)),((getMeasuredHeight()/2)-(bitmap.getHeight()/2)),null);
        //canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,308f,brush);
        //canvas.drawLine(0,0,getMeasuredWidth()/2,getMeasuredHeight()/2,redbrush);
        canvas.save();
        super.onDraw(canvas);
    }
}
