package com.example.waiki.testui;

import java.io.Serializable;

/**
 * Created by pang on 10/11/2017.
 */

public class History implements Serializable {

    String userID;
    String resID;
    String resName;
    double viewTimer;
    double likelihoodValue;

    public History(String resName, double viewTimer, double likelihoodValue) {

        this.resName = resName;
        this.viewTimer = viewTimer;
        this.likelihoodValue = likelihoodValue;

    }

    public History(String resName, String resID, double viewTimer, double likelihoodValue) {
        this.resName = resName;
        this.resID = resID;
        this.viewTimer = viewTimer;
        this.likelihoodValue = likelihoodValue;

    }

    public String getResID() {
        return resID;
    }

    public void setResID(String resID) {
        this.resID = resID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


    public double getViewTimer() {
        return viewTimer;
    }

    public void setViewTimer(double viewTimer) {
        this.viewTimer = viewTimer;
    }


    public double getLikelihoodValue() {
        return likelihoodValue;
    }

    public void setLikelihoodValue(double likelihoodValue) {
        this.likelihoodValue = likelihoodValue;
    }


    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }


}
