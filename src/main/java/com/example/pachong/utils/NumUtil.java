package com.example.pachong.utils;

import java.math.BigDecimal;

/**
 * @author admin
 * @ClassName NumUtil
 * @Description a
 * @Date 2019/8/30
 */
public class NumUtil {

  /**
   * 两个int类型的值，相除，取两位小数的带百分号的值
   *
   * @param a a
   * @param b b
   * @return double c
   */
  public static Double getDoubleNum(int a,int b) {
    if(b == 0) {
      return 0.0;
    }
    return new BigDecimal((float) a * 100 / b).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
  }
}
