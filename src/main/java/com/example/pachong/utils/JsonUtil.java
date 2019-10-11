package com.example.pachong.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017/3/24.
 */
public class JsonUtil {

  private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

  /*public static Map<String, Object> toMap(JsonObject json) {
    Map<String, Object> map = new HashMap<String, Object>();
    Set<Entry<String, JsonElement>> entrySet = json.entrySet();
    for (Iterator<Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext(); ) {
      Entry<String, JsonElement> entry = iter.next();
      String key = entry.getKey();
      Object value = entry.getValue();
      if (value instanceof JsonArray)
        map.put((String) key, toList((JsonArray) value));
      else if (value instanceof JsonObject)
        map.put((String) key, toMap((JsonObject) value));
      else
        map.put((String) key, value);
    }
    return map;
  }


  public static List<Object> toList(JsonArray json) {
    List<Object> list = new ArrayList<Object>();
    for (int i = 0; i < json.size(); i++) {
      Object value = json.get(i);
      if (value instanceof JsonArray) {
        list.add(toList((JsonArray) value));
      } else if (value instanceof JsonObject) {
        list.add(toMap((JsonObject) value));
      } else {
        list.add(value);
      }
    }
    return list;
  }


  public static JsonObject parseJson(String json) {
    JsonParser parser = new JsonParser();
    JsonObject jsonObj = parser.parse(json).getAsJsonObject();
    return jsonObj;
  }

  public static String convertObject2Json(Object object) {
    Gson gson = new Gson();
    return gson.toJson(object);
  }


  public static Object jsonToBean(JSONObject json, Class cls) {
    Object obj = null;

    try {
      obj = cls.newInstance();

      // 取出Bean里面的所有方法
      Method[] methods = cls.getMethods();
      for (int i = 0; i < methods.length; i++) {
        // 取出方法名
        String methodName = methods[i].getName();
        // 取出方法的类型
        Class[] clss = methods[i].getParameterTypes();
        if (clss.length != 1) {
          continue;
        }

        // 若是方法名不是以set开始的则退出本次循环
        if (methodName.indexOf("set") < 0) {
          continue;
        }

        // 类型
        String type = clss[0].getSimpleName();

        String key = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);

        // 如果map里有该key
        if (json.has(key) && json.get(key) != null) {
          setValue(type, json.get(key), methods[i], obj);
        }
      }
    } catch (Exception ex) {
      logger.error("JSONObject转JavaBean失败", ex);
    }

    return obj;
  }

  *//**
   * 根据key从JSONObject对象中取得对应值
   * @param json
   * @param key
   * @return
   * @throws JSONException
   *//**//*
      public static String getString(JsonObject json, String key) throws JSONException
	    {
	        String retVal = null;
	        if(json.isNull(key))
	        {
	            retVal = "";
	        }
	        else
	        {
	            retVal = json.getString(key);
	        }
	        return retVal;
	    }
	      *//*

  *//**
   * 给JavaBean的每个属性设值
   *
   * @param type
   * @param value
   * @param method
   * @param bean
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   *//*
  private static void setValue(String type, Object value, Method method, Object bean) {
    if (value != null && !"".equals(value)) {
      try {
        if ("String".equals(type)) {
          method.invoke(bean, new Object[]{value});
        } else if ("int".equals(type) || "Integer".equals(type)) {
          method.invoke(bean, new Object[]{new Integer("" + value)});
        } else if ("double".equals(type) || "Double".equals(type)) {
          method.invoke(bean, new Object[]{new Double("" + value)});
        } else if ("float".equals(type) || "Float".equals(type)) {
          method.invoke(bean, new Object[]{new Float("" + value)});
        } else if ("long".equals(type) || "Long".equals(type)) {
          method.invoke(bean, new Object[]{new Long("" + value)});
        } else if ("int".equals(type) || "Integer".equals(type)) {
          method.invoke(bean, new Object[]{new Integer("" + value)});
        } else if ("boolean".equals(type) || "Boolean".equals(type)) {
          method.invoke(bean, new Object[]{Boolean.valueOf("" + value)});
        } else if ("BigDecimal".equals(type)) {
          method.invoke(bean, new Object[]{new BigDecimal("" + value)});
        } else if ("Date".equals(type)) {
          Class dateType = method.getParameterTypes()[0];
          if ("java.util.Date".equals(dateType.getName())) {
            java.util.Date date = null;
            if ("String".equals(value.getClass().getSimpleName())) {
              String time = String.valueOf(value);
              String format = null;
              if (time.indexOf(":") > 0) {
                if (time.indexOf(":") == time.lastIndexOf(":")) {
                  format = "yyyy-MM-dd H:mm";
                } else {
                  format = "yyyy-MM-dd H:mm:ss";
                }
              } else {
                format = "yyyy-MM-dd";
              }
              SimpleDateFormat sf = new SimpleDateFormat();
              sf.applyPattern(format);
              date = sf.parse(time);
            } else {
              date = (java.util.Date) value;
            }

            if (date != null) {
              method.invoke(bean, new Object[]{date});
            }
          } else if ("java.sql.Date".equals(dateType.getName())) {
            Date date = null;
            if ("String".equals(value.getClass().getSimpleName())) {
              String time = String.valueOf(value);
              String format = null;
              if (time.indexOf(":") > 0) {
                if (time.indexOf(":") == time.lastIndexOf(":")) {
                  format = "yyyy-MM-dd H:mm";
                } else {
                  format = "yyyy-MM-dd H:mm:ss";
                }
              } else {
                format = "yyyy-MM-dd";
              }
              SimpleDateFormat sf = new SimpleDateFormat();
              sf.applyPattern(format);
              date = new Date(sf.parse(time).getTime());
            } else {
              date = (Date) value;
            }

            if (date != null) {
              method.invoke(bean, new Object[]{date});
            }
          }
        } else if ("Timestamp".equals(type)) {
          Timestamp timestamp = null;
          if ("String".equals(value.getClass().getSimpleName())) {
            String time = String.valueOf(value);
            String format = null;
            if (time.indexOf(":") > 0) {
              if (time.indexOf(":") == time.lastIndexOf(":")) {
                format = "yyyy-MM-dd H:mm";
              } else {
                format = "yyyy-MM-dd H:mm:ss";
              }
            } else {
              format = "yyyy-MM-dd";
            }
            SimpleDateFormat sf = new SimpleDateFormat();
            sf.applyPattern(format);
            timestamp = new Timestamp(sf.parse(time).getTime());
          } else {
            timestamp = (Timestamp) value;
          }

          if (timestamp != null) {
            method.invoke(bean, new Object[]{timestamp});
          }
        } else if ("byte[]".equals(type)) {
          method.invoke(bean, new Object[]{new String("" + value).getBytes()});
        }
      } catch (Exception ex) {
        logger.error("JSONObject赋值给JavaBean失败", ex);
      }
    }
  }


  *//**
   * 将Model转换成JSONObject
   *//**//*
	    @SuppressWarnings("unchecked")
	    public static JSONObject coverModelToJSONObject(Object o) throws Exception{
	        JSONObject json = new JSONObject();
	        Class clazz = o.getClass();
	        Field [] fields = clazz.getDeclaredFields();
	        for(int i=0; i< fields.length; i++){
	            Field f = fields[i];
	            json.put(f.getName(), invokeMethod(clazz,f.getName(),o));
	        }
	        return json;
	    }


	    *//*

  *//**
   * 将list转换成JSONArray
   *//**//*
	    public static JSONArray coverModelToJSONArray(List list) throws Exception{
	        JSONArray array = null;
	        if(list.isEmpty()){
	            return array;
	        }
	        array = new JSONArray();
	        for(Object o : list){
	            array.put(coverModelToJSONObject(o));
	        }
	        return array;
	    }  *//*
  @SuppressWarnings("unchecked")
  private static Object invokeMethod(Class c, String fieldName, Object o) {
    String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    Method method = null;
    try {
      method = c.getMethod("get" + methodName);
      return method.invoke(o);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return "";
    }
  }

  public static Map<String, Object> parseMap(String content) {
    if (content == null || content.length() == 0)
      return new HashMap<String, Object>();

    try {
      return new ObjectMapper().readValue(content, Map.class);
    } catch (Exception ex) {
      logger.error(ex.getMessage());
    }

    return null;
  }


  private static ValueFilter filter = new ValueFilter() {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Object process(Object source, String name, Object value) {

      if (value != null) {
        if (value instanceof Date) {
          return sdf.format(value);
        }
      }
      return value;
    }

  };

  *//**
   * 对象转成JSON格式字符串
   *
   * @param obj
   * @return
   *//*
  public static String toJson(Object obj) {

    SerializeWriter sw = new SerializeWriter();
    JSONSerializer serializer = new JSONSerializer(sw);

    serializer.getValueFilters().add(filter);
    serializer.config(SerializerFeature.WriteMapNullValue, true); // 空的map转成null
    serializer.config(SerializerFeature.WriteNullListAsEmpty, true); // 空的list转成[]
    serializer.config(SerializerFeature.WriteNullNumberAsZero, true); // 空的数字转成0
    serializer.config(SerializerFeature.WriteNullBooleanAsFalse, true); // 空的boolean转成false
    serializer.config(SerializerFeature.WriteNullStringAsEmpty, true); // 空的string转成""

    serializer.write(obj);

    return sw.toString();
  }

  *//**
   * 将Json字符串转换成指定的pojoClass类型的对象
   *
   * @param jsonString json字符串
   * @param pojoClass
   * @return
   *//*
  public static Object jsonStringToObject(String jsonString,
                                          Class<?> pojoClass) {
    return JSON.parseObject(jsonString, pojoClass);
  }

  *//**
   * 将字符串转换成Map对象返回
   *
   * @param jsonString json字符串
   * @return
   *//*
  public static Map<String, Object> getMapFromJson(String jsonString) {
    com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(jsonString);
    Map<String, Object> map = new HashMap<String, Object>();
    Set<Entry<String, Object>> entryseSet = jsonObject.entrySet();
    for (Entry<String, Object> entry : entryseSet) {
      map.put(entry.getKey(), entry.getValue());
    }

    return map;
  }

  *//**
   * 字符串是否包含属性key
   *
   * @param jsonString json字符串
   * @return
   *//*
  public static boolean containsKey(String jsonString, String key) {
    com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(jsonString);
    return jsonObject.containsKey(key);
  }


  *//**
   * @param srcData  过滤信息源
   * @param propName 敏感属性字段
   * @return data     返回过滤后的信息源
   * @Description: 过滤敏感信息
   *//*
  public String filterSensitiveProperty(String srcData, String propName) {
    StringBuffer sb = new StringBuffer();
    try {
      Pattern FILTER_REGEX1 = Pattern.compile("\"(\\w*(" + propName + ")\\w*)\":\"(\\S*?)\"", Pattern.CASE_INSENSITIVE);

      Matcher regexMatcher = FILTER_REGEX1.matcher(srcData);
      while (regexMatcher.find()) {
        regexMatcher.appendReplacement(sb, "\"$1\":\"******\"");
      }
      regexMatcher.appendTail(sb);
    } catch (Exception ex) {
      return srcData;
    }
    return sb.toString();
  }*/

}

