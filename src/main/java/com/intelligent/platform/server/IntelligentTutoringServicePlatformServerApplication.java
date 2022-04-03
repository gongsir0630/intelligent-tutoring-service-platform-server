package com.intelligent.platform.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gongtao
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.intelligent.platform.server.dao"})
public class IntelligentTutoringServicePlatformServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelligentTutoringServicePlatformServerApplication.class, args);
    }

}
