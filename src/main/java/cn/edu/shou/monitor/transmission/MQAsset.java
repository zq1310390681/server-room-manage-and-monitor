package cn.edu.shou.monitor.transmission;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by light on 2016/3/10.
 */
public class MQAsset {
    JSONObject asset = new JSONObject();
    JSONObject data = new JSONObject();

    public String addAndUpdAndDelAsset(String operation,long id, String hostName,String hostId,String serverSN,String purchasingDate,
                                 String maintenanceDueTime, String serverBrand,String serverType,String IP,
                                 String storageDevice,String equipmentCabinet,String serverU,String serverRemark){

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

        asset.put("_data_",data);
        return "["+asset.toString()+"]";
    }

    public void updApp(String operation,List<String> hostId,String appName){
        for(String str: hostId){
            asset.put("_operation_", operation);
            asset.put("_sc_", "国家海洋局");
            asset.put("_pool_", "架式设备");
            asset.put("_id_", str);

            data.put("编号",str);
            data.put("搭载应用服务",appName);

            asset.put("_data_",data);
            String assets = "["+asset.toString()+"]";
            MQSendMessage.sendMessages(assets,"asset");
        }
    }

    public void updApp(String operation,String hostId,String appName){
        asset.put("_operation_", operation);
        asset.put("_sc_", "国家海洋局");
        asset.put("_pool_", "架式设备");
        asset.put("_id_", hostId);

        data.put("编号",hostId);
        data.put("搭载应用服务",appName);

        asset.put("_data_",data);
        String assets = "["+asset.toString()+"]";
        MQSendMessage.sendMessages(assets,"asset");
    }

    public void updApp(String operation,long id, String hostName,String hostId,String serverSN,String purchasingDate,
                       String maintenanceDueTime, String serverBrand,String serverType,String IP,
                       String storageDevice,String equipmentCabinet,String serverU,String appName,String serverRemark){
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
        MQSendMessage.sendMessages(assets,"asset");
    }
}

