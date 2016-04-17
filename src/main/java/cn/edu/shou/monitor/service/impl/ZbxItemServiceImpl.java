package cn.edu.shou.monitor.service.impl;

import cn.edu.shou.monitor.domain.item.ItemGetRequest;
import cn.edu.shou.monitor.service.zbxapi.IItemService;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * Created by light on 2016/3/1.
 */
public class ZbxItemServiceImpl {
    private static IItemService itemService = new ItemServiceImpl();

    static {
        // 登录
       Login.login();
    }


    public JSONObject getItem(String searchParam) {
        // 数据准备
        ItemGetRequest get = new ItemGetRequest();
        String[] outPuts = {"hostid","itemid","lastvalue","lastclock","name","key_","units","value_type"};
        get.getParams().setOutput(outPuts);
//        String[] hostids = {getHostIdArray()};
//        get.getParams().setHostids(hostids);
        get.getParams().getSearch().setKey_(searchParam);
        //System.out.println("返回数据"+itemService.get(get));
        return itemService.get(get);
    }

    public JSONObject getItem(String hostId ,String searchParam) {
        // 数据准备
        ItemGetRequest get = new ItemGetRequest();
        String[] outPuts = {"hostid","itemid","lastvalue","lastclock","name","key_","units","value_type"};
        get.getParams().setOutput(outPuts);
        String[] hostIds = {hostId};
        get.getParams().setHostids(hostIds);
        get.getParams().getSearch().setKey_(searchParam);
        //System.out.println("返回数据"+itemService.get(get));
        return itemService.get(get);
    }

    public JSONObject getSimpleItem(String hostId ,String searchParam,String searchName) {
        // 数据准备
        ItemGetRequest get = new ItemGetRequest();
        String[] outPuts = {"lastvalue"};
        get.getParams().setOutput(outPuts);
        String[] hostIds = {hostId};
        get.getParams().setHostids(hostIds);
        get.getParams().getSearch().setKey_(searchParam);
        get.getParams().getSearch().setName(searchName);
        //System.out.println("返回数据"+itemService.get(get));
        return itemService.get(get);
    }
}
