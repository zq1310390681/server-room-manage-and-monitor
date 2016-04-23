package cn.edu.shou.monitor.transmission;

import cn.edu.shou.monitor.service.impl.ZbxHostServiceImpl;
import cn.edu.shou.monitor.service.impl.ZbxTriggerServiceImpl;
import cn.edu.shou.monitor.spring.TargetDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by light on 2016/4/14.
 */
@Repository
public class PhoneMessage {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @TargetDataSource(name = "predict")
    public String sendMessage(String hostid){  // result 中Array size 为2

        ZbxTriggerServiceImpl trigger = new ZbxTriggerServiceImpl();
        JSONObject triggerRs = trigger.getTriggerForSms(hostid);
        JSONArray result = triggerRs.getJSONArray("result");  //结果信息
        String description = result.getJSONObject(0).getString("description");
        String priority = result.getJSONObject(0).getString("priority");
        String content = null;
        if(!result.toString().equals("[]")){
            String selectSqlName = "SELECT hosts FROM predict.zbx_hosts where host_id = '"+ hostid+"';";
            String selectSqlDesc = "SELECT desc_zh FROM predict.alarm_description where desc_en = '"+ description+"';";

            List<Map<String,Object>> listName = new ArrayList<Map<String,Object>>();
            listName = jdbcTemplate.queryForList(selectSqlName);
            String name = listName.get(0).get("hosts").toString();

            List<Map<String,Object>> listdesc = new ArrayList<Map<String,Object>>();
            listdesc = jdbcTemplate.queryForList(selectSqlDesc);
            String desc_zh = listdesc.get(0).get("desc_zh").toString();
            content = "机房主机出现报警，请尽快处理。详细信息为：主机编号：" + name + ",报警内容: "+ desc_zh + ",警报等级"+ priority+"。";
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
