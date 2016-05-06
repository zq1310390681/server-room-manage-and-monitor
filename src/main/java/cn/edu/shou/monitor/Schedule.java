package cn.edu.shou.monitor;

import cn.edu.shou.monitor.service.ZActiveMQRepository;
import cn.edu.shou.monitor.service.ZPhoneSmsRepository;
import cn.edu.shou.monitor.service.impl.ZbxHostServiceImpl;
import cn.edu.shou.monitor.service.ZbxHostRepository;
import cn.edu.shou.monitor.spring.TargetDataSource;
import cn.edu.shou.monitor.util.SmsSend;
import cn.edu.shou.monitor.util.ActiveMQ;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.util.*;

/**
 * Created by light on 2016/3/20.
 * Set a schedule
 */
@Component
@RestController
@RequestMapping(value = "/predictCenter/screen")
public class Schedule {
    @Autowired
    ZbxHostRepository zbxHost;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ZActiveMQRepository activeMQ;
    @Autowired
    ZPhoneSmsRepository phoneMessage;

    private static Logger log = Logger.getLogger(Schedule.class);

    //@Scheduled(fixedRate = 5000)       //perf 的队列
    public void sendPerfTimed(){
        String memoryPerfMQ = activeMQ.performItem("memory.size[available]");
        String cpuPerfMQ =activeMQ.performItem("system.cpu.load[percpu,avg1]");
        ActiveMQ.sendMessages(memoryPerfMQ, "perf");
        ActiveMQ.sendMessages(cpuPerfMQ, "perf");
    }

    //@Scheduled(fixedRate = 3000)     // event 的队列
    public void SendEventMq(){
        ArrayList<String> hostIdsArray = ZbxHostServiceImpl.HostArray.getHostId();
        for(String hostIdSingle : hostIdsArray ){
            String eventMQ = activeMQ.showEvent(hostIdSingle);
            ActiveMQ.sendMessages(eventMQ, "event");
        }
    }


//  @Scheduled(fixedRate = 1000 * 60 * 20)  //定时短信
    @RequestMapping(value = "/sendSms")
    public void sendSms(){
        List<String> hostidArr = new ArrayList<>();
        hostidArr = zbxHost.getHostid();
        SmsSend send = new SmsSend();
        for(String hostid : hostidArr){
            String content = phoneMessage.sendMessage(hostid);
            if(!(content == null || content.length() <= 0)) {
                send.sendMessage("13003269434", content);
            }
        }
    }

    //@Scheduled(fixedRate = 1000 * 60 * 5) // 定时处理海洋站的数据
    @TargetDataSource(name = "webdata")  // schedule
    public void updSeaStaNum(){
        String dailyInitSql = "UPDATE sea_quantity SET last_real=0, current_num=0;"; //一天开始的初始化
        String hourlyInitSql = "UPDATE sea_quantity SET last_real=0;"; //每小时初始化

        Date date = new Date();
        DateFormat df3 = DateFormat.getTimeInstance();//只显示出时分秒
        String currentTime = df3.format(date);
        if(currentTime.equals("00:00:00")){
            jdbcTemplate.update(dailyInitSql);
        }
        //sql sea station
        //sql sea quantity
        String sql = "SELECT sea_station.name,sea_quantity.last_real,sea_quantity.current_num,sea_station.real_quantity,sea_station.real_time,sea_station.parent_name\n" +
                "FROM webdata.sea_quantity,sea_station where sea_station.name = sea_quantity.name;";
        //compare last and real quantity
        List<Map<String,Object>> compareList = jdbcTemplate.queryForList(sql);
        for(Map element : compareList){
            int lastValue = Integer.parseInt(element.get("last_real").toString());
            int realQuantity = Integer.parseInt(element.get("real_quantity").toString());
            int currentNumOld = Integer.parseInt(element.get("current_num").toString());
            int currentNumNew;
            String name = element.get("name").toString();
            if(lastValue<=realQuantity){
                String updLastValue = "UPDATE sea_quantity SET last_real="+realQuantity+" where name = '"+name+"';";
                jdbcTemplate.update(updLastValue);
            }else{                   //last + currentNumOld = current
                currentNumNew = lastValue + currentNumOld; //需要新加上去的
                String updTwoValue ="UPDATE sea_quantity SET last_real="+ realQuantity +",current_num="+ currentNumNew +" where name ='"+name+"';";
                jdbcTemplate.update(updTwoValue);
            }
        }
    }
}
