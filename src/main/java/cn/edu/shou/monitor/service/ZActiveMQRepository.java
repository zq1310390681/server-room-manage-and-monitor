package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.service.impl.ZbxItemServiceImpl;
import cn.edu.shou.monitor.service.impl.ZbxTriggerServiceImpl;
import cn.edu.shou.monitor.util.ActiveMQ;
import com.alibaba.fastjson.JSON;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by light on 2016/5/6.
 */
@Repository
public class ZActiveMQRepository {
    public String addAndUpdAndDelAssetForServer(String operation,long id, String hostName,String hostId,String serverSN,String purchasingDate,
                                     String maintenanceDueTime, String serverBrand,String serverType,String IP,
                                     String storageDevice,String equipmentCabinet,String serverU,String serverRemark,String kvm){
        JSONObject asset = new JSONObject();
        JSONObject data = new JSONObject();
        asset.put("_operation_", operation);
        asset.put("_sc_", "国家海洋局");
        asset.put("_pool_", "架式设备");
        asset.put("_id_", hostId);

        //data.put("布局","");
        data.put("编号",hostId);
        data.put("编号名称",hostName);
        data.put("管理id",id);
        data.put("服务器SN号",serverSN);
        data.put("购买时间",purchasingDate);
        data.put("维保到期时间",maintenanceDueTime);
        data.put("服务器品牌",serverBrand);
        data.put("设备型号",serverType);
        data.put("IP地址",IP);
        data.put("存储设备",storageDevice);
        data.put("所属",equipmentCabinet);  //机柜
        data.put("所在U位",serverU);
        data.put("备注信息",serverRemark);
        data.put("KVM",kvm);

        asset.put("_data_",data);
        return "["+asset.toString()+"]";
    }

    public String addAndUpdAndDelAssetForSwitchBoard(String operation,long id, String hostName,String hostId,String serverSN,String purchasingDate,
                                     String maintenanceDueTime, String serverBrand,String serverType,String IP,String equipmentCabinet,String serverU,String serverRemark){
        JSONObject asset = new JSONObject();
        JSONObject data = new JSONObject();
        asset.put("_operation_", operation);
        asset.put("_sc_", "国家海洋局");
        asset.put("_pool_", "架式设备");
        asset.put("_id_", hostId);

        //data.put("布局","");
        data.put("编号",hostId);
        data.put("编号名称",hostName);
        data.put("管理id",id);
        data.put("交换机SN号",serverSN);
        data.put("购买时间",purchasingDate);
        data.put("维保到期时间",maintenanceDueTime);
        data.put("交换机品牌",serverBrand);
        data.put("设备型号",serverType);
        data.put("IP地址",IP);
        data.put("所属",equipmentCabinet);  //机柜
        data.put("所在U位",serverU);
        data.put("备注信息",serverRemark);

        asset.put("_data_",data);
        return "["+asset.toString()+"]";
    }

    public void updApp(String operation,List<String> hostId,String appName){
        JSONObject asset = new JSONObject();
        JSONObject data = new JSONObject();
        for(String str: hostId){
            asset.put("_operation_", operation);
            asset.put("_sc_", "国家海洋局");
            asset.put("_pool_", "架式设备");
            asset.put("_id_", str);

            data.put("编号",str);
            data.put("搭载应用服务",appName);

            asset.put("_data_",data);
            String assets = "["+asset.toString()+"]";
            ActiveMQ.sendMessages(assets, "asset");
        }
    }

    public void updApp(String operation,String hostId,String appName){
        JSONObject asset = new JSONObject();
        JSONObject data = new JSONObject();
        asset.put("_operation_", operation);
        asset.put("_sc_", "国家海洋局");
        asset.put("_pool_", "架式设备");
        asset.put("_id_", hostId);

        data.put("编号",hostId);
        data.put("搭载应用服务",appName);

        asset.put("_data_",data);
        String assets = "["+asset.toString()+"]";
        ActiveMQ.sendMessages(assets,"asset");
    }

    public void updApp(String operation,long id, String hostName,String hostId,String serverSN,String purchasingDate,
                       String maintenanceDueTime, String serverBrand,String serverType,String IP,
                       String storageDevice,String equipmentCabinet,String serverU,String appName,String serverRemark){
        JSONObject asset = new JSONObject();
        JSONObject data = new JSONObject();
        asset.put("_operation_", operation);
        asset.put("_sc_", "国家海洋局");
        asset.put("_pool_", "架式设备");
        asset.put("_id_", hostId);

        data.put("编号",hostId);
        data.put("编号名称",hostName);
        data.put("管理id",id);
        data.put("服务器SN号",serverSN);
        data.put("提供应用服务",appName);
        data.put("购买时间",purchasingDate);
        data.put("维保到期时间",maintenanceDueTime);
        data.put("服务器品牌",serverBrand);
        data.put("设备型号",serverType);
        data.put("IP地址",IP);
        data.put("存储设备",storageDevice);
        data.put("所属",equipmentCabinet);  //机柜
        data.put("所在U位",serverU);
        data.put("备注信息",serverRemark);

        asset.put("_data_",data);
        String assets = "["+asset.toString()+"]";
        ActiveMQ.sendMessages(assets, "asset");
    }

    /*********************************************************************************************/

    public String showEvent(String hostIds) {
        // 此方法不便于显示对报警的操作
        JSONObject eventForepart = new JSONObject();
        eventForepart.put("scene", "国家海洋局");
        eventForepart.put("id", hostIds);
        eventForepart.put("key","");
        eventForepart.put("arg1", "_");
        eventForepart.put("arg2", "_");
        eventForepart.put("modifyTime",System.currentTimeMillis());

        ZbxTriggerServiceImpl trigger = new ZbxTriggerServiceImpl();
        JSONObject triggerRs = trigger.getTrigger(hostIds);
        JSONArray result = triggerRs.getJSONArray("result");

        JSONArray resultMqDraft = new JSONArray();
        for (int i = 0; i < result.length(); i++) {
            String rs = eventForepart.toString() + "," + result.get(i).toString();
            String rs1 = rs.replace("{", "").replace("[", "").replace("]", "").replace("}", "")
                    .replace("\"functions\":", "");
            String rs2 = "{" + rs1 + "}";
            com.alibaba.fastjson.JSONObject rs3 = JSON.parseObject(rs2);
            resultMqDraft.put(i, rs3);
        }
        String resultMqStr = resultMqDraft.toString();
        String resultMQ = resultMqStr.replace("description", "title").replace("priority", "severity")
                .replace("comments", "msg").replace("lastchange", "time")
                .replace("\"value\":\"1\"", "\"status\":\"CLOSED\"");
        return resultMQ;
    }

    /**************************************************************************************************/

    public String performItem(String searchParam) {
        JSONObject perfForepart = new JSONObject();
        ZbxItemServiceImpl itemGet = new ZbxItemServiceImpl();

        JSONObject itemRs = itemGet.getItem(searchParam);
        JSONArray result = itemRs.getJSONArray("result");

        perfForepart.put("scene", "国家海洋局");
        perfForepart.put("app", searchParam);
        perfForepart.put("key", "");
        perfForepart.put("inst", "_");

        JSONArray resultMqDraft = new JSONArray();
        for (int i = 0; i < result.length(); i++) {
            //System.out.println("输出第"+i+"个"+result.get(i));
            String rs = perfForepart.toString() + "," + result.get(i).toString();
            String rs1 = rs.replace("{", "").replace("}", "");
            String rs2 = "{" + rs1 + "}";
            com.alibaba.fastjson.JSONObject ss = JSON.parseObject(rs2);
            resultMqDraft.put(i, ss);
        }
        String resultMqStr = resultMqDraft.toString();
        //System.out.println("ZBX result "+resultMqStr);
        String resultMQ = resultMqStr.replace("name", "param").replace("hostid", "id")
                .replace("units", "unit").replace("lastvalue", "val").replace("lastclock", "time")
                .replace("\"value_type\":\"0\"", "\"type\":\"数值\"").replace("\"value_type\":\"3\"", "\"type\":\"数值\"");
        //System.out.println("resultMQ "+resultMQ);
        return resultMQ;
    }

    public String performItem(String hostId, String searchParam) {
        JSONObject perfForepart = new JSONObject();
        ZbxItemServiceImpl itemGet = new ZbxItemServiceImpl();

        JSONObject itemRs = itemGet.getItem(hostId, searchParam);
        JSONArray result = itemRs.getJSONArray("result");

        perfForepart.put("scene", "国家海洋局");
        perfForepart.put("app", searchParam);
        perfForepart.put("key", "");
        perfForepart.put("inst", "_");

        JSONArray resultMqDraft = new JSONArray();
        for (int i = 0; i < result.length(); i++) {
            //System.out.println("输出第"+i+"个"+result.get(i));
            String rs = perfForepart.toString() + "," + result.get(i).toString();
            String rs1 = rs.replace("{", "").replace("}", "");
            String rs2 = "{" + rs1 + "}";
            com.alibaba.fastjson.JSONObject ss = JSON.parseObject(rs2);
            resultMqDraft.put(i, ss);
        }
        String resultMqStr = resultMqDraft.toString();
        String resultMQ = resultMqStr.replace("name", "param").replace("hostid", "id")
                .replace("units", "unit").replace("lastvalue", "val").replace("lastclock", "time")
                .replace("\"value_type\":\"0\"", "\"type\":\"数值\"").replace("\"value_type\":\"3\"", "\"type\":\"数值\"");
        return resultMQ;
    }
}
