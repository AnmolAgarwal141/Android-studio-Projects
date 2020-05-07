package com.example.tripplanner.Model;

public class Register {
    String Userid;
    String Password;
    public Register()
    {

    }

    public Register(String userid, String password) {
        Userid = userid;
        Password = password;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
