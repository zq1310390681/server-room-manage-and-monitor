package cn.edu.shou.monitor.transmission;

import cn.edu.shou.monitor.service.impl.ZbxTriggerServiceImpl;
import com.alibaba.fastjson.JSON;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by light on 2016/3/16.
 */
public class MQEvent {
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
        //System.out.println("ZBX result" + resultMqStr);
        String resultMQ = resultMqStr.replace("description", "title").replace("priority", "severity")
                .replace("comments", "msg").replace("lastchange", "time")
                .replace("\"value\":\"1\"", "\"status\":\"CLOSED\"");
        //System.out.println("resultMQ " + resultMQ);
        return resultMQ;
    }


}


