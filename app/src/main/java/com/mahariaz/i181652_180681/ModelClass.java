package com.mahariaz.i181652_180681;


public class ModelClass {


    public ModelClass(int dp, String person_name, String time, String last_chat) {
        this.dp = dp;
        this.person_name = person_name;
        this.time = time;
        this.last_chat = last_chat;
    }

    public int getDp() {
        return dp;
    }

    public void setDp(int dp) {
        this.dp = dp;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLast_chat() {
        return last_chat;
    }

    public void setLast_chat(String last_chat) {
        this.last_chat = last_chat;
    }

    private int dp;
    private String person_name;
    private String time;
    private String last_chat;


}
class contact_model{
    public contact_model(int dp,String person_name, String phone,String lastSeen) {
        this.person_name = person_name;
        this.phone = phone;
        this.dp=dp;
        this.lastSeen = lastSeen;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    String person_name,phone;

    public int getDp() {
        return dp;
    }

    public void setDp(int dp) {
        this.dp = dp;
    }

    private int dp;
    private String lastSeen;
}

