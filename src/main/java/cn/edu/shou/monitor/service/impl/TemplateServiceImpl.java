package cn.edu.shou.monitor.service.impl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import cn.edu.shou.monitor.domain.base.Template;
import cn.edu.shou.monitor.domain.template.TemplateCreateRequest;
import cn.edu.shou.monitor.domain.template.TemplateDeleteRequest;
import cn.edu.shou.monitor.domain.template.TemplateExistsRequest;
import cn.edu.shou.monitor.domain.template.TemplateGetRequest;
import cn.edu.shou.monitor.domain.template.TemplateGetobjectsRequest;
import cn.edu.shou.monitor.domain.template.TemplateIsreadableRequest;
import cn.edu.shou.monitor.domain.template.TemplateIswritableRequest;
import cn.edu.shou.monitor.domain.template.TemplateMassaddRequest;
import cn.edu.shou.monitor.domain.template.TemplateMassremoveRequest;
import cn.edu.shou.monitor.domain.template.TemplateMassupdateRequest;
import cn.edu.shou.monitor.domain.template.TemplateUpdateRequest;
import cn.edu.shou.monitor.service.zbxapi.ITemplateService;
import cn.edu.shou.monitor.util.FormatData;
public class TemplateServiceImpl implements ITemplateService {
	private static Logger log = Logger.getLogger("allmoniapi");
	public Object create(TemplateCreateRequest create){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			String json = js.toJson(create);
			log.info("json request:"+json);
			putMethod.setRequestBody( FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			log.info("json response:"+response);
			 rs = new JSONObject(response);
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
	public Object delete(TemplateDeleteRequest delete){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			String json = js.toJson(delete);
			log.info("json request:"+json);
			putMethod.setRequestBody( FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			log.info("json response:"+response);
			 rs = new JSONObject(response);
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
	public Object exists(TemplateExistsRequest exists){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			String json = js.toJson(exists);
			log.info("json request:"+json);
			putMethod.setRequestBody( FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			log.info("json response:"+response);
			 rs = new JSONObject(response);
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
	public Object getobjects(TemplateGetobjectsRequest getobjects){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			String json = js.toJson(getobjects);
			log.info("json request:"+json);
			putMethod.setRequestBody( FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			log.info("json response:"+response);
			 rs = new JSONObject(response);
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
	public Object get(TemplateGetRequest get){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			String json = js.toJson(get);
			log.info("json request:"+json);
			putMethod.setRequestBody( FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			log.info("json response:"+response);
			System.out.println("json response:"+response);
			 rs = new JSONObject(response);
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
	public List<Template> getTemplateToBean(TemplateGetRequest get){
		List<Template> templates = null;
		JSONObject result= (JSONObject) this.get(get);
		if (result.has("result")) {
			JSONArray array;
			try {
				array = result.getJSONArray("result");
				if(array!=null&&array.length()>0){
					templates = new ArrayList<Template>();
					for(int i = 0; i<array.length();i++){
						JSONObject jsonObject = array.getJSONObject(i);
						Template template = new Template();
						template.setTemplateid(jsonObject.getString("templateid"));
						template.setHost(jsonObject.getString("host"));
						template.setName(jsonObject.getString("name"));
						templates.add(template);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}else if (result.has("error")) {
			return null;
		}
		return templates;
	}
	
	public Object isreadable(TemplateIsreadableRequest isreadable){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			String json = js.toJson(isreadable);
			log.info("json request:"+json);
			putMethod.setRequestBody( FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			log.info("json response:"+response);
			 rs = new JSONObject(response);
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
	public Object iswritable(TemplateIswritableRequest iswritable){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			String json = js.toJson(iswritable);
			log.info("json request:"+json);
			putMethod.setRequestBody( FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			log.info("json response:"+response);
			 rs = new JSONObject(response);
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
	public Object massadd(TemplateMassaddRequest massadd){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			String json = js.toJson(massadd);
			log.info("json request:"+json);
			putMethod.setRequestBody( FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			log.info("json response:"+response);
			 rs = new JSONObject(response);
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
	public Object massremove(TemplateMassremoveRequest massremove){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			String json = js.toJson(massremove);
			log.info("json request:"+json);
			putMethod.setRequestBody( FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			log.info("json response:"+response);
			 rs = new JSONObject(response);
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
	public Object massupdate(TemplateMassupdateRequest massupdate){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			String json = js.toJson(massupdate);
			log.info("json request:"+json);
			putMethod.setRequestBody( FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			log.info("json response:"+response);
			 rs = new JSONObject(response);
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
	public Object update(TemplateUpdateRequest update){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			String json = js.toJson(update);
			log.info("json request:"+json);
			putMethod.setRequestBody( FormatData.fromString(json));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			log.info("json response:"+response);
			 rs = new JSONObject(response);
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
}
