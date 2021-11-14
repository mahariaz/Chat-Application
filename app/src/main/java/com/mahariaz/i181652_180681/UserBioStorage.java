package com.mahariaz.i181652_180681;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

public class UserBioStorage {
    public UserBioStorage(String dp,String email, String username,String fname, String lname, String age,String tagline) {
        this.fname = fname;
        this.lname = lname;
        this.age = age;
        this.email=email;
        this.username=username;
        this.dp=dp;
        this.tagline=tagline;

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

    String fname;
    String lname;
    String age;
    String email;
    String tagline;

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    String dp;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String username;





    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
