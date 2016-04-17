package cn.edu.shou.monitor.service.impl;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;


import com.google.gson.Gson;
import cn.edu.shou.monitor.domain.base.Host;
import cn.edu.shou.monitor.domain.base.HostGroup;
import cn.edu.shou.monitor.domain.base.HostInterface;
import cn.edu.shou.monitor.domain.base.Template;
import cn.edu.shou.monitor.domain.host.HostCreateRequest;
import cn.edu.shou.monitor.domain.host.HostDeleteRequest;
import cn.edu.shou.monitor.domain.host.HostExistsRequest;
import cn.edu.shou.monitor.domain.host.HostGetRequest;
import cn.edu.shou.monitor.domain.host.HostGetobjectsRequest;
import cn.edu.shou.monitor.domain.host.HostIsreadableRequest;
import cn.edu.shou.monitor.domain.host.HostIswritableRequest;
import cn.edu.shou.monitor.domain.host.HostMassaddRequest;
import cn.edu.shou.monitor.domain.host.HostMassremoveRequest;
import cn.edu.shou.monitor.domain.host.HostMassupdateRequest;
import cn.edu.shou.monitor.domain.host.HostUpdateRequest;
import cn.edu.shou.monitor.service.zbxapi.IHostService;
import cn.edu.shou.monitor.util.FormatData;

/**
 * @ClassName: HostServiceImpl
 * @Description: 设备接口实现
 * @author 李庆雷
 * @date 2013-9-23 下午1:15:09
 */
public class HostServiceImpl implements IHostService {
	Logger logger = Logger.getLogger(getClass());
	
	public Object create(HostCreateRequest create){       // post api
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		String content ;
		try {
			content = js.toJson(create);
			System.out.println("content is String"+content);
			putMethod.setRequestBody( FormatData.fromString(content));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println("response is String"+response);
			rs = new JSONObject(response);
			System.out.println("rs is Json"+rs);
//			if (rs.has("result")) {
//				result = rs.get("result");
//			}
//			else if (rs.has("error")) {
//				result = rs.get("error");
//			}
			//transmission
//			MQSendMessage sendTest = new MQSendMessage();
//			sendTest.sendMessages(content);
//			sendTest.sendMessages(rsString);
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
	public String createHost(Host host,HostInterface hostInterface,HostGroup hostgroup,Template template){
		String result = "";
		HostCreateRequest create = new HostCreateRequest();
		if(host!=null){
			if(host.getHost()!=null&&!"".equals(host.getHost())){
				create.getParams().setHost(host.getHost());
			}
			if(host.getAvailable()!=null){
				create.getParams().setAvailable(host.getAvailable());
			}
			if(host.getDisable_until()!=null&&!"".equals(host.getDisable_until())){
				create.getParams().setDisable_until(host.getDisable_until());
			}
			if(host.getError()!=null){
				create.getParams().setError(host.getError());
			}
			if(host.getErrors_from()!=null&&!"".equals(host.getErrors_from())){
				create.getParams().setErrors_from(host.getErrors_from());
			}
			if(host.getIpmi_authtype()!=null){
				create.getParams().setIpmi_authtype(host.getIpmi_authtype());
			}
			if(host.getIpmi_available()!=null){
				create.getParams().setIpmi_available(host.getIpmi_available());
			}
			if(host.getIpmi_disable_until()!=null&&!"".equals(host.getIpmi_disable_until())){
				create.getParams().setIpmi_disable_until(host.getIpmi_disable_until());
			}
			if(host.getIpmi_error()!=null&&!"".equals(host.getIpmi_error())){
				create.getParams().setIpmi_error(host.getIpmi_error());
			}
			if(host.getIpmi_errors_from()!=null&&!"".equals(host.getIpmi_errors_from())){
				create.getParams().setIpmi_errors_from(host.getIpmi_errors_from());
			}
			if(host.getIpmi_password()!=null&&!"".equals(host.getIpmi_password())){
				create.getParams().setIpmi_password(host.getIpmi_password());
			}
			if(host.getIpmi_privilege()!=null){
				create.getParams().setIpmi_privilege(host.getIpmi_privilege());
			}
			if(host.getIpmi_username()!=null&&!"".equals(host.getIpmi_username())){
				create.getParams().setIpmi_username(host.getIpmi_username());
			}
			if(host.getJmx_available()!=null){
				create.getParams().setJmx_available(host.getJmx_available());
			}
			if(host.getJmx_disable_until()!=null&&!"".equals(host.getJmx_disable_until())){
				create.getParams().setJmx_disable_until(host.getJmx_disable_until());
			}
			if(host.getJmx_error()!=null&&!"".equals(host.getJmx_error())){
				create.getParams().setJmx_error(host.getJmx_error());
			}
			if(host.getJmx_errors_from()!=null&&!"".equals(host.getJmx_errors_from())){
				create.getParams().setJmx_errors_from(host.getJmx_errors_from());
			}
			if(host.getMaintenance_from()!=null&&!"".equals(host.getMaintenance_from())){
				create.getParams().setMaintenance_from(host.getMaintenance_from());
			}
			if(host.getMaintenance_status()!=null){
				create.getParams().setMaintenance_status(host.getMaintenance_status());
			}
			if(host.getMaintenance_type()!=null){
				create.getParams().setMaintenance_type(host.getMaintenance_type());
			}
			if(host.getMaintenanceid()!=null&&!"".equals(host.getMaintenanceid())){
				create.getParams().setMaintenanceid(host.getMaintenanceid());
			}
			if(host.getName()!=null&&!"".equals(host.getName())){
				create.getParams().setName(host.getName());
			}
			if(host.getProxy_hostid()!=null&&!"".equals(host.getProxy_hostid())){
				create.getParams().setProxy_hostid(host.getProxy_hostid());
			}
			if(host.getSnmp_available()!=null){
				create.getParams().setSnmp_available(host.getSnmp_available());
			}
			if(host.getSnmp_disable_until()!=null&&!"".equals(host.getSnmp_disable_until())){
				create.getParams().setSnmp_disable_until(host.getSnmp_disable_until());
			}
			if(host.getSnmp_error()!=null&&!"".equals(host.getSnmp_error())){
				create.getParams().setSnmp_error(host.getSnmp_error());
			}
			if(host.getSnmp_errors_from()!=null&&!"".equals(host.getSnmp_errors_from())){
				create.getParams().setSnmp_errors_from(host.getSnmp_errors_from());
			}
			if(host.getStatus()!=null&&!"".equals(host.getStatus())){
				create.getParams().setStatus(host.getStatus());
			}
			if(hostInterface!=null&&hostInterface.getIp()!=null&&!"".equals(hostInterface.getIp())){
				HostInterface hif = new HostInterface();
				if(hostInterface.getType()!=null){
					hif.setType(hostInterface.getType());
				}
					hif.setIp(hostInterface.getIp());
				if(hostInterface.getDns()!=null){
					hif.setDns(hostInterface.getDns());
				}
				if(hostInterface.getUseip()!=null){
					hif.setUseip(hostInterface.getUseip());
				}
				if(hostInterface.getMain()!=null){
					hif.setMain(hostInterface.getMain());
				}
				if(hostInterface.getPort()!=null&&!"".equals(hostInterface.getPort())){
					hif.setPort(hostInterface.getPort());
				}
				create.getParams().getInterfaces().add(hif);
			}else{
				return "Error,HostInterface is required";
			}
			if(hostgroup!=null){
				HostGroup group = new HostGroup();
				if(hostgroup.getGroupid()!=null&&!"".equals(hostgroup.getGroupid())){
					group.setGroupid(hostgroup.getGroupid());
				}
				if(hostgroup.getInternal()!=null){
					group.setInternal(hostgroup.getInternal());
				}
				if(hostgroup.getName()!=null&&!"".equals(hostgroup.getName())){
					group.setName(hostgroup.getName());
				}
				create.getParams().getGroups().add(group);
			}else{
				return "Error,HostGroup is required";
			}
			if(template!=null){
				Template myTemplate = new Template();
				if(template.getTemplateid()!=null&&!"".equals(template.getTemplateid())){
					myTemplate.setTemplateid(template.getTemplateid());
				}
				create.getParams().getTemplates().add(myTemplate);
			}else{
				return "Error,Template is required";	
			}
			JSONObject object = (JSONObject) create(create);
			if (object.has("result")) {
				return "success"+object.get("result");   //if(createResult.equals("success")){ }
			}else {
				try {
					return "Error,"+object.get("error");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					return "Error,"+e.getMessage();
				}
			}
		}else{
			return "Error,host is required";	
		}
	}
	
	public String delete(HostDeleteRequest delete){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		String rsString = null;
		try {
			System.out.println(js.toJson(delete));
			putMethod.setRequestBody( FormatData.fromString(js.toJson(delete)));
			client.executeMethod(putMethod);
			String response = putMethod.getResponseBodyAsString();
			System.out.println(response);		
			if (response!=null && !response.equals("")) {				
				rs = new JSONObject(response);
				System.out.println(rs);				
//				if (rs.has("result")) {
//					result = rs.get("result");
//				}
//				 else if (rs.has("error")) {
//					result = rs.get("error");
//				}
			} else {
				rs = new JSONObject();
				rs.put("errorInfoByMyself", "moreItemAndNotDelte");		
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
		return rs.toString();
	}

//	public String deleteHost(Host host) {
//		String result = "";
//		HostDeleteRequest delete = new HostDeleteRequest();
//		if (host != null) {
//			if (host.getHostid() != null && !"".equals(host.getHostid())) {
//				delete.getParams().add("100");
//				JSONObject object = (JSONObject) delete(delete);
//				if (object.has("result")) {
//					return "success";
//				} else {
//					try {
//						return "Error," + object.get("error");
//					} catch (JSONException e) {
//						return "Error," + e.getMessage();
//					}
//				}
//			}
//		}else {
//			return "Error,host is required";
//		}
//		return result;
//	}

	public Object exists(HostExistsRequest exists){
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
	public Object getobjects(HostGetobjectsRequest getobjects){
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
	
	/*
	 * Title: get
	 * Description: 获得设备信息（json）
	 * @param get
	 * @return Object
	 * @see cn.edu.shou.monitor.service.zbxapi.IHostService#get(cn.edu.shou.monitor.domain.host.HostGetRequest)
	 */
	public JSONObject get(HostGetRequest get){
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
			rs = new JSONObject(response);
			//System.out.println("response是"+response);
			//System.out.println("rs是"+rs.getJSONArray("result"));
			//logger.info(response);
		}
		 catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public Object isreadable(HostIsreadableRequest isreadable){
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
	public Object iswritable(HostIswritableRequest iswritable){
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
	public Object massadd(HostMassaddRequest massadd){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			System.out.println(js.toJson(massadd));
			putMethod.setRequestBody( FormatData.fromString(js.toJson(massadd)));
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
	public Object massremove(HostMassremoveRequest massremove){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			System.out.println(js.toJson(massremove));
			putMethod.setRequestBody( FormatData.fromString(js.toJson(massremove)));
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
	public Object massupdate(HostMassupdateRequest massupdate){
		Object result = null;
		Gson js = new Gson();
		HttpClient client = new HttpClient();
		PostMethod putMethod = new PostMethod(FormatData.API_URL);
		putMethod.setRequestHeader("Content-Type", "application/json-rpc");
		JSONObject rs =null;
		try {
			System.out.println(js.toJson(massupdate));
			putMethod.setRequestBody( FormatData.fromString(js.toJson(massupdate)));
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
	public Object update(HostUpdateRequest update){
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
}
