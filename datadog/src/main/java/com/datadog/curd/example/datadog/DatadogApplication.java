package com.datadog.curd.example.datadog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling; // 이 줄 추가

@SpringBootApplication
@EnableScheduling // 이 줄 추가
public class DatadogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatadogApplication.class, args);
    }

}
