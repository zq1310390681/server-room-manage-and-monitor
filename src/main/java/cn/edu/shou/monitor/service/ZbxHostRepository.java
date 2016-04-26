package cn.edu.shou.monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by light on 2016/4/26.
 */
@Repository
public class ZbxHostRepository {
    @Autowired
    JdbcTemplate jdbc;

    public List<String> getHostid(){
        String sql = "SELECT host_id FROM predict.zbx_hosts_sms;";
        List<Map<String,Object>> hostidMap = new ArrayList<Map<String,Object>>();
        hostidMap = jdbc.queryForList(sql);

        List<String> hostid = new ArrayList<>();
        for(Map map : hostidMap){
            String temp = map.get("host_id").toString();
            hostid.add(temp);
        }
        return hostid;
    }
}
