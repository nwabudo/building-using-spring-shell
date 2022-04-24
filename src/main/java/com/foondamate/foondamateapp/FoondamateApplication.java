package com.foondamate.foondamateapp;

import com.foondamate.foondamateapp.helpers.ShellHelper;
import org.jline.terminal.Terminal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

@SpringBootApplication
public class FoondamateApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoondamateApplication.class, args);
    }

    @Bean
    public ShellHelper shellHelper(@Lazy Terminal terminal) {
        return new ShellHelper(terminal);
    }

//    @Bean(name="restTemplate")
//    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
//
//        RestTemplate template = restTemplateBuilder.requestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
//                .messageConverters(new MappingJackson2HttpMessageConverter())
//                .build();
//        return template;
//
//
//    }
}
