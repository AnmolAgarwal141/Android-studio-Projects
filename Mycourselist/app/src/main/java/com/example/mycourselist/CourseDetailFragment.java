package com.example.mycourselist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycourselist.Data.Course;
import com.example.mycourselist.Data.CourseData;

public class CourseDetailFragment extends Fragment {
    Course course;
    public CourseDetailFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        if(bundle!=null && bundle.containsKey("course_id")){
            int position=bundle.getInt("course_id");
            course=new CourseData().courselist().get(position);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_detail_fragment,container,false);
        if(course!=null){
            TextView coursename=view.findViewById(R.id.detailcoursename);
            coursename.setText(course.getCoursename());
            ImageView courseimage=view.findViewById(R.id.detailCourseImage);
            courseimage.setImageResource(course.getImageResourceid(getActivity()));
            TextView coursedescription=view.findViewById(R.id.detailcoursedescription);
            coursedescription.setText(course.getCoursename());
        }
        return view;
    }
}
