package cn.edu.shou.monitor.util;


import org.springframework.stereotype.Service;

@Service
public class SmsSend {
	/*String httpPath = "http://58.68.247.139:9021/communication/sendSms.ashx";*/

	String httpPath = SmsMessageParams.sendPath;
	// 程序ID
	String cid = SmsMessageParams.cid;
	// 账号密码
	String pwd = SmsMessageParams.pwd;
	// 产品编号,具体账号，具体分配，建议不要写死
	String productid = SmsMessageParams.productid;
	/*// 手机号码,多个手机号码用英文逗号隔开， 最多100个
	String mobile = "15000819806";
	// 短信内容，建议不要超过500字
	String content = "短信你好！我来测试！哈哈！"+System.currentTimeMillis();*/
	// 子号,默认留空，但需保留这个参数
	String lcode = SmsMessageParams.lcode;
	// 流水号，控制在18位以内
	String ssid = SmsMessageParams.ssid;
	// 短信编码，普通短信15，长短信32，，默认填写32
	String format = SmsMessageParams.format;
	// 短信签名，默认不起作用，但需保留这个参数
	String sign = SmsMessageParams.sign;
	// 自定义字段
	String custom = SmsMessageParams.custom;

	StringBuffer params = null;
	public void sendMessage(String mobile,String content){
		params = new StringBuffer();
		try {
			params.append("cid=").append(SmsCodingUtils.encodeBase64URL(cid))
					.append("&").append("pwd=")
					.append(SmsCodingUtils.encodeBase64URL(pwd)).append("&")
					.append("productid=")
					.append(SmsCodingUtils.encodeURL(productid)).append("&")
					.append("mobile=")
					.append(SmsCodingUtils.encodeBase64URL(mobile)).append("&")
					.append("content=")
					.append(SmsCodingUtils.encodeBase64URL(content)).append("&")
					.append("lcode=").append(lcode).append("&").append("ssid=")
					.append(ssid).append("&").append("format=").append(format)
					.append("&").append("sign=")
					.append(SmsCodingUtils.encodeBase64URL(sign)).append("&")
					.append("custom=").append(SmsCodingUtils.encodeURL(custom));
			/*System.out.println(params.toString());*/
			System.out.println("mobile"+mobile);
			System.out.println("content"+content);
			String result = SmsHttpUtil.sendPostRequestByParam(httpPath,
					params.toString());
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
