package com.hexa.muinus.temp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisContoller {

    private final RedisTestDataLoader testDataLoader;

    @PostMapping("/start")
    public void startRedis(){
      log.info("startRedis");
      testDataLoader.loadTestData();
    }
}
