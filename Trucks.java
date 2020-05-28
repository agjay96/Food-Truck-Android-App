package com.example.foodtruck;

public class Trucks {
    private String app, add, item, time;

    public Trucks(String app, String add, String item, String time){
        this.setAdd(add);
        this.setApp(app);
        this.setItem(item);
        this.setTime(time);
    }

    public String getApp() {
        return this.app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
