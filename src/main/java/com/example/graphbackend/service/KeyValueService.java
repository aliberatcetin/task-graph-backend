package com.example.graphbackend.service;

import com.example.graphbackend.client.MpiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeyValueService {

    private final HashMap<String,String> inMemoryDb;

    public String put(String key, String value){
        inMemoryDb.put(key,value);
        return key;
    }
    public String get(String key){
        return inMemoryDb.get(key);
    }

}
