package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.service.ZbxScreenRepository;
import cn.edu.shou.monitor.transmission.PhoneMessage;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by light on 2016/4/5.
 */
@RestController
@RequestMapping(value = "/predictCenter/screen", method= RequestMethod.GET,produces={"application/xml", "application/json"})
public class ZbxScreenApiController {
    @Autowired
    ZbxScreenRepository zbx;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    PhoneMessage phoneMessage;

    @RequestMapping(value = "/cpu")
    public List<Map<String,Object>> getCpu(){
        List<Map<String,Object>> list=zbx.getCpu();
        return list;
    }

    @RequestMapping(value = "/disk")
    public List<Map<String,Object>> getDisk(){
        List<Map<String,Object>> list = zbx.getDisk();
        return list;
    }

    @RequestMapping(value = "/ram")
    public List<Map<String,Object>> getRam(){
        List<Map<String,Object>> list = zbx.getRam();
        return list;
    }

    @RequestMapping(value = "/union")
    public String unionSend(){
        List<Map<String,Object>> cpu = zbx.getCpu();
        List<Map<String,Object>> disk = zbx.getDisk();
        List<Map<String,Object>> ram = zbx.getRam();
        JSONObject union = new JSONObject();
        union.put("disk",disk);
        union.put("ram",ram);
        union.put("cpu",cpu);
        union.put("dateTime",System.currentTimeMillis());
        return union.toString();

    }

    @RequestMapping(value = "/testSms")
    public String testSms(){
        String name = phoneMessage.testSendSms("10212");
        return name;
    }

}
