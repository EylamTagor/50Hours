package com.fiftyhours.data;

import java.util.ArrayList;

public class User {
    private ArrayList<Integer> day, night;
    private String name;

    public User(String name, ArrayList<Integer> day, ArrayList<Integer> night) {
        this.day = day;
        this.night = night;
        this.name = name;
    }

    public ArrayList<Integer> getDay() {
        return day;
    }

    public void setDay(ArrayList<Integer> day) {
        this.day = day;
    }

    public ArrayList<Integer> getNight() {
        return night;
    }

    public void setNight(ArrayList<Integer> night) {
        this.night = night;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
