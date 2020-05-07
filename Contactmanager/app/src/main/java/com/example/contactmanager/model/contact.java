package com.example.contactmanager.model;

public class contact {
    private int id;
    private String Name;
    private String Phonenumber;

    public contact(){}

    public contact(int id, String name, String phonenumber) {
        this.id = id;
        Name = name;
        Phonenumber = phonenumber;
    }
    public contact(String name ,String phonenumber)
    {
        Name=name;
        Phonenumber=phonenumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }
}
