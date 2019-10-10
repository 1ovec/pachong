package com.example.pachong.utils;

import org.slf4j.Logger;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/24.
 */
public class HessianResult {

  public final static String CODEKEY = "code";
  public final static String RESULTKEY = "result";
  public final static String SUCCESS = "0000";
  public final static String SUCCESS_1 = "00";
  public final static String FAIL = "-1";

  public final static String QUOTES = "\"";
  public final static String ACCESSKEY = "access"; //接口AddAccessPhones  result返回key值
  //3.2.13	查询代理商所有坐席{"username":"1001","userpasswd":"1001","agentgroupid":282,"userregtype":0}
  public final static String USERNAME = "username";
  public final static String USERPASSWD = "userpasswd";
  public final static String AGENTGROUPID = "agentgroupid";
  public final static String USERREGTYPE = "userregtype";
  public final static String AGENTIDKEY = "agentid";

  public final static String CALLPATTERN_ZHIXIAN = "1";//直线模式
  public final static String CALLPATTERN_KONGJIAN = "0";//控件模式

  public static boolean validate(String result,Logger logger,String action ,Object obj){
    Map<String, Object> map = JsonUtil.toMap(JsonUtil.parseJson(result));
    String code = map.get(CODEKEY).toString().replaceAll(QUOTES, "");
    if(code.equals(SUCCESS)){
      return true;
    }
    logger.error("----"+ action +" error result:" + result +" " +JsonUtil.convertObject2Json(obj));
    return false;
  }

  public static String getCode(String result){
    Map<String, Object>  map = JsonUtil.toMap(JsonUtil.parseJson(result));
    String code = map.get(CODEKEY).toString().replaceAll(QUOTES, "");
    return code;
  }

  public static Object getResult(String result) {
    Map<String, Object>  map = JsonUtil.toMap(JsonUtil.parseJson(result));
    return map.get("result");
  }

}
