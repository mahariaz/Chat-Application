package com.mahariaz.i181652_180681;

public class UsersMessageStorage {
    public UsersMessageStorage(String to_phone, String from_phone, String message, String time,String sortingParam) {
        this.to_phone = to_phone;
        this.imageFlag = from_phone;
        this.message = message;
        this.time = time;
        this.sortingParam = sortingParam;
    }

    public UsersMessageStorage() {

    }

    public String getTo_phone() {
        return to_phone;
    }

    public void setTo_phone(String to_phone) {
        this.to_phone = to_phone;
    }

    public String getImageFlag() {
        return imageFlag;
    }

    public void setImageFlag(String imageFlag) {
        this.imageFlag = imageFlag;
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

    String to_phone, imageFlag,message,time,sortingParam;

    public String getSortingParam() {
        return sortingParam;
    }

    public void setSortingParam(String sortingParam) {
        this.sortingParam = sortingParam;
    }
}
