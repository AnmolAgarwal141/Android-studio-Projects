package com.example.mycourselist.Data;

import java.util.ArrayList;
import java.util.List;

public class CourseData {
    private String[] coursenames={"First_Course","Second_Course","Third_Course","Fourth_Course","Fifth_Course","Sixth_Course","Seventh_Course"};
    public ArrayList<Course> courselist(){
        ArrayList<Course> list = new ArrayList<>();
        for(int i=0;i<coursenames.length;i++){
            Course course =new Course(coursenames[i],coursenames[i].replace("_","").toLowerCase());
            list.add(course);

        }
        return  list;
    }
}
