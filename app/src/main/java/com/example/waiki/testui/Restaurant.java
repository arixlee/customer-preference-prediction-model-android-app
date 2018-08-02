package com.example.waiki.testui;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Fung Desk on 05-Oct-17.
 */

public class Restaurant implements Serializable {
    private String resID;
    private String resName;
    private String area;
    private String address;
    private double minPrice;
    private int noGoodReview;
    private int noBadReview;
    private Bitmap resImage;
    private int imageSize;

    private String userID;

    public Restaurant(String resName, int noGoodReview,int imageSize, Bitmap resImage) {
        this.resName = resName;
        this.noGoodReview = noGoodReview;
        this.imageSize=imageSize;
        this.resImage=resImage;
    }

    public Restaurant(String resName, String area, String address, double minPrice, int noGoodReview, int noBadReview) {

        this.resName = resName;
        this.area = area;
        this.address = address;
        this.minPrice = minPrice;
        this.noGoodReview = noGoodReview;
        this.noBadReview = noBadReview;

    }

    public Restaurant(String resID, String resName, String area, String address, double minPrice, int noGoodReview, int noBadReview, String userID) {
        this.resID=resID;
        this.resName = resName;
        this.area = area;
        this.address = address;
        this.minPrice = minPrice;
        this.noGoodReview = noGoodReview;
        this.noBadReview = noBadReview;
        this.userID=userID;
    }

    public Bitmap getResImage() {
        return resImage;
    }

    public int getImageSize() {
        return imageSize;
    }

    public void setResImage(Bitmap resImage) {
        this.resImage = resImage;
    }

    public void setImageSize(int imageSize) {
        this.imageSize = imageSize;
    }

    public Restaurant(String userID){
        this.userID=userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getResID() {
        return resID;
    }

    public String getResName() {
        return resName;
    }

    public String getArea() {
        return area;
    }

    public String getAddress() {
        return address;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public int getNoGoodReview() {
        return noGoodReview;
    }

    public int getNoBadReview() {
        return noBadReview;
    }

    public void setResID(String resID) {
        this.resID = resID;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public void setNoGoodReview(int noGoodReview) {
        this.noGoodReview = noGoodReview;
    }

    public void setNoBadReview(int noBadReview) {
        this.noBadReview = noBadReview;
    }
}
