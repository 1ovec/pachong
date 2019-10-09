package com.example.pachong.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author admin
 * @ClassName PageInfo
 * @Description
 * @Date 2019/10/9
 */
public class PageInfo {

  /**
   * jsoup方式 获取虎扑新闻列表页
   *
   * @param url 虎扑新闻列表页url
   */
  public static void jsoupList(String url) {
    try {
      Document document=Jsoup.connect(url).get();
      // 使用 css选择器 提取列表新闻 a 标签
      // <a href="https://voice.hupu.com/nba/2484553.html" target="_blank">霍华德：夏休期内曾节食30天，这考验了我的身心</a>
      Elements elements=document.select("div.news-list > ul > li > div.list-hd > h4 > a");
      for(Element element : elements) {
        //System.out.println(element);
        // 获取详情页链接
        String d_url=element.attr("href");
        // 获取标题
        String title=element.ownText();
        System.out.println("详情页链接：" + d_url + " ,详情页标题：" + title);

      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    //https://4dddnn.com/picture/detail/22054#header
    ///picture/list/%E6%B8%85%E7%BA%AF%E5%94%AF%E7%BE%8E#header
    //https://2nnncc.com/picture/list/%E6%B8%85%E7%BA%AF%E5%94%AF%E7%BE%8E#header
    //5324hu
    getJsoupList("https://www.5324hu.com/vod/html26/23105.html");
  }

  public static void getJsoupList(String url) {
    try {
      Document document=Jsoup.connect(url).get();
      System.out.println(document);
    } catch(IOException e) {
      e.printStackTrace();
    }
  }


}
