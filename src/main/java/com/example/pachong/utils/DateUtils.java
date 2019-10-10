package com.example.pachong.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {

  private final static SimpleDateFormat sdf1=new SimpleDateFormat(
    "yyyy-MM-dd HH:mm:ss");

  private final static SimpleDateFormat sdf2=new SimpleDateFormat(
    "yyyy-MM-dd");


  public static Date stringToDate(String obj,String myformat) {
    Date date=null;
    if(StringUtils.isNotBlank(obj)) {
      try {
        Format format=new SimpleDateFormat(myformat);
        date=(Date) format.parseObject(obj);
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
    return date;

  }

  /**
   * 转换时间
   *
   * @param dateFormat
   * @param date
   * @return
   */
  public static String transformDate(String dateFormat,Date date) {
    SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
    return sdf.format(date);
  }

  /**
   * 转换时间  patten yyyy-MM-dd HH24:mm:ss
   *
   * @param date
   * @return
   */
  public static String transformDate1(Date date) {
    return sdf1.format(date);
  }

  /**
   * 转换时间  patten yyyy-MM-dd
   *
   * @param date
   * @return
   */
  public static String transformDate2(Date date) {
    return sdf2.format(date);
  }


  /**
   * 日期函数，以当天为基数，返回指定天数后的日期
   * 返回日期格式为： yyyy-MM-dd
   *
   * @param field  操作的数据类型，如年，月，日
   * @param amount 总数
   * @return
   */
  public static String getStrDate(int field,int amount) {
    Calendar c=Calendar.getInstance();
    c.add(field,amount);
    return sdf2.format(c.getTime());
  }

  private final static int WEEK=1;
  private final static int MONTH=2;

  /**
   * 返回一个时间段，当前一周，或者当前一个月的时间段
   * 返回日期格式为： yyyy-MM-dd
   *
   * @param type 1-一周，2-一月
   * @return 时间
   */
  public static Map<String, String> getDate(int type) {
    Map<String, String> map=new HashMap<>();
    Calendar c=Calendar.getInstance();
    Calendar ca=Calendar.getInstance();
    if(type == WEEK) {
      //获取本周的时间字段
      c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
      String weekStart=sdf2.format(c.getTime());
      ca.setFirstDayOfWeek(Calendar.MONDAY);
      ca.set(Calendar.DAY_OF_WEEK,ca.getFirstDayOfWeek() + 6);
      String weekEnd=sdf2.format(ca.getTime());
      map.put("Start",weekStart);
      map.put("End",weekEnd);
    } else if(type == MONTH) {
      //获取当前月的开始时间和结束时间
      c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
      String monthStart=sdf2.format(c.getTime());
      ca.set(Calendar.DAY_OF_MONTH,ca.getActualMaximum(Calendar.DAY_OF_MONTH));
      String monthEnd=sdf2.format(ca.getTime());
      map.put("Start",monthStart);
      map.put("End",monthEnd);
    } else {
      return map;
    }
    return map;
  }
}
