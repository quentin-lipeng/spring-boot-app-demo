package org.quentin.springbootredisapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * todo Try to add function of spring session use redis
 * todo To document a rest api
 * todo if no return in query method then throw an exception in service
 */
@SpringBootApplication
public class SpringBootRedisAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRedisAppApplication.class, args);
    }

}
