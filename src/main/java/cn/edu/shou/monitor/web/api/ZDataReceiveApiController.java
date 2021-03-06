package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.service.ZDataReceiveRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by light on 2016/4/4.
 */
@RestController
@RequestMapping(value = "/predictCenter/screen")
public class ZDataReceiveApiController {
    @Autowired
    ZDataReceiveRepository receive;

    @RequestMapping(value = "/radar")
    public List<Map<String,Object>> getRadar(){
        return receive.checkNum("radar");
    }

    @RequestMapping(value = "/xradar")
    public List<Map<String,Object>> getXRadar(){
        return receive.checkNum("xradar");
    }

    @RequestMapping(value = "/vos")
    public List<Map<String,Object>>getVos(){
        return receive.checkNum("vos");
    }

    @RequestMapping(value = "/buoy")
    public List<Map<String,Object>>getBuoy(){
        return receive.checkNum("buoy");
    }

    @RequestMapping(value = "/seaReal")
    public List<Map<String,Object>>getSeaReal(){
        return receive.checkSeaNum("seaReal");
    }

    @RequestMapping(value = "/seaHourly")
    public List<Map<String,Object>>getSeaHourly(){
        return receive.checkSeaNum("seaHourly");
    }

    @RequestMapping(value = "/seaPun")
    public List<Map<String,Object>>getSeaPun(){
        return receive.checkSeaNum("seaPun");
    }

    @RequestMapping(value = "/receiveRate")
    public String receiveRate(){
        List<List<Map<String,Object>>> check = new ArrayList<>();
        check.add(receive.checkNum("radar"));
        check.add(receive.checkNum("xradar"));
        check.add(receive.checkNum("vos"));
        check.add(receive.checkNum("buoy"));
        check.add(receive.checkSeaNum("seaReal"));
        check.add(receive.checkSeaNum("seaHourly"));
        check.add(receive.checkSeaNum("seaPun"));
//        check.add(receive.getStationNum());   //正式上线用这个

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String rate;
        double quantitySum =0;
        double checkSum=0;
        for(List<Map<String,Object>> list : check){
            double a = Double.valueOf(list.get(0).get("fenziAll").toString()); //固定第一位为check
            double b = Double.valueOf(list.get(0).get("fenmuAll").toString());
            quantitySum = quantitySum + a;
            checkSum = checkSum + b;
        }
        rate = decimalFormat.format(100*quantitySum/checkSum);
        JSONObject receiveRate = new JSONObject();
        receiveRate.put("receiveRate",rate);
        return receiveRate.toString();
    }

    @RequestMapping(value = "/stationSum")
    public List<Map<String,Object>> sendStationSum(){
        return receive.getStationNum();
    }

    @RequestMapping(value = "/gpsInfo")
    public List<Map<String,Object>> getGpsInfo(){
        return receive.getGpsInfo();
    }

    @RequestMapping(value = "/stationAndRadar")
    public Map<String,List<Map<String,Object>>> sendStationAndRadar(){
//        JSONObject together = new JSONObject();
        Map<String,List<Map<String,Object>>> together = new HashMap<>();
        List<Map<String,List<Map<String,Object>>>> templist = new ArrayList<>();
        List<Map<String,Object>> sta = receive.getStationNum();
        List<Map<String,Object>> rd = receive.checkNum("radar");
        List<Map<String,Object>> xrd = receive.checkNum("xradar");
        together.put("station",sta);
        together.put("radar",rd);
        together.put("xradar",xrd);
        return together;
    }

    @RequestMapping(value = "/db_status")
    public Map<String,Object> getDbStatus(){
        return receive.getDbStatus();
    }

    @RequestMapping(value = "/rtnormal")
    public List<Map<String,Object>> rtnormal(){
        return receive.getNetworkRealData();
    }

    @RequestMapping(value = "/rterror")
    public List<Map<String,Object>> rterror(){
        return receive.getNetworkError();
    }

    @RequestMapping(value = "/networkRealTimeData") // 包括正常和有错误的
    public Map<String,List<Map<String,Object>>> getRealTimeData(){
        Map<String,List<Map<String,Object>>> realTimeData = new HashMap<>();
        List<Map<String,Object>> normal = receive.getNetworkRealData();
        List<Map<String,Object>> error = receive.getNetworkError();
        realTimeData.put("normal",normal);
        realTimeData.put("error",error);
        return realTimeData;
    }

    @RequestMapping(value = "/dbDataNum")
    public Map<String,Object> getDbDataNum(){
        return receive.getDbDataNum();
    }

}
