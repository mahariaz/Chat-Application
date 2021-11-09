package com.mahariaz.i181652_180681;

public class UserBioStorage {
    public UserBioStorage(String email,String fname, String lname, String age) {
        this.fname = fname;
        this.lname = lname;
        this.age = age;
        this.email=email;
    }

    public UserBioStorage() {

    }

    String email,fname,lname,age;
}
