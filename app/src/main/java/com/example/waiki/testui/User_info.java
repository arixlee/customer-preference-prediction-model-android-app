package com.example.waiki.testui;

import java.io.Serializable;

/**
 * Created by pang on 10/12/2017.
 */

public class User_info implements Serializable{
    String id;
    char gender;
    String birthday;
    String addr;
    String race;
    boolean vegen;

    public User_info(){}

    public User_info(String id){
        this.id=id;
    }

    public User_info(String id, char gender, String birthday, String addr, String race, boolean vegen){
        this.id=id;
        this.gender=gender;
        this.birthday=birthday;
        this.addr=addr;
        this.race=race;
        this.vegen=vegen;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public boolean isVegen() {
        return vegen;
    }

    public void setVegen(boolean vegen) {
        this.vegen = vegen;
    }



    public String getUser_id() {
        return id;
    }

    public void setUser_id(String id) {
        this.id = id;
    }
}
