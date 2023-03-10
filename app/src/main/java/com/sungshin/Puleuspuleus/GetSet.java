package com.sungshin.Puleuspuleus;

public class GetSet {

    String name;
    String date;

    public GetSet(String name, String date){
        this.name = name;
        this.date = date;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

}
