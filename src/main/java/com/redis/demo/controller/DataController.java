package com.redis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.redis.demo.model.DataModel;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    RedisTemplate redisTemplate;


    @PostMapping
    public void saveData(@RequestBody DataModel dataModel) {
        redisTemplate.opsForValue().set(dataModel.getKey(), dataModel.getValue());
    }

    @GetMapping("/{key}")
    public DataModel fetchData(@PathVariable String key) {
        String value = (String) redisTemplate.opsForValue().get(key);
        if (value == null) {
            throw new RuntimeException("Key not found");
        }
        DataModel dataModel = new DataModel();
        dataModel.setKey(key);
        dataModel.setValue(value);
        return dataModel;
    }
}
