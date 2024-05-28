package com.example.graphbackend.controller;

import com.example.graphbackend.service.KeyValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


@RestController
@RequiredArgsConstructor
@RequestMapping("/key")
@CrossOrigin(maxAge = 3600)
public class KeyValueController {

    private final KeyValueService keyValueService;

    @PostMapping("/put/{key}")
    String put(@PathVariable String key, @RequestBody String value) {
        try {
            return keyValueService.put(key, URLDecoder.decode(value.substring(0, value.length() - 1), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @GetMapping("/get/{key}")
    String get(@PathVariable String key) {
        return keyValueService.get(key);
    }

}
