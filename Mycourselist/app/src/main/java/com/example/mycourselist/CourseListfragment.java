package com.example.mycourselist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.mycourselist.Data.Course;
import com.example.mycourselist.Data.CourseArrayAdapter;
import com.example.mycourselist.Data.CourseData;
import com.example.mycourselist.Util.ScreenUtility;

import java.util.List;

public class CourseListfragment extends ListFragment {
    List<Course> courses =new CourseData().courselist();
    private Callbacks activitty;
    public CourseListfragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_list_fragment,container,false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtility screenUtility=new ScreenUtility(getActivity());

        CourseArrayAdapter adapter = new CourseArrayAdapter(getActivity(),R.layout.course_listittem,courses);
        setListAdapter(adapter);

    }
    public interface Callbacks{
        public void onItemSelected(Course course,int position);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Course course=courses.get(position);
        this.activitty.onItemSelected(course,position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activitty=(Callbacks)context;
    }
}
