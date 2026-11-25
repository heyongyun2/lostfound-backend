package org.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jmx.export.annotation.ManagedNotification;
import org.mybatis.spring.annotation.MapperScan;
@SpringBootApplication
@MapperScan("org.example.springboot.mapper")

public class SpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}