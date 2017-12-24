package com.example.jasmine.goalideatracker;

/**
 * Created by jasmine on 19/12/17.
 */

public class Goal {

    private String name;
    private String time;
    private String key;
    private String reason;
    private String type;
    private String priority;

    public Goal(){

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Goal(String name, String time, String reason, String type,String priority){
        this.name = name;
        this.time = time;
        //this.key = key;
        this.reason = reason;
        this.type = type;
        this.priority = priority;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setReason(String name) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
