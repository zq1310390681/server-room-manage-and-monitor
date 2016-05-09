package cn.edu.shou.monitor.service.impl;

import cn.edu.shou.monitor.util.FormatData;
import org.springframework.beans.factory.annotation.Value;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PutMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.HashMap;
import java.util.Map;


public class Login {
//	public static final String url = "http://192.168.8.101/zabbix/api_jsonrpc.php";
	@Value("${spring.main.url_192}")
	static String url_192;
	@Value("${spring.main.url_128}")
	static String url_128;
	@Value("${spring.main.url_1080}")
	static String url_1080;
	@Value("${spring.main.url_test}")
	static String url_test;
	@Value("${spring.main.ZBX_loginName}")
	static String loginName;
	@Value("${spring.main.ZBX_password}")
	static String password;

	static{
		FormatData.API_URL = url_192;
		}
	/**
	 * 登录json
	 * 
	 * @return
	 */
	private static String loginJson(String loginName, String password) {
		JSONStringer js = new JSONStringer();
		try {
			js.object();
			js.key("jsonrpc").value("2.0");
			js.key("method").value("user.login");
			js.key("id").value(1);
			// js.key("auth").value(FormatData.auth);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("user", loginName);
			jsonObject.put("password", password);
			js.key("params").value(jsonObject);

			js.endObject();
		} catch (JSONException e) {
			return null;
		}
		return js.toString();
	}

	public static HttpClient login() {
		try {
			HttpClient client = new HttpClient();
			PutMethod putMethod = new PutMethod(url_192);
			putMethod.setRequestHeader("Content-Type", "application/json-rpc");
			String jsonrpc = loginJson(loginName, password);
			JSONObject jsonObj = new JSONObject(jsonrpc);
			
			putMethod.setRequestBody(FormatData.fromString(jsonObj.toString()));
			String loginResponse = "";
			client.executeMethod(putMethod);
			loginResponse = putMethod.getResponseBodyAsString();
			JSONObject obj = new JSONObject(loginResponse);
			String sessionId = "";

			if (obj.has("result")) {
				sessionId = obj.getString("result");
				
				FormatData.auth = sessionId; //
				// init();

				// IUserGroupService ius = new UserGroupServiceImpl();
				// ius.getAllUserGroup(); 
				// IHostGroupService ihs = new HostGroupServiceImpl();
				// ihs.getAllHostGroup(); // 
				System.out.println(loginResponse);
				System.out.println(sessionId);
				return client;
			} else if (obj.has("error")) {
				sessionId = obj.getJSONObject("error").getString("data");
				throw new Exception(sessionId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//三网同步部署用此方法 正式上线
	public static HttpClient login(String IP) {
		Map<String,String> mapUrl = new HashMap<>();
		mapUrl.put("192.168.8",url_192);
		mapUrl.put("128.1.1",url_128);
		mapUrl.put("10.80.0",url_1080);
//		mapUrl.put("192.168.8",url_test); //url_test 需要直接设置

		String IPSub = IP.substring(0,IP.lastIndexOf("."));
		String url = mapUrl.get(IPSub);
		try {
			HttpClient client = new HttpClient();
			PutMethod putMethod = new PutMethod(url);
			putMethod.setRequestHeader("Content-Type", "application/json-rpc");
			String jsonrpc = loginJson(loginName, password);
			JSONObject jsonObj = new JSONObject(jsonrpc);

			putMethod.setRequestBody(FormatData.fromString(jsonObj.toString()));
			String loginResponse = "";
			client.executeMethod(putMethod);
			loginResponse = putMethod.getResponseBodyAsString();
			JSONObject obj = new JSONObject(loginResponse);
			String sessionId = "";

			if (obj.has("result")) {
				sessionId = obj.getString("result");

				FormatData.auth = sessionId; //
				// init();

				// IUserGroupService ius = new UserGroupServiceImpl();
				// ius.getAllUserGroup();
				// IHostGroupService ihs = new HostGroupServiceImpl();
				// ihs.getAllHostGroup(); //
				System.out.println(loginResponse);
				System.out.println(sessionId);
				return client;
			} else if (obj.has("error")) {
				sessionId = obj.getJSONObject("error").getString("data");
				throw new Exception(sessionId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
