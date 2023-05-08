package org.quentin.springbootredisapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class ControllerOfMyRedis {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerOfMyRedis.class);

    private final RedisTemplate<String, String> redisTemplate;

    private final StringRedisTemplate stringTemplate;

    public ControllerOfMyRedis(RedisTemplate<String, String> redisTemplate, StringRedisTemplate stringTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringTemplate = stringTemplate;
    }

    @GetMapping("/buffer")
    public String buffer(@RequestParam("value") String value){
        Assert.notNull(value, "value cannot be null");
        String key = "demo-queue";
        Long result = redisTemplate.opsForList().leftPush(key, value);
        LOGGER.info("result is {}", result);
        return String.join(" ","The value of the key",key,"is", value.toUpperCase());
    }

    @GetMapping("/strings")
    public String strings(@RequestParam("value") String value){
        Long result = stringTemplate.opsForList().leftPush("string-queue", value);
        LOGGER.info("result is {}", result);
        return String.join(" ", "The value is",value.toUpperCase());
    }
}
