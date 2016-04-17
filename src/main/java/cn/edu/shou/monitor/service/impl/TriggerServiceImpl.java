package cn.edu.shou.monitor.service.impl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import cn.edu.shou.monitor.domain.base.Trigger;
import cn.edu.shou.monitor.domain.trigger.TriggerAdddependenciesRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerCreateRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerCreateRequest.TriggerCreateParams;
import cn.edu.shou.monitor.domain.trigger.TriggerDeleteRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerDeletedependenciesRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerExistsRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerGetRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerGetobjectsRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerIsreadableRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerIswritableRequest;
import cn.edu.shou.monitor.domain.trigger.TriggerUpdateRequest;
import cn.edu.shou.monitor.service.zbxapi.ITriggerService;
import cn.edu.shou.monitor.util.FormatData;
public class TriggerServiceImpl implements ITriggerService {
	public Object adddependencies(TriggerAdddependenciesRequest adddependencies){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			System.out.println(js.toJson(adddependencies));
			putMethod.setRequestBody( FormatData.fromString(js.toJson(adddependencies)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			 rs = new JSONObject(response);
			System.out.println(rs);
			if (rs.has("result")) {
				result = rs.get("result");
			}
			 else if (rs.has("error")) {
				result = rs.get("error");
			}
		}
		 catch (HttpException e) {
			e.printStackTrace();
		}
		 catch (IOException e) {
			e.printStackTrace();
		}
		 catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	public Object create(TriggerCreateRequest create){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			System.out.println(js.toJson(create));
			putMethod.setRequestBody( FormatData.fromString(js.toJson(create)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			rs = new JSONObject(response);
			System.out.println(rs);
//			if (rs.has("result")) {
//				result = rs.get("result");
//			}
//			 else if (rs.has("error")) {
//				result = rs.get("error");
//			}
		}
		 catch (HttpException e) {
			e.printStackTrace();
		}
		 catch (IOException e) {
			e.printStackTrace();
		}
		 catch (JSONException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public Object addTrigger(Trigger trigger){
		Object object = null;
		String desc = trigger.getDescription();
		String expr = trigger.getExpression();
		int prior = trigger.getPriority();
		int status = trigger.getStatus();
		if(desc!=null&&!"".equals(desc)&&expr!=null&&!"".equals(expr)){
			TriggerCreateRequest createRequest = new TriggerCreateRequest();
			TriggerCreateParams params = createRequest.getParams();
			params.setDescription(desc);
			params.setExpression(expr);
			params.setStatus(status);
			params.setPriority(prior);
			object = this.create(createRequest);
		}
		return object;
	}
	
	public Object deletedependencies(TriggerDeletedependenciesRequest deletedependencies){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			System.out.println(js.toJson(deletedependencies));
			putMethod.setRequestBody( FormatData.fromString(js.toJson(deletedependencies)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			 rs = new JSONObject(response);
			System.out.println(rs);
			if (rs.has("result")) {
				result = rs.get("result");
			}
			 else if (rs.has("error")) {
				result = rs.get("error");
			}
		}
		 catch (HttpException e) {
			e.printStackTrace();
		}
		 catch (IOException e) {
			e.printStackTrace();
		}
		 catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	public Object delete(TriggerDeleteRequest delete){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			System.out.println(js.toJson(delete));
			putMethod.setRequestBody( FormatData.fromString(js.toJson(delete)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			 rs = new JSONObject(response);
			System.out.println(rs);
//			if (rs.has("result")) {
//				result = rs.get("result");
//			}
//			 else if (rs.has("error")) {
//				result = rs.get("error");
//			}
		}
		 catch (HttpException e) {
			e.printStackTrace();
		}
		 catch (IOException e) {
			e.printStackTrace();
		}
		 catch (JSONException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public Object exists(TriggerExistsRequest exists){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			System.out.println(js.toJson(exists));
			putMethod.setRequestBody( FormatData.fromString(js.toJson(exists)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			 rs = new JSONObject(response);
			System.out.println(rs);
			if (rs.has("result")) {
				result = rs.get("result");
			}
			 else if (rs.has("error")) {
				result = rs.get("error");
			}
		}
		 catch (HttpException e) {
			e.printStackTrace();
		}
		 catch (IOException e) {
			e.printStackTrace();
		}
		 catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	public Object getobjects(TriggerGetobjectsRequest getobjects){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			System.out.println(js.toJson(getobjects));
			putMethod.setRequestBody( FormatData.fromString(js.toJson(getobjects)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			 rs = new JSONObject(response);
			System.out.println(rs);
			if (rs.has("result")) {
				result = rs.get("result");
			}
			 else if (rs.has("error")) {
				result = rs.get("error");
			}
		}
		 catch (HttpException e) {
			e.printStackTrace();
		}
		 catch (IOException e) {
			e.printStackTrace();
		}
		 catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject get(TriggerGetRequest get){
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			//System.out.println(js.toJson(get));
			putMethod.setRequestBody( FormatData.fromString(js.toJson(get)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			//System.out.println(response);
			rs = new JSONObject(response);
//			System.out.println(rs);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public Object isreadable(TriggerIsreadableRequest isreadable){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			System.out.println(js.toJson(isreadable));
			putMethod.setRequestBody( FormatData.fromString(js.toJson(isreadable)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			 rs = new JSONObject(response);
			System.out.println(rs);
			if (rs.has("result")) {
				result = rs.get("result");
			}
			 else if (rs.has("error")) {
				result = rs.get("error");
			}
		}
		 catch (HttpException e) {
			e.printStackTrace();
		}
		 catch (IOException e) {
			e.printStackTrace();
		}
		 catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	public Object iswritable(TriggerIswritableRequest iswritable){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			System.out.println(js.toJson(iswritable));
			putMethod.setRequestBody( FormatData.fromString(js.toJson(iswritable)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			 rs = new JSONObject(response);
			System.out.println(rs);
			if (rs.has("result")) {
				result = rs.get("result");
			}
			 else if (rs.has("error")) {
				result = rs.get("error");
			}
		}
		 catch (HttpException e) {
			e.printStackTrace();
		}
		 catch (IOException e) {
			e.printStackTrace();
		}
		 catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	public Object update(TriggerUpdateRequest update){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			System.out.println(js.toJson(update));
			putMethod.setRequestBody( FormatData.fromString(js.toJson(update)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);
			 rs = new JSONObject(response);
			System.out.println(rs);
//			if (rs.has("result")) {
//				result = rs.get("result");
//			}
//			 else if (rs.has("error")) {
//				result = rs.get("error");
//			}
		}
		 catch (HttpException e) {
			e.printStackTrace();
		}
		 catch (IOException e) {
			e.printStackTrace();
		}
		 catch (JSONException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	@Override
	public List<Trigger> getTriggerBean(TriggerGetRequest get) {
		JSONObject result = (JSONObject) this.get(get);
		List<Trigger> triggers = null;
		if (result.has("result")) {
			try {
				JSONArray array = result.getJSONArray("result");
				if(array!=null&&array.length()>0){
					triggers = new ArrayList<Trigger>();
					for(int i=0;i<array.length();i++){
						JSONObject triggerjson = array.getJSONObject(i);
						Trigger trigger =new Trigger();
						trigger.setComments(triggerjson.getString("comments"));
						trigger.setDescription(triggerjson.getString("description"));
						trigger.setError(triggerjson.getString("error"));
						trigger.setExpression(triggerjson.getString("expression"));
						trigger.setFlags(triggerjson.getInt("flags"));
						trigger.setLastchange(triggerjson.getString("lastchange"));
						trigger.setPriority(triggerjson.getInt("priority"));
						trigger.setStatus(triggerjson.getInt("status"));
						trigger.setTemplateid(triggerjson.getString("templateid"));
						trigger.setTriggerid(triggerjson.getString("triggerid"));
						trigger.setType(triggerjson.getInt("type"));
						trigger.setUrl(triggerjson.getString("url"));
						trigger.setValue(triggerjson.getInt("value"));
						trigger.setValue_flags(triggerjson.getInt("value_flag"));
						triggers.add(trigger);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return triggers;
			}
		}else if (result.has("error")) {
			return triggers;
		 }
		return triggers;
	}
}
