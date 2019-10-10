package com.example.pachong.utils;

import huaplus.bean.LoginUser;

public class SqlUtil {
  /**
   * 获取号码查询likeStr
   * @param loginUser
   * @param phone
   * @return
   */
  public static String getPhoneLikeStr(LoginUser loginUser, String phone){
    return getPhoneLikeStr(loginUser.getAgent().getHidePhone() == 0,phone);
  }

  public static String getPhoneLikeStr(Boolean isHidePhone, String phone){
    if (phone == null || phone.equals("")) {
      return null;
    }
    String phoneStr = "";
    if(isHidePhone){
      if (phone.length() == 4){
        phoneStr = "%" + phone;
      }else if(phone.length() == 3 && (phone.charAt(0) == '1' ||phone.charAt(0) == '0')){
        phoneStr = phone + "%";
      }else {
        phoneStr = "%" + phone;
      }
    }else {
      phoneStr = "%" + phone + "%";
    }
    return phoneStr;
  }

  /*public static String filterPhoneStr(Boolean isHidePhone, String phone){
    if (phone == null || phone.equals("")) {
      return null;
    }
    String phoneStr = "";
    if(isHidePhone){
      if (phone.length() <= 4){
        Boolean isContain = phone.contains("*");
        if(isContain){
          phoneStr = phone.replace("*","");
        }else {
          phoneStr = phone;
        }
      }else if(phone.length() > 4 && phone.length() < 11){
        String phoneDelete = phone.substring(0,3);
        phoneStr = phoneDelete;
      }else {
        String phoneDelete = phone.substring(7,11);
        phoneStr = phoneDelete;
      }
    }
    return phoneStr;
  }*/
}
