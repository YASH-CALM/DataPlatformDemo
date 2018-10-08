package com.yash.calm.model;

public class Analysis extends Output{

    // Speed setting of the processing plant
    private int speed;
    // How fine the material is crushed
    private int fineness;

    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getFineness() {
        return fineness;
    }
    public void setFineness(int fineness) {
        this.fineness = fineness;
    }



}
