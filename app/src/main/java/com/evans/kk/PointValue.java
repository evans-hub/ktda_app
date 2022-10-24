package com.evans.kk;

public class PointValue {
    int xValue;
    int yValue;

    public PointValue() {
    }

    public PointValue(int xValue, int yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public long getxValue() {
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
}
