package com.aurora.util;

import java.util.*;

/**
 * @author 51066
 * @作者 曹媛媛
 * @日期 2020年12月18日 下午5:56:05 
 * @说明: 基础常量类
 */
public class BasicCommonConstant {

	public static final String EMPTY_STRING = "";
	// 下划线拼接
	public static final String IND_X = "_";
	// 斜拼接
	public static final String IND_Z = "/";
	// 横线拼接
	public static final String IND_H = "-";
	// 竖线拼接
	public static final String IND_S = "|";
	public static final String IND_S_ZY = "\\|";
	// 竖线拼接
	public static final String IND_D = "、";
	//左括号
	public static final String IND_L = "(";
	//右括号
	public static final String IND_R = ")";

	// #拼接
	public static final String IND_J = "#";
	// 颜色分割拼接
	public static final String COLOR = "color";
	// 颜色分割拼接code
	public static final String CODE = "code";

	public static final String AMOUNT_MARK = "¥"; // 金额符号
	// 序列号名称
	public static final String serialVersionUID = "serialVersionUID";

	public static final String IP = "IP";


	/**
	 * 报表表头拼接符号
	 */
	public static final String IND_COMMA = ",";
	
	/**
	 * 报表表头拼接符号
	 */
	public final static String CONNECTSYMBOL = "_&_";

	/**
	 * 数据库默认为-1的
	 */
	public static final Integer DEF_INT = -1;
	/**
	 * 数据库默认为-1的
	 */
	public static final String DEF_STRING = "-1";

	/**
	 * 数据库默认为-100的
	 */
	public static final Integer DEF_INT_100 = -100;

	/**
	 * 操作类型 add新增  edit修改 delete 删除
	 */
    public static final String ADD = "add";
	public static final String EDIT = "edit";
	public static final String DELETE = "delete";
	
	
	/**
	 * 全选标识 part部分 all全选
	 */
	public static final String PART = "part";
	public static final String ALL = "all";

	/**
	 * excel返回信息key
	 **/
	public static final String RETURN_CODE = "code";
	public static final String RETURN_MESSAGE = "message";
	public static final String RETURN_DATAJSON = "datajson";
	public static final String CODE_0000 = "0000";

	/**
	 * 任务类型-下载
	 */
	public static final String DOWNLOAD = "0";
	/**
	 * 任务类型-上传
	 */
	public static final String UPLOAD = "1";
	/**
	 * 任务类型-上传导致的下载文件
	 */
	public static final String UPLOAD_DOWN = "2";

	/**
	 * 附件类型 1-文件，2-图片
	 */
	public static final String ATTACHMENT_TYPE_FILE = "1";
	/**
	 * 附件类型 1-文件，2-图片
	 */
	public static final String ATTACHMENT_TYPE_PIC = "2";

	/**
	 *  标识：1肯定 0否定
	 */
	public static final String YES = "1";
	public static final String NO = "0";
	
	/**
	 *  标识：1肯定 0否定
	 */
	public static final Integer YES_INIT = 1;
	public static final Integer NO_INIT = 0;
	
	
	/**
	 *  删除状态
	 */
	public static final String DLE_FLAG_0 = "0";
	
	public static final String DLE_FLAG_1 = "1";

	public static final String DLE_FLAG_2 = "2";
	
	/**
	 *  状态 integer类型
	 */
	public static final Integer FLAG_INIT_0 = 0;
	
	public static final Integer FLAG_INIT_1 = 1;
	
	public static final Integer FLAG_INIT_2 = 2;

	/**
	 * redis返回状态码值
	 * 	000000	生成中	redis成功状态,下载中心显示下载按钮，上传中心不显示错误信息下载按钮或下载按钮
	 * 	111111	等待中	redis等待状态,下载中心不显示下载按钮，上传中心不显示错误信息下载按钮或下载按钮
	 * 	999999	生成失败  redis失败状态，下载中心不显示下载按钮，上传中心显示错误信息下载按钮
	 */
	public static final String REDIS_PROGRESS_CODE_000000 = "000000";
	public static final String REDIS_PROGRESS_CODE_111111 = "111111";
	public static final String REDIS_PROGRESS_CODE_999999 = "999999";

	/**
	 *  来源(1商户（商户注册）,2第三方,3站点注册,4合同联系人
	 */
	public static final String SOURCE_VALUE_1 = "1";
	public static final String SOURCE_VALUE_2 = "2";
	public static final String SOURCE_VALUE_3 = "3";
	public static final String SOURCE_VALUE_4 = "4";

	/**
	 * 1 超管   用于商户入驻超管的时候使用
	 * 2 注册   用于商户入驻注册类型，机构，身份的时候使用
	 * 3 客商   用于客商类型
	 * 4.商联运营  用于商联运营类型
	 * 9 默认  用于商户入驻客商类型
	 */
	public static final String SPECIAL_VALUE= "1";
	public static final String SPECIAL_VALUE_TWO = "2";
	public static final String SPECIAL_VALUE_CLIENT = "3";
	public static final String SPECIAL_VALUE_SHANG_LIAN = "4";
	public static final String SPECIAL_VALUE_DEFAULT = "9";

	/**
	 * 根机构 parent_id
	 */
	public static final Integer ROOT_MECHANISM_PARENT_ID = 0;

	/**
	 * 编号初始值密码
	 */
	public static final String DEF_P = "123456789";

	/**
	 * 密码自定义			1
	 * 密码取身份证		2
	 * 密码取手机号		3
	 */
	public static final String PASSWORD_DEFINE_FLAG="1";
	public static final String PASSWORD_IDCARD_FLAG="2";
	public static final String PASSWORD_MOBILE_FLAG="3";
	
	/**
	 * 1男 2女 3未知
	 */
	public static final String SEX_MAN = "1";
	public static final String SEX_WOMAN = "2";
	public static final String SEX_UNKNOW = "3";

	/**
	 * 0  注册用户
	 * 1  白名单用户
	 * 2  客商
	 */
	public static final String MERCHANT_APPROVE_REGISTER = "0";
	public static final String MERCHANT_APPROVE_WHITE    = "1";
	public static final String MERCHANT_APPROVE_CUSTOMERS    = "2";
	
	/**
	 * 证件类型
	 * 1 身份证
	 * 2 护照
	 * 3 签证
	 * 4 港澳通行证
	 * 5 其他
	 */
	public final  static  String CARD_TYPE_IDCARD = "1";
	
	public final  static  String CARD_TYPE_PASSPORT = "2";
	
	public final  static  String CARD_TYPE_VISA = "3";
	
	public final  static  String CARD_TYPE_HKMPASS = "4";
	
	public final  static  String CARD_TYPE_OTHERS = "5";


	/**
	 * 正则表达式：验证身份证
	 */
	public static final String REGEX_ID_CARD ="(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
			"(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";


	/**
	 * 信息项类型值编码
	 * 		007	文本
	 * 		008	数字
	 * 		009	日期
	 * 		010	文件
	 * 		011	表格
	 */
	public final  static  String INFO_ITEM_VALUE_TYPE_007 = "007";
	public final  static  String INFO_ITEM_VALUE_TYPE_008 = "008";
	public final  static  String INFO_ITEM_VALUE_TYPE_009 = "009";
	public final  static  String INFO_ITEM_VALUE_TYPE_010 = "010";
	public final  static  String INFO_ITEM_VALUE_TYPE_011 = "011";

	/**
	 * 信息项类型值编码
	 * 		1	引用
	 * 		3	创建人填写
	 * 		4	自定义
	 * 		99	预置
	 */
	public final  static  String ENTER_TYPE_1 = "1";
	public final  static  String ENTER_TYPE_3 = "3";
	public final  static  String ENTER_TYPE_4 = "4";
	public final  static  String ENTER_TYPE_99 = "99";


	/**
	 * 信息固定值
	 */
	public final static Map<String,String> MEETING_INFO_BASE_MAP = new HashMap<String, String>(){
	private static final long serialVersionUID = 1L;
	{
		put("meetingMode","会议方式(会议创建时填写)");
		put("meetingPlace","会议地点(会议创建时填写)");
		put("projectType","评审项目分类(会议创建时填写)");
		put("meetingTime","会议时间(会议创建时填写)");
		put("meetingProjectInfoIdList","评审项目(会议创建时填写)");
	}};

	/**
	 * 信息固定值 对应参数表的 typeCode
	 */
	public final static Map<String,String> MEETING_INFO_BASE_SYS_MAP = new HashMap<String, String>(){
	private static final long serialVersionUID = 1L;
	{
		put("meetingType","MEETINGTYPE");			//会议类型
		put("meetingMode","MEETINMODE");			//会议方式
		put("projectType","ITEMTYPE");		//评审项目分类
	}};


	/**
	 * 1-商户
	 * 2-运营者
	 */
	public static final String MER_C = "1";

	public static final String OPR_C = "2";

	/**
	 * 错误信息
	 */
	public static final String ERROR_MESSAGE = "errorMessage";

	public static final String RETURN_PROGRESS = "progress";

	public static final String RETURN_USE_TIME = "useTime";

	public static final String RETURN_RETURN_MESSAGE = "returnMsg";

	/**
	 * 按钮
	 */
	public final static String BUTTON_TYPE_1 = "1";
	public final static String BUTTON_TYPE_2 = "2";
	public final static String BUTTON_TYPE_3 = "3";
	public final static String BUTTON_TYPE_4 = "4";
	public final static String BUTTON_TYPE_5 = "5";
	public final static String BUTTON_TYPE_6 = "6";

	public final static Map<String, String> BUTTON_TYPE_MAP = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(BUTTON_TYPE_1, "url"); //跳转
			put(BUTTON_TYPE_2, "add"); //增加
			put(BUTTON_TYPE_3, "edit"); //修改
			put(BUTTON_TYPE_4, "delete"); //删除
			put(BUTTON_TYPE_5, "firing"); //启动
			put(BUTTON_TYPE_6, "suspend"); //暂停
		}
	};

	/**
	 * 编码集 utf-8
	 */
	public static final String UTF_8 = "utf-8";

	/**
	 * 版本号
	 */
	public static final String version = "version";
	/**
	 * 字符集
	 */
	public static final String charset = "charset";

	/**
	 * 新商户号
	 */
	public static final String merId = "merId";


	/**
	 * 新平台编号
	 */
	public static final String platNo = "platNo";

	/**
	 * 新接口编码
	 */
	public static final String intId = "intId";

	/**
	 * 报文加密串
	 */
	public static final String data = "data";

	/**
	 * 新报文加密串
	 */
	public static final String strDes = "strDes";


	/**
	 * 新签名信息（和返回值保持一致）
	 */
	public static final String signMsg = "signMsg";


	/**
	 * 新交易流水号
	 */
	public static final String tradeNo = "tradeNo";


	/**
	 * 文件分片上传---状态
	 *      * 1、从未上传文件
	 *      * 2、传了一部分，且返回已传部分的分片记录
	 *      * 3、已传的分片数量与当前分片数量一致，，且返回已传部分的分片记录
	 *      * 4、已上传过文件
	 */
	public final static String FILE_CHUNK_STATUS_1 = "1";
	public final static String FILE_CHUNK_STATUS_2 = "2";
	public final static String FILE_CHUNK_STATUS_3 = "3";
	public final static String FILE_CHUNK_STATUS_4 = "4";

	/**
	 * redis文件失效关键字
	 */
	public final static String REDIS_TEMP_CHUNK_FILE = "redis_temp_chunk_file";

	/**
	 * 微信code
	 */
	public static final String WECHAT_CODE = "WECHAT_CODE_";
	public static final String ACCESS_TOKEN = "ACCESS_TOKEN_ONLY";
}
