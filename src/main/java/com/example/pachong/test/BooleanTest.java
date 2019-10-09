package com.example.pachong.test;

/**
 * @author admin
 * @ClassName BooleanTest
 * @Description
 * @Date 2019/10/9
 */
public class BooleanTest {

  public static String generteNum() {
    Boolean maxNum = true;
    if (maxNum == true) {
      System.out.println("fdfsfsfsdfsfsfsfsfsdfsdfsfsdfsfsfsdffsffds");
      maxNum = false;
      System.out.println(maxNum);
    } else {
      maxNum = true;
    }
    System.out.println(maxNum);
    return maxNum.toString();
  }

  public static void main(String[] args) {
    generteNum();
  }
}
