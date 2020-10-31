package com.uOttawaSEG.servicenovigrad;

import java.util.Dictionary;
import java.util.Hashtable;

public class Service{
    private String name;
    private Dictionary reqInfo;

    public Service(String name){
        this.name = name;
        reqInfo = new Hashtable();
    }

    public String getName(){
        return name;
    }

    public void addInfo(String key, String value){
        reqInfo.put(key, value);
    }


}
