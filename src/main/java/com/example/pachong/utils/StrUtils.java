package com.example.pachong.utils;

import org.apache.commons.lang3.StringUtils;

public class StrUtils {

	public static String getPhoneTail(String obj) {
		String result = "";
		try{
      if (StringUtils.isNotBlank(obj) && obj.length() >= 4){
        result = obj.substring(obj.length()-4,obj.length());
      }
    }catch(Exception e){
		  e.printStackTrace();
    }
		return result;
	}
}
