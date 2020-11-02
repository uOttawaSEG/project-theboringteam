package com.uOttawaSEG.servicenovigrad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class Service{
    private String name;
    private HashMap reqInfo;
    private Integer nextReq;

    public Service(String name){
        this.name = name;
        reqInfo = new HashMap<String,String>();
        nextReq = 0;
    }

    public Service(String name, HashMap reqInfo,Integer nextReq){
        this.name = name;
        this.reqInfo = reqInfo;
        this.nextReq = nextReq;
    }

    public String getName(){
        return name;
    }

    public void addInfo(String requiremnt){
        reqInfo.put(nextReq.toString(),requiremnt);
        Integer nextReq0 = new Integer(nextReq.intValue()+1);
        nextReq = nextReq0;
    }

    public String getRequiremnt(String key){
        return (String) reqInfo.get(key);
    }

    public void removeReqiuirement(int index){
        reqInfo.remove(index);
    }

    public Integer getNextReq(){return nextReq;}

    public HashMap<Integer,String> getReqInfo(){return reqInfo;}


}
