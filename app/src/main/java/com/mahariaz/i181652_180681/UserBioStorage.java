package com.mahariaz.i181652_180681;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

public class UserBioStorage {
    public UserBioStorage(String email, String fname, String lname, String age) {
        this.fname = fname;
        this.lname = lname;
        this.age = age;
        this.email=email;

    }

    public UserBioStorage() {

    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    String fname,lname,age,email;





    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
