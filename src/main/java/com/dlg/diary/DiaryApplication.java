package com.dlg.diary;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class DiaryApplication {

    @Autowired
    private Environment env;

    @PostConstruct
    void init(){
        System.out.println(env.getProperty("spring.datasource.driver-class-name"));
    }

    public static void main(String[] args) {
        SpringApplication.run(DiaryApplication.class, args);
    }

}
