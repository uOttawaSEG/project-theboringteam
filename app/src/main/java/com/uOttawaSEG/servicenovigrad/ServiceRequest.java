package com.uOttawaSEG.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ServiceRequest{

    private String requestID;
    private String userID;
    private String branchID;
    private String serviceID;
    private String status;
    private HashMap<String,String> info;

    public ServiceRequest(){
        this.userID = "";
        this.branchID = "";
        this.serviceID = "";
        this.status = "pending";
        this.info = new HashMap<String,String>();

    }

    public ServiceRequest(String userID, String branchID, String serviceID){
        this.userID = userID;
        this.branchID = branchID;
        this.serviceID = serviceID;
        this.status = "pending";
        this.info = new HashMap<String,String>();

    }

    public ServiceRequest(String requestID, String userID, String branchID, String serviceID){
        this.userID = userID;
        this.branchID = branchID;
        this.serviceID = serviceID;
        this.requestID = requestID;
        this.status = "pending";
        this.info = new HashMap<String,String>();

    }

    public ServiceRequest(String requestID, String userID, String branchID, String serviceID, HashMap<String, String> info){
        this.userID = userID;
        this.branchID = branchID;
        this.serviceID = serviceID;
        this.requestID = requestID;
        this.status = "pending";
        this.info = info;

    }

    public String getRequestID(){
        return requestID;
    }

    public String getUserID() {
        return userID;
    }

    public String getBranchID() {
        return branchID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public String getStatus() {
        return status;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public void setStatus(String status) {
        if(status.equals("pending")|| status.equals("approved")||status.equals("denied")){
            this.status = status;
        }
    }

    public void updateInfo(String infoType, String information){
        this.info.put(infoType,information);
    }

    public void removeInfo(String infoType){
        this.info.remove(infoType);
    }
}