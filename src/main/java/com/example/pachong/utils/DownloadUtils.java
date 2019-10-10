package com.example.pachong.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by 14457 on 2017/6/26.
 */
public class DownloadUtils {

  /**
   * 下载文件
   * @param response
   * @param downloadName
   * @param url
   * @throws IOException
   */
  public static void downloadFile(HttpServletResponse response,String downloadName,String url) throws IOException {
    BufferedInputStream bis = null ;
    BufferedOutputStream bos = null ;
    try {
      response.reset() ;
      response.setContentType("application/octet-stream");
      response.setHeader("Content-disposition", "attachment; filename="+ downloadName);
      CloseableHttpClient client = HttpClientBuilder.create().build();
      HttpGet httpGet = new HttpGet(url);
      httpGet.addHeader("User-Agent", "Mozilla/5.0");
      CloseableHttpResponse httpResponse = client.execute(httpGet);
      bis = new BufferedInputStream(httpResponse.getEntity().getContent());
      bos = new BufferedOutputStream(response.getOutputStream());
      byte[] buff = new byte[1024];
      int len = -1;
      while ((len = bis.read(buff)) != -1) {
        bos.write(buff, 0, len);
      }
      bis.close();
      bos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }finally{
      if(bis != null){
        bis.close() ;
      }
      if(bos != null){
        bos.close() ;
      }
    }

  }

  public static void downloadZipFile(HttpServletRequest request,HttpServletResponse response,String downloadName,String recordFileUrl,Integer downRuler,Integer isHidden,List<Map<String, Object>> list) throws IOException {
    OutputStream out1 = new BufferedOutputStream(response.getOutputStream());
    response.addHeader("Content-Disposition", "attachment;filename="+new String((downloadName+".zip").getBytes("gbk"),"iso8859-1"));
    ZipOutputStream zipOut = new ZipOutputStream(out1);
    synchronized (zipOut) {
      try{
        byte filedata[] = new byte[1024];
        for (Map<String, Object> map:list) {
          String fileName = "";
          String phone =  map.get("phone").toString();
          String time = "";

          if(map.get("ctime")==null){
            time = LocalDate.now().toString()+" "+LocalTime.now().toString();
            time = time.substring(0,time.length()-4);
          }else{
            time = map.get("ctime").toString();
            time = time.substring(0,time.length()-2);
          }

          if(isHidden!=1){
            if(phone!=null&&phone.length()==11){
              phone = phone.substring(0,3)+"****"+phone.substring(phone.length()-4,phone.length());
            }else if(phone.length()>11){
              phone = phone.substring(1,4)+"****"+phone.substring(phone.length()-4,phone.length());
            }
          }else{
            if(phone.length()>11){
              phone = phone.substring(1,phone.length());
            }
          }
          if(downRuler==null||downRuler==0||downRuler==1){
            fileName = map.get("uname") + "_" +phone+ "_" + time + ".mp3";
          }else if(downRuler == 2){
            fileName = map.get("username") + "_" +phone+ "_" + time + ".mp3";
          }else if(downRuler == 3){
            fileName = phone+ "_" + time + ".mp3";
          }
          String recordUrl = map.get("recordUrl").toString();
          String url = recordFileUrl + recordUrl.substring(0, recordUrl.indexOf("_")) + "/" + recordUrl;
          try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("User-Agent", "Mozilla/5.0");
            CloseableHttpResponse httpResponse = client.execute(httpGet);
            InputStream origin = new BufferedInputStream(httpResponse.getEntity().getContent(),1024);
            ZipEntry entry = new ZipEntry(fileName);
            zipOut.putNextEntry(entry);
            int count;
            while ((count = origin.read(filedata, 0, 1024)) != -1) {
              zipOut.write(filedata, 0, count);
            }
            zipOut.closeEntry();
            origin.close();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        zipOut.flush();
        zipOut.close();
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
  }

  /**
   * 处理下载的文件名称
   * @param request
   * @param downloadName 导出文件名称
   * @return
   * @throws UnsupportedEncodingException
   */
  public static String dealDownloadName(HttpServletRequest request ,String downloadName ) throws UnsupportedEncodingException{

    final String userAgent = request.getHeader("USER-AGENT");
    try {
      if(StringUtils.contains(userAgent, "MSIE")){
        downloadName = URLEncoder.encode(downloadName,"UTF8");
      }else if(StringUtils.contains(userAgent, "Mozilla")){
        downloadName = new String(downloadName.getBytes("gbk"), "ISO8859-1");
      }else{
        downloadName = URLEncoder.encode(downloadName,"UTF8");
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return downloadName ;
  }

}
