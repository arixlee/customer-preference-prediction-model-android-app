package com.example.waiki.testui;

import java.io.Serializable;

/**
 * Created by waiki on 10/19/2017.
 */

public class Comment implements Serializable {
    String comment,date,userid;

    public Comment(String comment, String date, String userid) {
        this.comment = comment;
        this.date = date;
        this.userid = userid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
