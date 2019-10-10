package com.example.pachong.utils;

import java.util.UUID;

/**
 * 生成唯一标示
 * @author jim
 *
 */
public class UUIDUtil {

	public static String getUUID(){
	      UUID uuid = UUID.randomUUID();
	      String id = uuid.toString();
	      return id.replaceAll("-", "").substring(0,25);
	}
}
