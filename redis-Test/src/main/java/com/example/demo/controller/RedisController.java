package com.example.demo.controller;

import com.example.demo.model.RedisInfo;
import com.example.demo.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/test")
    public String test() {
        return "test";
    }


    @RequestMapping(value= "/register", method= RequestMethod.POST, produces = "application/json; charset=utf8")
    public Object register(@RequestBody RedisInfo redisInfo) {
        log.info("regist 시작");
        log.info("body 내용" + redisInfo.getKey() + redisInfo.getValue());
        redisService.setStringOps(redisInfo.getKey(), redisInfo.getValue(), 100000, TimeUnit.SECONDS);
        log.info("regist 완료");
        return redisInfo;
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    public Object get(@RequestBody String key) {
        return redisService.getStringOps(key);
    }


}
