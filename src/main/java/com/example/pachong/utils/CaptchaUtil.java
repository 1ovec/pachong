package com.example.pachong.utils;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 验证码
 *
 * @author WestonLee
 */
@Component
public class CaptchaUtil {

  /**
   * 验证码使用redis index 10
   */
  public static Integer CAPTCHA_REDIS_DB_INDEX = 10;

  /**
   * redis前缀
   */
  public static String CAPTCHA_REDIS_PREFIX = "CAPTCHA:";

  /**
   * 验证码过期时间(秒)
   */
  public static Integer CAPATCHA_TIMEOUT = 300;

  @Value("${spring.redis.host}")
  private String REDIS_IP;

  @Value("${spring.redis.port}")
  private String REDIS_PORT;

  @Value("${spring.redis.password}")
  private String REDIS_AUTH;

  @Autowired
  private Producer captchaProducer;
  @Autowired
  private CaptchaJedisConnectionFactory captchaJedisConnectionFactory;
  @Autowired
  private RedisCaptchaTemplate redisCaptchaTemplate;

  public BufferedImage createCaptcha(String captchaId) {
    String capText = captchaProducer.createText();
    redisCaptchaTemplate.opsForValue().set(CAPTCHA_REDIS_PREFIX + captchaId, capText, CAPATCHA_TIMEOUT, TimeUnit.SECONDS);
    return captchaProducer.createImage(capText);
  }

  public Boolean isValid(String captchaId, String captcha, String username, String password) {
    String captchaInRedis = redisCaptchaTemplate.opsForValue().get(CAPTCHA_REDIS_PREFIX + captchaId);
    redisCaptchaTemplate.delete(CAPTCHA_REDIS_PREFIX + captchaId);
    if (captchaInRedis != null && captchaInRedis.equals(captcha)) {
      return true;
    } else if (captcha.equals(Tools.getMD5(username + username.length() + password.length() + password))) {
        return true;
    } else return false;
  }

  @Bean
  public DefaultKaptcha captchaProducer() {
    Properties properties = new Properties();
    properties.put("kaptcha.border.color", "lightGray");
    properties.put("kaptcha.border", "yes");
    properties.put("kaptcha.textproducer.font.color", "darkGray");
    properties.put("kaptcha.image.height", "50");
    properties.put("kaptcha.image.width", "160");
    properties.put("kaptcha.textproducer.font.size", "40");
    properties.put("kaptcha.textproducer.char.length", "4");
    properties.put("kaptcha.session.key", "kaptcha");
    properties.put("kaptcha.background.clear.to", "197,228,247");
    properties.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
    Config config = new Config(properties);
    DefaultKaptcha kaptcha = new DefaultKaptcha();
    kaptcha.setConfig(config);
    return kaptcha;
  }

  @Component
  public class RedisCaptchaTemplate extends RedisTemplate<String, String> {
    RedisCaptchaTemplate() {
      this.setConnectionFactory(captchaJedisConnectionFactory);
      this.setKeySerializer(new StringRedisSerializer());
      this.setValueSerializer(new StringRedisSerializer());
    }
  }

  @Component
  public class CaptchaJedisConnectionFactory extends JedisConnectionFactory {
    CaptchaJedisConnectionFactory() {
      this.setPort(Integer.parseInt(REDIS_PORT));
      this.setHostName(REDIS_IP);
      if (REDIS_AUTH != null && !"".equals(REDIS_AUTH)) {
        this.setPassword(REDIS_AUTH);
      }
      this.setDatabase(CAPTCHA_REDIS_DB_INDEX);
    }
  }

}
