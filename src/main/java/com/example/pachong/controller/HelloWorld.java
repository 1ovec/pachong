package com.example.pachong.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 * @ClassName HelloWorld
 * @Description
 * @Date 2019/10/9
 */
@RestController
@RequestMapping("/hello")
public class HelloWorld {

  @GetMapping
  public Map<String,Object> test(){
    Map<String, Object> map = new HashMap<>();
    map.put("name","lovec");
    map.put("sex",'n');
    return map;
  }
}
