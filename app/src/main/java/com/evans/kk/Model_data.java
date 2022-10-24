package com.evans.kk;

public class Model_data {
    private int xValue,yValue;
    private String date,imageUrl;

    public Model_data(int xValue, int yValue, String date, String imageUrl) {
        this.xValue = xValue;
        this.yValue = yValue;
        this.date = date;
        this.imageUrl = imageUrl;
    }



    public Model_data() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getxValue() {
        return xValue;
    }

    public void setxValue(int xValue) {
        this.xValue = xValue;
    }

    public int getyValue() {
        return yValue;
    }

    public void setyValue(int yValue) {
        this.yValue = yValue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
