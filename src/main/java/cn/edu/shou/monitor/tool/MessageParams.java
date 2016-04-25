package cn.edu.shou.monitor.tool;

public class MessageParams {

	public static String IP="http://58.68.247.137:9053";
	public static String sendPath = IP+"/communication/sendSms.ashx";
	public static String receicePath = IP+"/communication/fetchDelivers.ashx";
	public static StringBuffer params = null;
	// 程序ID
	public static String cid = "8273";
	// 账号密码
	public static String pwd = "123qwe";
	// 产品编号,具体账号，具体分配，建议不要写死
	public static String productid = "20774301";
	// 子号,默认留空，但需保留这个参数
	public static String lcode = "";
	// 流水号，控制在18位以内
	public static String ssid = String.valueOf(System.currentTimeMillis());
	// 短信编码，普通短信15，长短信32，，默认填写32
	public static String format = "32";
	// 短信签名，默认不起作用，但需保留这个参数
	public static String sign = "";
	// 自定义字段
	public static String custom = "";
	// 一次获取的上行个数，建议100个，最大500
	public static int cnt = 100;
}
