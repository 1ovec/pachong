package com.example.pachong.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author admin
 * @ClassName BooleanTest
 * @Description
 * @Date 2019/10/9
 */
public class BooleanTest {

  public static String generteNum() {
    List<Integer>list =new ArrayList<>(Arrays.asList(1,0,3,0,8,9,0,12,0,45,0,14));

    List<Integer>list2 =new ArrayList<>();
    list2.add(1); list2.add(0); list2.add(1); list2.add(0); list2.add(1); list2.add(9); list2.add(0);
    for(int i=0; i < list.size(); ++ i) {
      if(list.get(i)==0){
        list.remove(i);
      }
    }
    System.out.println(list.toString());

    for(int i=0; i < list2.size(); ++ i) {
      if(list2.get(i)==0){
        list2.remove(i);
      }
    }
    System.out.println(list2.toString());
    return "000";
  }

  public static void main(String[] args) {
    generteNum();

    //下面代码运行正常
    new TestThread("t1").start();
    new TestThread("t2").start();

    System.out.println("-------------------------------------");
    //下面代码运行报错
    TestThread t1 =new TestThread("tt1");
    t1.start();
    TestThread t2 =new TestThread("tt2");
    t1.start();

  }
}
