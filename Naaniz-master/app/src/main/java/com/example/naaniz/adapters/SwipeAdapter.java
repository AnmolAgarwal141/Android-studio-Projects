package com.example.naaniz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.naaniz.R;

public class SwipeAdapter extends PagerAdapter {

    private int[] images = {R.drawable.slider2, R.drawable.slider1, R.drawable.slider3};
    private int[] writeUp1 = {R.string.get_your_fo, R.string.receive_rec, R.string.get_your_in};
    private int[] writeUp2 = {R.string.submit_all_, R.string.recipes_spe, R.string.browse_thro};
    private Context context;
    private LayoutInflater inflater;

    public SwipeAdapter (Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.swipe_onboarding, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.swipe_image);
        TextView textView1 = (TextView) view.findViewById(R.id.text_view1);
        TextView textView2 = (TextView) view.findViewById(R.id.text_view2);

        textView1.setText(writeUp1[position]);
        textView2.setText(writeUp2[position]);
        imageView.setImageResource(images[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
