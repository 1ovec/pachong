package com.example.pachong.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by jim on 2017/3/25.
 */
public class Tools {

    public static String getSequenceId(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateStr = formatter.format(new Date());

        Random rnd = new Random();
        String rand = "";

        for (int i=0; i<9;i++)
        {
            rand = rand + String.valueOf(rnd.nextInt(10));
        }

        return dateStr+rand;
    }

  public static String getMD5(String src)
  {
    String md5 = "";
    byte[] rBytes = new byte[0];
    try
    {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] bytes = src.getBytes();
      rBytes = md.digest(bytes);
      md5 = getHexs(rBytes);
    }
    catch (NoSuchAlgorithmException e) {
    }
    return md5;
  }

  private static String getHexs(byte[] bytes)
  {
    StringBuilder buff = new StringBuilder();
    for (int index = 0; index < bytes.length; index++)
    {
      buff.append(getHex(bytes[index]));
    }
    return buff.toString();
  }

  public static String getHex(byte b)
  {
    String hex = "";
    if (b > 0)
    {
      hex = Integer.toHexString(b);
    }
    else
    {
      hex = Integer.toHexString(b & 0xFF);
    }
    if (hex.length() == 1)
    {
      hex = new StringBuilder().append("0").append(hex).toString();
    }
    return hex;
  }
}
