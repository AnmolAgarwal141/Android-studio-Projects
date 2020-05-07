package com.example.tripplanner.Model;

public class Registerdetails {
    String id;
    String email;
    String Name;
    String Phone;
    String Bdate;

    public Registerdetails(String id, String email, String name, String phone, String bdate) {
        this.id = id;
        this.email = email;
        Name = name;
        Phone = phone;
        Bdate = bdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getBdate() {
        return Bdate;
    }

    public void setBdate(String bdate) {
        Bdate = bdate;
    }
}
