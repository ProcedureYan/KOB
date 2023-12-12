package com.kob2.backend.service.impl.pk;

import com.kob2.backend.consumer.WebSocketServer;
import com.kob2.backend.service.pk.StartGameService;
import org.springframework.stereotype.Service;

@Service
public class StartGameServiceImpl implements StartGameService {
    @Override
    public String startGame(Integer aId, Integer bId) {
        System.out.println("start game:"+ aId + " " + bId);
        WebSocketServer.startGame(aId,bId);
        return "game start success";
    }
}
