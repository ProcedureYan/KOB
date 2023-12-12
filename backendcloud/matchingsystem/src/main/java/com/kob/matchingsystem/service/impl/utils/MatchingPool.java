package com.kob.matchingsystem.service.impl.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MatchingPool extends Thread{
    private static List<Player> players = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();
    private static RestTemplate restTemplate;
    private final static String startGameUrl = "http://127.0.0.1:3000/pk/start/game/";


    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        MatchingPool.restTemplate = restTemplate;
    }

    public void addPlayer(Integer userId,Integer rating){
        lock.lock();
        try{
            players.add(new Player(userId,rating,0));
        }finally {
            lock.unlock();
        }
    }

    public void removePlayer(Integer userId){
        lock.lock();
        try{
            List<Player> newPlayers = new ArrayList<>();
            for(Player player: players){
                if(!player.getUserId().equals(userId)){
                    newPlayers.add(player);
                }
            }
            players = newPlayers;
        }finally {
            lock.unlock();
        }
    }

//    将所有当前玩家等待时间加1
    private void increaseWaitingTime(){
        for(Player player:players){
            player.setWaitingTime(player.getWaitingTime()+1);
        }
    }

//    判断两名玩家能否匹配上
//    匹配的规则是两个人的天梯分差小于他们等待时间的10倍
    private boolean checkMatched(Player a,Player b){
        int ratingDelta = Math.abs(a.getRating() - b.getRating());
        int waitingTime = Math.max(a.getWaitingTime(),b.getWaitingTime());
        if(ratingDelta < waitingTime * 10) return true;
        else return false;
    }

//    返回两名玩家的匹配结果,传给backend
    private void sendResult(Player a,Player b){
        System.out.println("send result:" + a + " " + b);
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("a_id",a.getUserId().toString());
        data.add("b_id",b.getUserId().toString());
        restTemplate.postForObject(startGameUrl,data,String.class);
    }

//    尝试匹配所有玩家
    private void matchPlayers(){
        System.out.println("match players:" + players.toString());
        boolean[] used = new boolean[players.size()];
        for(int i = 0;i<players.size();i++){
            if(used[i]) continue;
            for(int j = i + 1; j<players.size();j++){
                if(used[j]) continue;
                Player a = players.get(i), b = players.get(j);
                if(checkMatched(a,b)){
                    used[i] = true;
                    used[j] = true;
                    sendResult(a,b);
                    break;
                }
            }
        }
        List<Player> newPlayers = new ArrayList<>();
        for(int i = 0;i < players.size();i ++){
            if(!used[i]){
                newPlayers.add(players.get(i));
            }
        }
        players = newPlayers;
    }

    @Override
    public void run(){
        while(true){
            try {
                Thread.sleep(1000);
                lock.lock();
                try {
                    increaseWaitingTime();
                    matchPlayers();
                }finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
