package cn.edu.shou.monitor.web.api;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by light on 2016/4/7.
 */
@RestController
@RequestMapping(value ="/predictCenter/matrix")
public class UserRelationApiController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @RequestMapping(value ="/test")
    public String getMatrix(){
        String selectApp = "select id,application_name as name,app_group as 'group'\n" +
                "from predict_mm_applications;";
        List<Map<String,Object>> listApp;
        listApp = jdbcTemplate.queryForList(selectApp);

        String selectSo = "select id,service_object_name as name,service_object_group as 'group'\n" +
                "from predict_mm_service_object;";
        List<Map<String,Object>> listSo;
        listSo = jdbcTemplate.queryForList(selectSo);

        List<Map<String,Object>> listNodes = new ArrayList<Map<String, Object>>();
        listNodes.addAll(listApp);
        listNodes.addAll(listSo);

        JSONObject matrix = new JSONObject();
        matrix.put("nodes",listNodes); // 得到了nodes的所有数据

        String selectSoAndApp = "select application_id , service_object_id from predict_mm_service_object_and_application;";
        List<Map<String,Object>> listSoAndApp;
        listSoAndApp = jdbcTemplate.queryForList(selectSoAndApp);


        int d = listSoAndApp.size();
        List<String> temp =  new ArrayList<String>(); //用来存key
        List<JSONObject> links = new ArrayList<JSONObject>();

        for(int n=0;n< d;n++) {
            // 1&2,3,54,
            String relaApp = listSoAndApp.get(n).get("application_id").toString()+"&"; // 一个数字
            String relaSo = listSoAndApp.get(n).get("service_object_id").toString()+","; //多个数字

            // nodes 前部分是app，后部分是so
            for (int i = 0; i < listNodes.size() - listSo.size(); i++) { //app 的部分
                String keyApp = listNodes.get(i).get("id").toString()+"&";
                if(relaApp.equals(keyApp)){ // match app
                    for (int j = listApp.size(); j < listNodes.size(); j++) { // so 的部分
                        String keySO = listNodes.get(j).get("id").toString()+","; //所引的时候还从AppAndSo里索引,一个数字
                        if(relaSo.contains(keySO)){ //match so
                            JSONObject link = new JSONObject();
                            link.put("value",1);
                            link.put("state",1);

                            link.put("target",j);
                            link.put("source",i);
                            links.add(link);
                        }

                    }
                }
            }

        }
        matrix.put("links",links);
        return matrix.toString();
    }
}
