package com.example.skillswap.models;

import java.util.List;

public class Match implements Comparable<Match>{
    public User currentUser;
    public User matchedUser;
    public int learnCount;
    public int teachCount;

    //Initial when first created, Pending when user requested,Approved when request is approved
    public String status;

    public List<String> teachArr;
    public List<String> learnArr;

    public Match(User currentUser,User matchedUser,int learnCount,List<String> learnArr, int teachCount, List<String> teachArr){
        this.currentUser=currentUser;
        this.matchedUser=matchedUser;
        this.learnArr=learnArr;
        this.teachArr=teachArr;
        this.teachCount = teachCount;
        this.learnCount = learnCount;
        this.status = "Initial";
    }

    @Override
    public int compareTo(Match o) {


        if ( learnCount+teachCount < o.learnCount + o.teachCount){
            return 1;
        }
        else if (learnCount+teachCount > o.learnCount + o.teachCount){
            return -1;
        }
        else {
            return 0;
        }

    }

    @Override
    public String toString(){
        int totalCount = learnCount + teachCount;
        return totalCount + " matches with " + matchedUser.getId();
    }

    public void setPending(){
        status = "Pending";
    }

    public boolean isPending(){
        return status.equals("Pending");

    }

    //for further implementation

    public void setApproved(){
        status = "Approved";
    }

    public boolean isApproved(){
        return status.equals("Approved");

    }
}

