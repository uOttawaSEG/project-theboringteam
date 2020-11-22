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
    private String id;

    public Service(String name, String id){
        this.name = name;
        this.id = id;
        reqInfo = new HashMap<String,String>();
        nextReq = 0;
    }

    public Service(String name, HashMap reqInfo,Integer nextReq, String id){
        this.name = name;
        this.reqInfo = reqInfo;
        this.nextReq = nextReq;
        this.id = id;
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

    public String getId(){return id;}

    public void removeReqiuirement(int index){
        reqInfo.remove(index);
    }

    public Integer getNextReq(){return nextReq;}

    public HashMap<Integer,String> getReqInfo(){return reqInfo;}


}
