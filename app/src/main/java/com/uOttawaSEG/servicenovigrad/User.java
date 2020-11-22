package com.uOttawaSEG.servicenovigrad;

import java.util.HashMap;
import java.util.Map;

class User {
    private String _id;
    private String _name;
    private String _email;
    private String _type;
    private HashMap<String, String> _information;

    public User(){

    }

    public User(String id, String name, String email, String type){
        _id = id;
        _name = name;
        _email = email;
        _type = type;
        _information = new HashMap<>();
    }


    public void setId(String id) {
        _id = id;
    }
    public String getId() {
        return _id;
    }
    public void setName(String name) {
        _name = name;
    }
    public String getName() {
        return _name;
    }
    public void setEmail(String email) {
        _email = email;
    }
    public String getEmail() {
        return _email;
    }
    public void setType(String type) {
        _type = type;
    }
    public String getType() {
        return _type;
    }
    public void addInfo(String name, String value){
        _information.put(name,value);
    }
    public String getInfo(String name){
        return _information.get(name);
    }
    public void deleteInfo(String name){
        _information.remove(name);
    }
}
