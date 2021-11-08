package com.mahariaz.i181652_180681;

public class UsersMessageStorage {
    public UsersMessageStorage(String to_phone, String from_phone, String message, String time) {
        this.to_phone = to_phone;
        this.from_phone = from_phone;
        this.message = message;
        this.time = time;
    }

    public UsersMessageStorage() {

    }

    public String getTo_phone() {
        return to_phone;
    }

    public void setTo_phone(String to_phone) {
        this.to_phone = to_phone;
    }

    public String getFrom_phone() {
        return from_phone;
    }

    public void setFrom_phone(String from_phone) {
        this.from_phone = from_phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String to_phone,from_phone,message,time;

}
