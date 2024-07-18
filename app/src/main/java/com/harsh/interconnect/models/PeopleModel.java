package com.harsh.interconnect.models;

public class PeopleModel {
    String userID="";
    String name="";
    String gender="";
    String state="";
    String city="";
    String interestsWithAt="";
    Boolean hasProfile=false;
    public PeopleModel(String userID, String name, String gender, String state, String city, String interestsWithAt, Boolean hasProfile){
        this.userID = userID;
        this.name = name;
        this.gender = gender;
        this.state = state;
        this.city = city;
        this.interestsWithAt = interestsWithAt;
        this.hasProfile = hasProfile;
    }
    public String getUserID(){
        return userID;
    }
    public String getName(){
        return name;
    }
    public String getGender(){
        return gender;
    }
    public String getState(){
        return state;
    }
    public String getCity(){
        return city;
    }
    public String getInterestsWithAt(){
        return interestsWithAt;
    }
    public Boolean hasProfile(){
        return hasProfile;
    }
}
