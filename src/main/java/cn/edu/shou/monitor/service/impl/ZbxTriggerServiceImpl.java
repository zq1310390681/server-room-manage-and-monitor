package cn.edu.shou.monitor.service.impl;

import cn.edu.shou.monitor.domain.trigger.TriggerGetRequest;
import cn.edu.shou.monitor.service.zbxapi.ITriggerService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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

    //报警规则设置，从数据库直接该；两种情况，1)已经创建过的要批量该，2）为创建过的只需改template对应的trigger。
    //两种情况合并，改一次两个都改
    /**
     正向
     items 表的itemid 关联functions表的itemid ，得到了对应的triggerid，
     在triggers表的expression里面修改阈值。

     items.hostid = templateid
     items.itemid = functions.itemid
     functions.triggerid = triggers.triggerid
     triggers.expression

     逆向
     从triggers 表的description 找到监控的像 比如load
     triggers表的triggerid 和functions表的functonid 对应
     functions.itemid 对应于 items.itemid
     items.itemid对应hostid
     */

    public void updFunction(){


    }
}
