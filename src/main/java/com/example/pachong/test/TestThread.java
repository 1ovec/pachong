package com.example.pachong.test;

/**
 * @author admin
 * @ClassName TestThread
 * @Description 一个简单且无聊的线程demo
 * @Date 2019/10/10
 */
public class TestThread extends Thread {

  private static int i=0;
  private String name;


  public TestThread(String name1) {
    this.name=name1;
  }

  @Override
  public void run() {
    i++;
    System.out.println(name+":"+i);
  }
}
