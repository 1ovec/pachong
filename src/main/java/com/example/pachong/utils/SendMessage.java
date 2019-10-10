package com.example.pachong.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SendMessage {

	private static Logger logger =LoggerFactory.getLogger("SendMessageLog");


	public static String sendPostExt(String url ,String bodyXml ){
		logger.debug("[request URL : "+url+" , request body : "+bodyXml+" ]");
		//创建一个HTTP客户端
		CloseableHttpClient client = HttpClientBuilder.create().build();
		//创建post并将发送的url去空格
		HttpPost post = new HttpPost(url.trim());
		//设置请求超时时间
		RequestConfig requestConfig = RequestConfig.custom()
			.setConnectionRequestTimeout(10000)
			.setConnectTimeout(10000)
			.setSocketTimeout(10000).build();
		post.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		String res = null;
		try {
			if(StringUtils.isNotEmpty(bodyXml)){
				StringEntity entity = new StringEntity(bodyXml, "utf-8");
				post.setEntity(entity);
			}
			response = client.execute(post);
			if(response.getStatusLine().getStatusCode()==200){
				logger.debug("[ response statusCode : "+response.getStatusLine().getStatusCode()+" ], [request URL : "+url+" , request body : "+bodyXml+" ]");
				HttpEntity entity = response.getEntity();
				res = EntityUtils.toString(entity, "utf-8");
				logger.debug("[ response xml: "+res + " ]");
			}else{
				logger.debug("[ response statusCode : "+response.getStatusLine().getStatusCode()+" ], [request URL : "+url+" , request body : "+bodyXml+" ]");
			}
		} catch (ClientProtocolException e) {
			logger.error(" [ post :客户端协议异常!"+e + " ] , [request URL : "+url+" , request body : "+bodyXml+" ]");
		} catch (IOException e) {
			logger.error("[ post :io 异常!"+e + " ] , [request URL : "+url+" , request body : "+bodyXml+" ]");
		} finally {
			//释放post请求连接
			post.releaseConnection();
		}
		return res;
	}


	public static String sendPostExt(String url ,String phones,String msg ,String suffix){
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url.trim());
		RequestConfig requestConfig = RequestConfig.custom()
			.setConnectionRequestTimeout(30000)
			.setConnectTimeout(30000)
			.setSocketTimeout(30000).build();
		post.setConfig(requestConfig);
		post.addHeader("Content-Type", "application/x-www-form-urlencoded");
		String param = "phone="+phones+"&msg="+msg+"&suffix="+suffix;
		String res = null;
		CloseableHttpResponse response = null;
		StringEntity entity = new StringEntity(param, "UTF-8");
		post.setEntity(entity);
		try {
			response = client.execute(post);
			if(response.getStatusLine().getStatusCode()==200){
				logger.info("StatusCode:[ "+response.getStatusLine().getStatusCode()+" ] post url:{"+url+"}"+"post package: "+msg);
				HttpEntity resEntity = response.getEntity();
				res = EntityUtils.toString(resEntity, "utf-8");
				logger.info("短信发送返回:  "+res);
			}else{
				logger.error("StatusCode:["+response.getStatusLine().getStatusCode()+"] post url:{ "+url+"}"+" post package: "+msg);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}




	public static String sendGet(String url){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url.trim());
        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(5000)
            .setConnectTimeout(5000)
            .setSocketTimeout(5000).build();
        get.setConfig(requestConfig);
        CloseableHttpResponse response = null ;
        String res = null;
        try {
            response = client.execute(get);
            if(response.getStatusLine().getStatusCode()==200){
                HttpEntity entity = response.getEntity();
                res = EntityUtils.toString(entity,"UTF-8");
            }else{
                logger.error("get 异常!" + "[" + response.getStatusLine().getStatusCode() + "]");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            get.releaseConnection();
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("Get CloseableHttpClient close exception!!");
            }
        }
        return res;
    }

}
