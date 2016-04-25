package cn.edu.shou.monitor.tool;

import org.apache.commons.codec.binary.Base64;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class CodingUtils {

	private static final Base64 base64 = new Base64();

	public static String encodeBase64URL(String arg) throws Exception {
		String content = new String(base64.encode(URLEncoder.encode(arg,
				"UTF-8").getBytes()));
		return content;
	}

	public static String decodeBase64URL(String arg) throws Exception {
		String content = URLDecoder.decode(
				new String(base64.decode(arg.getBytes())), "UTF-8");
		return content;
	}

	public static String encodeURL(String arg) throws Exception {
		String content = URLEncoder.encode(arg, "UTF-8");
		return content;
	}

	public static String decodeURL(String arg) throws Exception {
		String content = URLDecoder.decode(arg, "UTF-8");
		return content;
	}
	

}
