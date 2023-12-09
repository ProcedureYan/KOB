package com.kob.backend.consumer.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Integer id;
    private Integer sx;
    private Integer sy;
    private List<Integer> steps;

//    检验当前回合蛇会不会变长
    private boolean checkTailIncreasing(int step){
        if (step <= 10) return true;
        return step%3 == 1;
    }

    public List<Cell> getCells(){
        List<Cell> res = new ArrayList<>();
        int[] dx = {-1, 0, 1, 0},dy = {0, 1, 0, -1};
        int x = sx,y = sy;
        int step = 0;
        res.add(new Cell(x,y));
        for(int d:steps){
            x += dx[d];
            y += dy[d];
            res.add(new Cell(x,y));
            step ++;
            if(!checkTailIncreasing(step)){
                res.remove(0);
            }
        }
        return res;
    }

    public String getStepsString() {
        StringBuilder res = new StringBuilder();
        for (int d: steps) {
            res.append(d);
        }
        return res.toString();
    }

}
