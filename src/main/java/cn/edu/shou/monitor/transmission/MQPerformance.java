package cn.edu.shou.monitor.transmission;

import cn.edu.shou.monitor.service.impl.ZbxItemServiceImpl;
import com.alibaba.fastjson.JSON;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by light on 2016/3/14.
 */
@Component
@RestController
@RequestMapping(value = "/predictCenter/item/perf")
public class MQPerformance {
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



