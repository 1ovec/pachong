package com.example.pachong.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 * @ClassName HelloWorld
 * @Description 项目测试
 * @Date 2019/10/9
 */
@RestController
@RequestMapping("/hello")
public class HelloWorld {

  private static String[] namesChar=new String[]{"aa","bb","cc","dd","ee","ff","gg","hh","ii","jj"};
  private static String[] sex=new String[]{"nan","nv"};


  /**
   * 根据id来动态返回一个json串
   *
   * @param id id
   * @return map
   */
  @GetMapping("/{id}")
  public Map<String, Object> test(@PathVariable int id) {
    Map<String, Object> map=new HashMap<>();
    map.put("name",namesChar[id % 10]);
    map.put("sex",sex[id % 2]);
    map.put("age",id);
    return map;
  }
}
