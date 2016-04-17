package cn.edu.shou.monitor.service.impl;

import cn.edu.shou.monitor.domain.trigger.TriggerGetRequest;
import cn.edu.shou.monitor.service.zbxapi.ITriggerService;
import org.json.JSONObject;

/**
 * Created by light on 2016/3/15.
 */
public class ZbxTriggerServiceImpl {
    private static ITriggerService triggerService = new TriggerServiceImpl();

    static {
        // 登录
        Login.login();
    }

    public JSONObject getTrigger(String hostId){
        // 数据准备
        TriggerGetRequest get = new TriggerGetRequest();
        String[] outPuts = {"hostid", "lastchange", "priority", "value", "comments","description"};
        get.getParams().setOutput(outPuts);// shorten, refer, extend
        String[] functions = {"itemid"};
        get.getParams().setSelectFunctions(functions);
        get.getParams().getFilter().setValue("1"); // 1- problem
        get.getParams().getFilter().setHostid(hostId); //test:10112
        get.getParams().setSortfield("priority");
        get.getParams().setSortorder("DESC");

        triggerService.get(get);
        return triggerService.get(get);
    }

    public JSONObject getTriggerForSms(String hostId){
        // 数据准备
        TriggerGetRequest get = new TriggerGetRequest();
        String[] outPuts = { "priority", "description"};
        get.getParams().setOutput(outPuts);// shorten, refer, extend
//        String[] functions = {"itemid"};
//        get.getParams().setSelectFunctions(functions);
        get.getParams().getFilter().setValue("1"); // 1- problem
        get.getParams().getFilter().setHostid(hostId); //test:10112
        get.getParams().setSortfield("priority");
        get.getParams().setSortorder("DESC");

        triggerService.get(get);
        return triggerService.get(get);
    }
}
