package com.foondamate.foondamateapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FoondamateApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoondamateApplication.class, args);
    }

}
