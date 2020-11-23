package com.uOttawaSEG.servicenovigrad;

import java.util.HashMap;

public class Branch {
    private String name;
    private String id;
    private String address;
    private int nextService;
    private HashMap workingHours;
    private HashMap<String,String> services;

    public Branch(){}

    public Branch(String name, String id, String address){
        this.name = name;
        this.id = id;
        this.address = address;
        this.workingHours = new HashMap<String,String>();

        this.workingHours.put("Sunday","closed");
        this.workingHours.put("Monday","closed");
        this.workingHours.put("Tuesday","closed");
        this.workingHours.put("Wednesday","closed");
        this.workingHours.put("Thursday","closed");
        this.workingHours.put("Friday","closed");
        this.workingHours.put("Saturday","closed");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}
}
