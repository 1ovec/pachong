package com.example.pachong.utils;

public class Httputil {

  /*public static String postMethod(String url, String postStr) {
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
  }*/


}
