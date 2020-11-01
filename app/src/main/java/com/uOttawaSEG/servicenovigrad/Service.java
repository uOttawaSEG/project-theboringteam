package com.uOttawaSEG.servicenovigrad;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Service{
    private String name;
    private ArrayList reqInfo;

    public Service(String name){
        this.name = name;
        reqInfo = new ArrayList<String>();
    }

    public Service(String name, ArrayList reqInfo){
        this.name = name;
        this.reqInfo = reqInfo;
    }

    public String getName(){
        return name;
    }

    public void addInfo(String requiremnt){
        reqInfo.add(requiremnt);
    }

    public String getRequiremnt(int index){
        return (String) reqInfo.get(index);
    }

    public void removeReqiuirement(int index){
        reqInfo.remove(index);
    }

    public ArrayList<String> getReqInfo(){return reqInfo;}


}
