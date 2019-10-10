package com.example.pachong.utils;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * redis 连接池
 *
 */

@SuppressWarnings("deprecation")
public final class JedisFactory {

	private static final Logger logger = LoggerFactory.getLogger(JedisFactory.class);

	/**
	 * 可用连接实例的最大数目，默认值为8。<br/>
	 * 如果赋值为-1，则表示不限制<br/>
	 * 如果pool已经分配了maxActive个jedis实例。<br/>
	 * 则此时pool的状态为exhausted(耗尽)。
	 */
    private static int MAX_ACTIVE = 10000;

    /**
     * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。<br/>
     * 默认值也是8。
     */
    private static int MAX_IDLE =100;

   /**
    * 等待可用连接的最大时间，单位毫秒.<br/>
    * 默认值为-1，表示永不超时。<br/>
    * 如果超过等待时间，则直接抛出JedisConnectionException
    */
    private static int MAX_WAIT = 10000;

    private static int TIMEOUT = 10000;

    /**
     * 在borrow一个jedis实例时，是否提前进行validate操作。<br/>
     * 如果为true，则得到的jedis实例均是可用的
     */
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    private static List<String> hosts = new ArrayList<String>() ;

	/**
	 * 初始化Redis连接池
	 */
	private static void initialPool() {

		for (String host : hosts) {
			try {
				JedisPoolConfig config = new JedisPoolConfig();
				config.setMaxTotal(MAX_ACTIVE);
				config.setMaxIdle(MAX_IDLE);
				config.setMaxWaitMillis(MAX_WAIT);
				config.setTestOnBorrow(TEST_ON_BORROW);
				String [] str = host.trim().split(":");
				if(str.length == 2){
					jedisPool = new JedisPool(config, host.trim().split(":")[0], Integer.parseInt(host.trim().split(":")[1]), TIMEOUT);
				}else {
					jedisPool = new JedisPool(config, host.trim().split(":")[0], Integer.parseInt(host.trim().split(":")[1]), TIMEOUT,host.trim().split(":")[2]);
				}
			} catch (Exception e) {
				logger.error("First create JedisPool error : " + e);
				continue;
			}
		}

	}


    /**
     * 在多线程环境同步初始化
     */
    private static synchronized void poolInit() {
    	if (jedisPool == null) {
            initialPool();
        }
    }

    /**
     * 同步获取Jedis实例
     * @return Jedis
     */
    public synchronized static Jedis getJedis() {
        if (jedisPool == null) {
        	poolInit();
        }
        Jedis jedis = null;
        try {
            if (jedisPool != null) {
            	jedis = jedisPool.getResource();
            }
        } catch (Exception e) {
        	logger.error("Get jedis error : "+e);
        }
        return jedis;
    }


    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null && jedisPool !=null) {
        	jedisPool.returnResource(jedis);
        }
    }

    /**
     * 销毁jedis资源
     * @param jedis
     */
    public static void returnBrokenResource(final Jedis jedis) {
        try {
        	if (jedis != null && jedisPool !=null) {
            	jedisPool.returnBrokenResource(jedis);
            }
		} catch (Exception e) {

		}
    }


    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public static boolean exists(String key){
        boolean exists = false;

        Jedis jedis = null;
        try {
            jedis = getJedis() ;
            exists = jedis.exists(key) ;
        } catch (Exception e) {
            //释放redis对象
        	logger.error(MessageFormat.format("exists error :key={0}", key),e) ;
        	returnBrokenResource(jedis);
        } finally {
            //返还到连接池
        	returnResource(jedis);
        }

        return exists;
    }

    /**
     * 判断key是否存在
     *
     * @param bytes
     * @return
     */
    public static boolean exists(byte[] bytes){
        boolean exists = false;

        Jedis jedis = null;
        try {
            jedis = getJedis() ;
            exists = jedis.exists(bytes) ;
        } catch (Exception e) {
            //释放redis对象
        	logger.error(MessageFormat.format("exists error :key={0}", new String(bytes)),e) ;
        	returnBrokenResource(jedis);
        } finally {
            //返还到连接池
        	returnResource(jedis);
        }

        return exists;
    }

    /**
     * 设置键过期时间
     *
     * @param bytes
     * @param seconds 过期时间  单位：秒
     * @return
     */
    public static long expire(byte[] bytes,int seconds){
        long time = 0L;

        Jedis jedis = null;
        try {
            jedis = getJedis() ;
            time = jedis.expire(bytes, seconds) ;
        } catch (Exception e) {
            //释放redis对象
        	logger.error(MessageFormat.format("expire error :key={0}", new String(bytes)),e) ;
        	returnBrokenResource(jedis);
        } finally {
            //返还到连接池
        	returnResource(jedis);
        }

        return time;
    }

    /**
     * 设置键过期时间
     *
     * @param key
     * @param seconds 过期时间  单位：秒
     * @return
     */
    public static long expire(String key,int seconds){
        long time = 0L;

        Jedis jedis = null;
        try {
            jedis = getJedis() ;
            time = jedis.expire(key, seconds) ;
        } catch (Exception e) {
            //释放redis对象
        	logger.error(MessageFormat.format("expire error :key={0}", key),e) ;
        	returnBrokenResource(jedis);
        } finally {
            //返还到连接池
        	returnResource(jedis);
        }

        return time;
    }


    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static String get(String key){
        String value = null;

        Jedis jedis = null;
        try {
            jedis = getJedis() ;
            value = jedis.get(key);
        } catch (Exception e) {
            //释放redis对象
        	logger.error(MessageFormat.format("get error :key={0}", key),e) ;
        	returnBrokenResource(jedis);
        } finally {
            //返还到连接池
        	returnResource(jedis);
        }

        return value;
    }
    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static void set(String key,String value){
        Jedis jedis = null;
        try {
            jedis = getJedis() ;
            jedis.set(key, value) ;

        } catch (Exception e) {
            //释放redis对象
        	logger.error(MessageFormat.format("set error :key={0} ,value={1}", key,value),e) ;
        	returnBrokenResource(jedis);
        } finally {
            //返还到连接池
        	returnResource(jedis);
        }

    }
    /**
     * 删除数据
     *
     * @param key
     * @return
     */

	public static boolean del(String key){
    	boolean success = false;

        Jedis jedis = null;
        try {
            jedis = getJedis() ;

	        jedis.del(key) ;
	        success = true ;
        } catch (Exception e) {
            //释放redis对象
        	logger.error(MessageFormat.format("del error :key={0}", key),e) ;
        	returnBrokenResource(jedis);
        } finally {
            //返还到连接池
        	returnResource(jedis);
        }

        return success;
    }

    /**
     *
     * @param key
     * @return
     */
    public static void set(String key,String value,int seconds){
        Jedis jedis = null;
        try {
            jedis = getJedis() ;
            jedis.set(key, value) ;
            jedis.expire(key, seconds) ;

        } catch (Exception e) {
            //释放redis对象
        	logger.error(MessageFormat.format("set error :key={0} ,value={1}", key,value),e) ;
        	returnBrokenResource(jedis);
        } finally {
            //返还到连接池
        	returnResource(jedis);
        }

    }


    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static byte[] getBytes(byte[] key){
    	byte[] bytes = null;

        Jedis jedis = null;
        try {
            jedis = getJedis() ;
            bytes = jedis.get(key);
        } catch (Exception e) {
            //释放redis对象
        	e.printStackTrace() ;
        	logger.error(MessageFormat.format("getBytes error :key={0}", new String(key)),e) ;
        	returnBrokenResource(jedis);
        } finally {
            //返还到连接池
        	returnResource(jedis);
        }

        return bytes;
    }

    /**
     *
     * @param key
     * @return
     */
    public static void set(byte[] key,byte[] value){
        Jedis jedis = null;
        try {
            jedis = getJedis() ;
            jedis.set(key, value) ;

        } catch (Exception e) {
            //释放redis对象
        	logger.error(MessageFormat.format("set error :key={0} ,value={1}", new String(key),value),e) ;
        	returnBrokenResource(jedis);
        } finally {
            //返还到连接池
        	returnResource(jedis);
        }

    }

    /**
     *
     * @param key
     * @return
     */
    public static void set(byte[] key,byte[] value,int seconds){
        Jedis jedis = null;
        try {
            jedis = getJedis() ;
            jedis.set(key, value) ;
            jedis.expire(key, seconds) ;

        } catch (Exception e) {
            //释放redis对象
        	logger.error(MessageFormat.format("set error :key={0} ,value={1}", new String(key),value),e) ;
        	returnBrokenResource(jedis);
        } finally {
            //返还到连接池
        	returnResource(jedis);
        }

    }

    /**
     * 删除数据
     *
     * @param key
     * @return
     */

	public static boolean del(byte[] key){
    	boolean success = false;

        Jedis jedis = null;
        try {
            jedis = getJedis() ;

	        jedis.del(key) ;
	        success = true ;
        } catch (Exception e) {
            //释放redis对象
        	logger.error(MessageFormat.format("del error :key={0}", new String(key)),e) ;
        	returnBrokenResource(jedis);
        } finally {
            //返还到连接池
        	returnResource(jedis);
        }

        return success;
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static Object getObject(String key){
        Object obj = null;

        Jedis jedis = null;
        try {
            jedis = getJedis() ;
            byte[] bytes = jedis.get(key.getBytes());

	        obj = SerializeTools.unserialize(bytes);

        } catch (Exception e) {
            //释放redis对象
        	e.printStackTrace() ;
        	logger.error(MessageFormat.format("getObject error :key={0}", key),e) ;
        	returnBrokenResource(jedis);
        } finally {
            //返还到连接池
        	returnResource(jedis);
        }

        return obj;
    }



    /**
     * 获取数据
     *
     * @param key
     * @return
     */

	public static boolean delObject(String key){
    	boolean success = false;

        Jedis jedis = null;
        try {
            jedis = getJedis() ;

	        jedis.del(key.getBytes()) ;
	        success = true ;
        } catch (Exception e) {
            //释放redis对象
        	logger.error(MessageFormat.format("delObject error :key={0}", key),e) ;
        	returnBrokenResource(jedis);
        } finally {
            //返还到连接池
        	returnResource(jedis);
        }

        return success;
    }

    /**
     * 获取数据
     *
     * @param key
     * @param object
     * @return
     */
    public static void setObject(String key,Serializable object){
        Jedis jedis = null;
        try {
            jedis = getJedis() ;
            jedis.set(key.getBytes(), SerializeTools.serialize(object));
        } catch (Exception e) {
            //释放redis对象
        	logger.error(MessageFormat.format("setObject error :key={0}", key),e) ;
        	returnBrokenResource(jedis);
        } finally {
            //返还到连接池
        	returnResource(jedis);
        }

    }

    /**
     * 获取数据
     *
     * @param key
     * @param object
     * @param seconds 过期时间  单位：秒
     * @return
     */
    public static void setObject(String key,Serializable object,int seconds){
        Jedis jedis = null;
        try {
            jedis = getJedis() ;
            jedis.set(key.getBytes(), SerializeTools.serialize(object));
            jedis.expire(key.getBytes(), seconds) ;
        } catch (Exception e) {
            //释放redis对象
        	logger.error(MessageFormat.format("setObject error :key={0}", key),e) ;
        	returnBrokenResource(jedis);
        } finally {
            //返还到连接池
        	returnResource(jedis);
        }

    }

	public static List<String> getHosts() {
		return hosts;
	}

	/**
	 * 设置主机地址 <br/>
	 *  格式如 ip:port
	 * @param hosts
	 */
	public static void setHosts(List<String> hosts) {
		JedisFactory.hosts = hosts;
	}

	/**
	 * 设置主机地址  <br/>
	 * 多个主机以逗号分隔   <br/>
	 * 格式如192.168.0.100:6379,192.168.0.101:8888
	 * @param hostAddress
	 */
	public static void setHosts(String hostAddress) {
		String[] arr = hostAddress.split(",");
		for (String addr : arr) {
			hosts.add(addr);
		}

	}


	/**
	 * 设置主机地址 包含密码<br/>
	 * 多个主机以逗号分隔  <br/>
	 * 格式如192.168.0.100:6379:passwd,192.168.0.101:8888:passwd
	 * @param hostAddress
	 */
	public static void setHostsPasswd(String hostAddress) {
		String[] arr = hostAddress.split(",");
		for (String addr : arr) {
			hosts.add(addr);
		}

	}

	/**
	 * 获取 redis 实例 有密码
	 * @param ip
	 * @param port 端口
	 * @param passwd 密码
	 * @return
	 */
	public static Jedis getRedisBean(String ip,String port,String passwd) {
		Jedis jedis = null;
		if(StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(port) && StringUtils.isNotBlank(passwd)){
			JedisFactory.setHostsPasswd(ip+":"+port+":"+passwd) ;
			jedis = JedisFactory.getJedis();
		}
		return jedis;
	}

	/**
	 * 获取 redis 实例 无密码
	 * @param ip
	 * @param port 端口
	 * @return
	 */
	public static Jedis getRedisBean(String ip,String port) {
		Jedis jedis = null;
		if(StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(port)){
			JedisFactory.setHosts(ip+":"+port) ;
			jedis = JedisFactory.getJedis();
		}
		return jedis;
	}




}
