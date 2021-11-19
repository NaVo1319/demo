package com.example.demo.entiy;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class AI {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ElementCollection
    private List<Double> list= new ArrayList<>();
    private String nameAi;
    private double oldState=0;
    private Integer lastStep=0;
    public AI() {
        for (int i=0;i< 9;++i){
            list.add(-1.0);
        }
    }
    public Double getReward(int state,GameTable game){
        if(game.isWin('2')){
            return 1.0;
        }
        if(game.isWin('1')){
            return 0.0;
        }
        if(list.get(state)!=-1.0){
            return list.get(state);
        }
        return 0.5;
    }
    public void correctReward(int state, double newReward,GameTable game){
        double oldReward=getReward(state,game);
        list.set(state,oldReward+0.1*(newReward-oldReward));
    }
    public GameTable makeStep(GameTable gameTable){
        ArrayList<Integer> free= gameTable.getFree();
        int step=0;
        if((int) (Math.random() * 101)<10){
            step= free.get((int)(Math.random() * (free.size())));
            gameTable.set(step,'2');
            oldState=getReward(step,gameTable);
        }
        else{
            ArrayList<Double> rewards=new ArrayList<>();
            GameTable cloneTable=new GameTable();
            cloneTable.setTable(gameTable.getTable());
            double maxReward=0;
            ArrayList<Integer> indexSteps=new ArrayList<>();
            for(int i: free){
                cloneTable.set(i, '2');
                rewards.add(getReward(i,cloneTable));
                indexSteps.add(i);
                if(getReward(i,cloneTable)>maxReward){
                    maxReward=getReward(i,cloneTable);
                }
            }
            ArrayList<Integer> Steps=new ArrayList<>();
            for(int i=0;i<rewards.size();++i){
                if(rewards.get(i)==maxReward){
                    Steps.add(indexSteps.get(i));
                }
            }
            step=Steps.get((int) (Math.random() * Steps.size()));
            gameTable.set(step,'2');
            correctReward(step,maxReward,gameTable);
        }
        lastStep=step;
        System.out.println(list);
        return gameTable;
    }
    public GameTable makeStep12(GameTable gameTable){
        ArrayList<Integer> free= gameTable.getFree();
        int step=0;
        if((int) (Math.random() * 101)<30){
            step= free.get((int)(Math.random() * (free.size()+1)));
            gameTable.set(step,'2');
        }
        else{
            double max=0;
            for (int i=0;i< free.size();++i){
                if(list.get(free.get(i))>max){
                    max= list.get(i);
                    step=free.get(i);
                }
            }
            gameTable.set(step,'2');
        }
        list.set(step,getReward(step,gameTable));
        lastStep=step;
        return gameTable;
    }
}
