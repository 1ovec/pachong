package com.example.pachong.utils;

import jodd.http.*;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Httputil {

  public static String postMethod(String url, String postStr) {
    String rst = "";
    try{
      HttpRequest httpRequest = HttpRequest.post(url).timeout(5000);
      httpRequest.bodyText(postStr);
      httpRequest.header("content-type", "application/json", true);
      httpRequest.header("accept", "application/json");
      HttpResponse response = httpRequest.send();
      rst = response.bodyText();
      response.close();
    }catch (Exception e){
      e.printStackTrace();
    }
    return rst;
  }

  public static String getMethod(String url) {
    String rst="";
    try{
      HttpResponse response = HttpRequest.get(url).connectionTimeout(3000).send();
      rst = response.bodyText();
      response.close();
    }catch (Exception e){
      e.printStackTrace();
    }
    return rst;
  }

  public static Map getParameterMap(HttpServletRequest request) {
    // 参数Map
    Map properties = request.getParameterMap();
    // 返回值Map
    Map returnMap = new HashMap();
    Iterator entries = properties.entrySet().iterator();
    Map.Entry entry;
    String name = "";
    String value = "";
    while (entries.hasNext()) {
      entry = (Map.Entry) entries.next();
      name = (String) entry.getKey();
      Object valueObj = entry.getValue();
      if(null == valueObj){
        value = "";
      }else if(valueObj instanceof String[]){
        String[] values = (String[])valueObj;
        for(int i=0;i<values.length;i++){
          value = values[i] + ",";
        }
        value = value.substring(0, value.length()-1);
      }else{
        value = valueObj.toString();
      }
      returnMap.put(name, value);
    }
    return returnMap;
  }

  public static String sendSingleMsg(String url , String phone ,String msg , String suffix){
    CloseableHttpClient client = HttpClientBuilder.create().build();
    HttpPost post = new HttpPost(url.trim());
    RequestConfig requestConfig = RequestConfig.custom()
      .setConnectionRequestTimeout(300000)
      .setConnectTimeout(300000)
      .setSocketTimeout(300000).build();
    post.setConfig(requestConfig);
    post.addHeader("Content-Type", "application/x-www-form-urlencoded");
    String param = "phone="+phone+"&msg="+msg+"&suffix="+suffix;
    String res = null;
    CloseableHttpResponse response = null;
    StringEntity entity = new StringEntity(param, "UTF-8");
    post.setEntity(entity);
    try {
      response = client.execute(post);
      if(response.getStatusLine().getStatusCode()==200){
        HttpEntity resEntity = response.getEntity();
        res = EntityUtils.toString(resEntity, "utf-8");
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return res;
  }


}
