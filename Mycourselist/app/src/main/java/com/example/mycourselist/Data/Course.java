package com.example.mycourselist.Data;

import android.content.Context;

public class Course {
    private String Coursename;
    private String CourseImage;

    public Course(String coursename, String courseImage) {
        Coursename = coursename;
        CourseImage = courseImage;
    }

    public int getImageResourceid(Context context){
        return context.getResources().getIdentifier(this.CourseImage,"drawable",context.getPackageName());
    }

    public String getCoursename() {
        return Coursename;
    }

    public void setCoursename(String coursename) {
        Coursename = coursename;
    }

    public String getCourseImage() {
        return CourseImage;
    }

    public void setCourseImage(String courseImage) {
        CourseImage = courseImage;
    }
}
