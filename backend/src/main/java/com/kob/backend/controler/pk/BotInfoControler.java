package com.kob.backend.controler.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pk/")
public class BotInfoControler {
    @RequestMapping("getbotinfo/")
    public String getBotInfo(){
        return "hhhh";
    }
}
