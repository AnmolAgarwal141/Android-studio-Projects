package com.example.mycourselist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.mycourselist.Data.Course;

public class MainActivity extends AppCompatActivity implements CourseListfragment.Callbacks {
    private boolean isTwopane=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.detailContainer)!=null){
            isTwopane=true;
        }

    }

    @Override
    public void onItemSelected(Course course,int Position) {
        if (isTwopane) {
            Bundle bundle=new Bundle();
            bundle.putInt("course_id",Position);
            FragmentManager fm = getSupportFragmentManager();
            CourseDetailFragment courseDetailFragment = new CourseDetailFragment();
            courseDetailFragment.setArguments(bundle);
            if (courseDetailFragment == null) {
                fm.beginTransaction().replace(R.id.detailContainer, courseDetailFragment).commit();
            }
        } else {
            Intent intent = new Intent(MainActivity.this, CourseDetailActivity.class);
            intent.putExtra("course_id", Position);
            startActivity(intent);
        }
    }
}
