package com.example.graphbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Config {

    @Bean
    public HashMap<String,String> inMemoryDb(){
        return new HashMap<>();
    }
}
