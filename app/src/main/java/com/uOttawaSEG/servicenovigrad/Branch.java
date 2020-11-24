package com.uOttawaSEG.servicenovigrad;

import java.util.HashMap;

public class Branch {
    private String name;
    private String id;
    private String address;
    private HashMap workingHours;
    private HashMap<String,String> services;

    public Branch(){
        services = new HashMap<>();
        workingHours = new HashMap();

        this.workingHours.put("Sunday","closed");
        this.workingHours.put("Monday","closed");
        this.workingHours.put("Tuesday","closed");
        this.workingHours.put("Wednesday","closed");
        this.workingHours.put("Thursday","closed");
        this.workingHours.put("Friday","closed");
        this.workingHours.put("Saturday","closed");
    }

    public Branch(String name, String id, String address){
        this.name = name;
        this.id = id;
        this.address = address;
        services = new HashMap<>();
        workingHours = new HashMap();

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

    public void setWorkingHoursSunday(String times) {
        workingHours.put("Sunday",times);
    }

    public void setWorkingHoursMonday(String times) {
        workingHours.put("Monday",times);
    }

    public void setWorkingHoursTuesday(String times) {
        workingHours.put("Tuesday",times);
    }

    public void setWorkingHoursWednesday(String times) {
        workingHours.put("Wednesday",times);
    }

    public void setWorkingHoursThursday(String times) {
        workingHours.put("Thursday",times);
    }

    public void setWorkingHoursFriday(String times) {
        workingHours.put("Friday",times);
    }
    public void setWorkingHoursSaturday(String times) {
        workingHours.put("Saturday",times);
    }

    public void addService(String id, String name) {
        services.put(id,name);
    }

    public HashMap getWorkingHours() {
        return workingHours;
    }

    public HashMap getServices() {
        return services;
    }



}
