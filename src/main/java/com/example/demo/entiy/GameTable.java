package com.example.demo.entiy;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GameTable {
    private String table="000000000";
    private User user;
    public void set(int position,char value){
        if(position>8){
            System.out.println("Error 9<"+position);
            return;
        }
        char[] charArray = table.toCharArray();
        charArray[position] = value;
        table = new String(charArray);
    }
    public boolean isWin(char type){
        boolean isWin=true;
        for (int i=0;i<3;++i){
            isWin=true;
            for(int j=0;j<3;++j){
                if(table.charAt(i*3+j)!=type){
                    isWin=false;
                    break;
                }
            }
            if(isWin){
                return true;
            }
        }
        for (int i=0;i<3;++i){
            isWin=true;
            for(int j=0;j<3;++j){
                if(table.charAt(j*3+i)!=type){
                    isWin=false;
                    break;
                }
            }
            if(isWin){
                return true;
            }
        }
        isWin=true;
        for(int i=0;i<3;i++){
            if(table.charAt(i*3+i)!=type){
                isWin=false;
                break;
            }
        }
        if(isWin){
            return true;
        }
        isWin=true;
        for(int i=0;i<3;i++){
            if(table.charAt(i*3+2-i)!=type){
                isWin=false;
                break;
            }
        }
        if(isWin){
            return true;
        }
        return false;
    }
    public boolean pat(){
        return !table.contains("0");
    }
    public ArrayList<Integer> getFree(){
        ArrayList<Integer> result=new ArrayList<>();
        for (int i=0;i<table.length();++i){
            if(table.charAt(i)=='0'){
                result.add(i);
            }
        }
        return result;
    }
    public void restart(){
        table="000000000";
    }
}
