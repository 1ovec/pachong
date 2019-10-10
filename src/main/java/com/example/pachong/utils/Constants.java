package com.example.pachong.utils;


public class Constants {
	/**hdcc呼叫结束事件*/
	public final static String HDCC_CALL_END ="201";
	/**WEB800呼叫结束*/
	public final static String WEB_CALL_END = "301";
	/**hdcc呼叫成功*/
	public final static String HDCC_CALL_SUCCESS ="2";
	/**坐席接通*/
	public final static String HDCC_CALL_CONNECT_SUCCESS ="1";
	/** 自动外呼呼叫失败 */
	public static final String CALL_LOST = "3";
	/**被叫无应答状态*/
	public static final String CALL_ERROR_REASON ="20";
	/** 项目质检类型：比例*/
	public static final int ITEM_QC_TYPE_1=1;
	/** 项目质检类型：沟通结果*/
	public static final int ITEM_QC_TYPE_2=2;
	/** 项目质检类型：级别*/
	public static final int ITEM_QC_TYPE_3=3;
  /**203：电话接通*/
  public static final String HDCC_203 = "203";
	/**204：坐席签入*/
	public static final String HDCC_204 = "204";
	/**205：坐席签出 */
	public static final String HDCC_205 = "205";
	/**206：坐席置忙*/
	public static final String HDCC_206 = "206";
	/**207：坐席置闲*/
	public static final String HDCC_207 = "207";
	/**220：多方会议开始成功通知事件*/
	public static final String HDCC_220 = "220";
	/**221：多方会议开始成功通知事件*/
	public static final String HDCC_221 = "221";
	/**222：移除多方会议成功通知事件*/
	public static final String HDCC_222 = "222";
	/**223：多方会议成员接通事件*/
	public static final String HDCC_223 = "223";
	/**224：多方会议成员挂机或没有接通事件*/
	public static final String HDCC_224 = "224";
	/**225：多方会议没有可用资源通知事件*/
	public static final String HDCC_225 = "225";
	/**226：多方会议没有可用通道通知事件*/
	public static final String HDCC_226 = "226";
	/**227：多方会议修改成员属性通知事件*/
	public static final String HDCC_227 = "227";
	/**228：多方会议修改成员属性通知事件*/
	public static final String HDCC_228 = "228";
	public static String NORMAL = "normal";
	public static String UNDEFINED = "undefined";
  public static final String SUCCESS = "0000";

  /**沟通结果成功*/
  public static final Integer CALL_RESULT_SUCCESS = 4;
  /**沟通结果失败*/
  public static final Integer CALL_RESULT_FAIL = 2;
  public static final Integer CALL_RESULT_NOTICE = -1;
  public static final String SMS_phone = "{phone}";
  public static final String SMS_seatsName = "{seatsName}";
  public static final String SMS_seatsPhone = "{seatsPhone}";
  public static final String SMS_seatsWeChat = "{seatsWeChat}";
  public static final String SMS_teamName = "{teamName}";
  public static final String SMS_teamAddress = "{teamAddress}";
  public static final String SMS_teamPhone = "{teamPhone}";
  public static final String SMS_itemName = "{itemName}";







}
