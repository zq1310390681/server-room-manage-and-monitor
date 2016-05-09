package cn.edu.shou.monitor.service.impl;

import cn.edu.shou.monitor.domain.history.HistoryGetRequest;
import cn.edu.shou.monitor.service.zbxapi.IHistoryService;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by light on 2016/3/24.
 */

public class ZbxHistoryServiceImpl{

	public JSONObject getHistory(String itemid){
		IHistoryService historyService = new HistoryServiceImpl();
		//登录
		Login.login();

		//数据准备
		HistoryGetRequest get = new HistoryGetRequest();
		get.getParams().setOutput("extend");//refer
		get.getParams().setHistory(0);
		List<String> item =new ArrayList<String>();
		item.add(itemid);
		get.getParams().setItemids(item);
//    		get.getParams().setTime_from(1399168800l);
		List<String> sortfield = new ArrayList<String>();
		sortfield.add("clock");
		get.getParams().setSortfield(sortfield);
		List<String> sort = new ArrayList<String>();
		sort.add("DESC");
		get.getParams().setSortorder(sort);
		get.getParams().setLimit(20);
		return historyService.get(get);
	}
}
