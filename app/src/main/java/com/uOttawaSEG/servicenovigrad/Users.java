package com.uOttawaSEG.servicenovigrad;

class User {
    private String _id;
    private String _name;
    private String _email;
    private String _type;

    public User(String id, String name, String email, String type) {
        _id = id;
        _name = name;
        _email = email;
        _type = type;
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

}
