package com.example.demo.entiy;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class GameTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 9)
    private String map="000000000";
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    public void set(int position,char value){
        if(position>8){
            System.out.println("Error 9<"+position);
            return;
        }
        char[] charArray = map.toCharArray();
        charArray[position] = value;
        map = new String(charArray);
    }
    public boolean isWin(char type){
        boolean isWin=true;
        for (int i=0;i<3;++i){
            isWin=true;
            for(int j=0;j<3;++j){
                if(map.charAt(i*3+j)!=type){
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
                if(map.charAt(j*3+i)!=type){
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
            if(map.charAt(i*3+i)!=type){
                isWin=false;
                break;
            }
        }
        if(isWin){
            return true;
        }
        isWin=true;
        for(int i=0;i<3;i++){
            if(map.charAt(i*3+2-i)!=type){
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
        return !map.contains("0");
    }
    public ArrayList<Integer> getFree(){
        ArrayList<Integer> result=new ArrayList<>();
        for (int i=0;i<map.length();++i){
            if(map.charAt(i)=='0'){
                result.add(i);
            }
        }
        return result;
    }
    public void restart(){
        map="000000000";
    }
}
