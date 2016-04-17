package cn.edu.shou.monitor.transmission;

import cn.edu.shou.monitor.service.impl.ZbxHostServiceImpl;
import cn.edu.shou.monitor.service.impl.ZbxTriggerServiceImpl;
import cn.edu.shou.monitor.spring.TargetDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by light on 2016/4/14.
 */
@RestController
public class PhoneMessage {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @TargetDataSource(name = "predict")
    public String sendMessage(String hostid){  // result 中Array size 为2

//        ZbxHostServiceImpl zbxHost = new ZbxHostServiceImpl();
//        JSONObject host = zbxHost.getHostIdAndName(hostid);
//        JSONArray hostResult = host.getJSONArray("result");
//        String mame = hostResult.getJSONObject(0).getString("name");

        ZbxTriggerServiceImpl trigger = new ZbxTriggerServiceImpl();
        JSONObject triggerRs = trigger.getTriggerForSms(hostid);
        JSONArray result = triggerRs.getJSONArray("result");  //结果信息
        String content = null;
        if(!result.toString().equals("[]")){
            String selectSql = "SELECT hosts FROM predict.zbx_hosts where host_id = '"+ hostid+"';";
            List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
            list = jdbcTemplate.queryForList(selectSql);
            String name = list.get(0).get("hosts").toString();
            content = "机房主机出现报警，请尽快处理。详细信息为：{主机编号：" + name + ",报警内容: "+result.toString();
        }
        return content;
    }

    public  String testSendSms(String hostid){
        String selectSql = "SELECT hosts FROM predict.zbx_hosts where host_id = '"+hostid+"';";
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        if(jdbcTemplate==null){
            jdbcTemplate=new JdbcTemplate();
        }
        list = jdbcTemplate.queryForList(selectSql);

        String name = list.get(0).get("hosts").toString();

        return name;
    }
}
