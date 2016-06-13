package cn.edu.shou.monitor.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by sqhe18 on 2016/5/5.
 */
@RestController
@RequestMapping(value ="/predictCenter/netDiagram")
public class NetDiagramApiController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @RequestMapping(value = "/diagram")
    public String getDiagram(){
        String appName = "select application_name as 'AppName' from predict_mm_applications;";
        List<Map<String,Object>> listAppName ;
        listAppName = jdbcTemplate.queryForList(appName);

        String hostName = "select host_name as 'hostName'from predict_mm_applications;";
        List<Map<String,Object>> listHostName ;
        listHostName = jdbcTemplate.queryForList(hostName);

        String subSys = "select host_content as 'subSysName' from predict_mm_applications;";
        List<Map<String ,Object>> listSubSys;
        listSubSys = jdbcTemplate.queryForList(subSys);

        return "";
    }
}
