package cn.edu.shou.monitor.tool;


import org.springframework.stereotype.Service;

@Service
public class SmsSend {
	/*String httpPath = "http://58.68.247.139:9021/communication/sendSms.ashx";*/

	String httpPath = MessageParams.sendPath;
	// 程序ID
	String cid = MessageParams.cid;
	// 账号密码
	String pwd = MessageParams.pwd;
	// 产品编号,具体账号，具体分配，建议不要写死
	String productid = MessageParams.productid;
	/*// 手机号码,多个手机号码用英文逗号隔开， 最多100个
	String mobile = "15000819806";
	// 短信内容，建议不要超过500字
	String content = "短信你好！我来测试！哈哈！"+System.currentTimeMillis();*/
	// 子号,默认留空，但需保留这个参数
	String lcode = MessageParams.lcode;
	// 流水号，控制在18位以内
	String ssid = MessageParams.ssid;
	// 短信编码，普通短信15，长短信32，，默认填写32
	String format = MessageParams.format;
	// 短信签名，默认不起作用，但需保留这个参数
	String sign = MessageParams.sign;
	// 自定义字段
	String custom = MessageParams.custom;

	StringBuffer params = null;
	public void sendMessage(String mobile,String content){
		params = new StringBuffer();
		try {
			params.append("cid=").append(CodingUtils.encodeBase64URL(cid))
					.append("&").append("pwd=")
					.append(CodingUtils.encodeBase64URL(pwd)).append("&")
					.append("productid=")
					.append(CodingUtils.encodeURL(productid)).append("&")
					.append("mobile=")
					.append(CodingUtils.encodeBase64URL(mobile)).append("&")
					.append("content=")
					.append(CodingUtils.encodeBase64URL(content)).append("&")
					.append("lcode=").append(lcode).append("&").append("ssid=")
					.append(ssid).append("&").append("format=").append(format)
					.append("&").append("sign=")
					.append(CodingUtils.encodeBase64URL(sign)).append("&")
					.append("custom=").append(CodingUtils.encodeURL(custom));
			/*System.out.println(params.toString());*/
			System.out.println("mobile"+mobile);
			System.out.println("content"+content);
			String result = HttpUtil.sendPostRequestByParam(httpPath,
					params.toString());
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
