package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.service.ZbxScreenRepository;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by light on 2016/4/5.
 */
@RestController
@RequestMapping(value = "/predictCenter/screen", method= RequestMethod.GET,produces={"application/xml", "application/json"})
public class ZbxScreenApiController {
    private static Logger log = Logger.getLogger(ZbxScreenApiController.class);
    /****从数据库**************************************************************************/
    @Autowired
    ZbxScreenRepository zbx;
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
        long time = System.currentTimeMillis();
        log.debug("start send union "+time);
        List<Map<String,Object>> cpu = zbx.getCpu();
        log.debug("disk end "+ (System.currentTimeMillis()-time));
        time = System.currentTimeMillis();
        List<Map<String,Object>> disk = zbx.getDisk();
        log.debug("cpu end "+(System.currentTimeMillis()-time));
        time = System.currentTimeMillis();
        List<Map<String,Object>> ram = zbx.getRam();
        log.debug("menory end "+(System.currentTimeMillis()-time));
        time = System.currentTimeMillis();
        JSONObject union = new JSONObject();
        union.put("disk",disk);
        union.put("ram",ram);
        union.put("cpu",cpu);
        union.put("dateTime",System.currentTimeMillis());
        log.debug("end send union "+(System.currentTimeMillis()-time));
        return union.toString();
    }

    /****ZBX API**************************************************************************/

    // Send msgs to the front screen
    @RequestMapping(value = "/ramUsage")
    public String sendRAMToFront() {
        JSONArray ram = zbx.sendMemory();
        JSONObject sendRAM = new JSONObject();
        sendRAM.put("disk",ram);
        sendRAM.put("dateTime",System.currentTimeMillis());
        return sendRAM.toString().replace("null,","");
    }

    @RequestMapping(value = "/cpuUsage")
    public String sendCPUToFront() {
        JSONArray cpu = zbx.sendCpu();
        JSONObject sendCPU = new JSONObject();
        sendCPU.put("cpu",cpu);
        sendCPU.put("dateTime",System.currentTimeMillis());
        return sendCPU.toString().replace("null,","");
    }

    @RequestMapping(value = "/diskUsage")
    public String sendDISKToFront() {
        JSONArray disk = zbx.sendDisk();
        JSONObject sendDisk = new JSONObject();
        sendDisk.put("disk",disk);
        sendDisk.put("dateTime",System.currentTimeMillis());
        return sendDisk.toString().replace("null,","");
    }

    @RequestMapping(value = "/unionUsage")
    public String sendUnion(){
        long time = System.currentTimeMillis();
        //log.debug("start send union "+time);
        JSONArray disk = zbx.sendDisk();
        //log.debug("disk end "+ (System.currentTimeMillis()-time));
        time = System.currentTimeMillis();
        JSONArray cpu = zbx.sendCpu();
        //log.debug("cpu end "+(System.currentTimeMillis()-time));
        time = System.currentTimeMillis();
        JSONArray ram = zbx.sendMemory();
        //log.debug("menory end "+(System.currentTimeMillis()-time));
        time = System.currentTimeMillis();
        JSONObject union = new JSONObject();
        union.put("disk",disk);
        union.put("ram",ram);
        union.put("cpu",cpu);
        union.put("dateTime",System.currentTimeMillis());
        //log.debug("end send union "+(System.currentTimeMillis()-time));
        return union.toString().replace("null,","");
    }


    @RequestMapping(value = "/ping")
    public String sendPingToFront(){
        return zbx.sendPing();
    }

    @RequestMapping(value = "/input")
    public String sendInputNet(){
        return zbx.sendPutNet("input");
    }

    @RequestMapping(value = "/output")
    public String sendOutputNet(){
        return zbx.sendPutNet("output");
    }
}
