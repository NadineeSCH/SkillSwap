package com.example.skillswap.models;

import java.util.List;

public class Match implements Comparable<Match>{
    public User currentUser;
    public User matchedUser;
    public int learnCount;
    public int teachCount;

    public List<String> teachArr;
    public List<String> learnArr;

    public Match(User currentUser,User matchedUser,int learnCount,List<String> learnArr, int teachCount, List<String> teachArr){
        this.currentUser=currentUser;
        this.matchedUser=matchedUser;
        this.learnArr=learnArr;
        this.teachArr=teachArr;
        this.teachCount = teachCount;
        this.learnCount = learnCount;
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
}
