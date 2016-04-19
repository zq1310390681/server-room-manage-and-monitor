package cn.edu.shou.monitor.transmission;

import cn.edu.shou.monitor.service.impl.ZbxHostServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by light on 2016/3/20.
 * Set a schedule to send the perf and event MQ timed.
 */
@Component
@RestController
@RequestMapping(value = "/predictCenter/screen")
public class Schedule {
    private static Logger log = Logger.getLogger(Schedule.class);

    //@Scheduled(fixedRate = 5000)       //perf 的队列
    public void sendPerfTimed(){
        MQPerformance perf = new MQPerformance();
        String memoryPerfMQ = perf.performItem("memory.size[available]");
        String cpuPerfMQ =perf.performItem("system.cpu.load[percpu,avg1]");
        MQSendMessage.sendMessages(memoryPerfMQ, "perf");
        MQSendMessage.sendMessages(cpuPerfMQ, "perf");
    }

    //@Scheduled(fixedRate = 3000)     // event 的队列
    public void SendEventMq(){
        ArrayList<String> hostIdsArray = ZbxHostServiceImpl.HostArray.getHostId();
        for(String hostIdSingle : hostIdsArray ){
            MQEvent testEvent = new MQEvent();
            String eventMQ = testEvent.showEvent(hostIdSingle);
            MQSendMessage.sendMessages(eventMQ, "event");
        }
    }

    // Send msgs to the front screen
    //@Scheduled(fixedRate = 3000)
    @RequestMapping(value = "/ramUsage")
    public String sendRAMToFront() {
        FrontScreen frontScreen = new FrontScreen();
        JSONArray ram = frontScreen.sendMemory();
        JSONObject sendRAM = new JSONObject();
        sendRAM.put("disk",ram);
        sendRAM.put("dateTime",System.currentTimeMillis());
        return sendRAM.toString().replace("null,","");
    }

    @RequestMapping(value = "/cpuUsage")
    public String sendCPUToFront() {
        FrontScreen frontScreen = new FrontScreen();
        JSONArray cpu = frontScreen.sendCpu();
        JSONObject sendCPU = new JSONObject();
        sendCPU.put("cpu",cpu);
        sendCPU.put("dateTime",System.currentTimeMillis());
        return sendCPU.toString().replace("null,","");
    }

    @RequestMapping(value = "/diskUsage")
    public String sendDISKToFront() {
        FrontScreen frontScreen = new FrontScreen();
        JSONArray disk = frontScreen.sendDisk();
        JSONObject sendDisk = new JSONObject();
        sendDisk.put("disk",disk);
        sendDisk.put("dateTime",System.currentTimeMillis());
        return sendDisk.toString().replace("null,","");
    }

    @RequestMapping(value = "/unionUsage")
    public String sendUnion(){
        FrontScreen frontScreen = new FrontScreen();
        long time = System.currentTimeMillis();
        //log.debug("start send union "+time);
        JSONArray disk = frontScreen.sendDisk();
        //log.debug("disk end "+ (System.currentTimeMillis()-time));
        time = System.currentTimeMillis();
        JSONArray cpu = frontScreen.sendCpu();
        //log.debug("cpu end "+(System.currentTimeMillis()-time));
        time = System.currentTimeMillis();
        JSONArray ram = frontScreen.sendMemory();
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
        return FrontScreen.sendPing();
    }

    @RequestMapping(value = "/input")
    public String sendInputNet(){
        return FrontScreen.sendPutNet("input");
    }

    @RequestMapping(value = "/output")
    public String sendOutputNet(){
        return FrontScreen.sendPutNet("output");
    }

    @RequestMapping(value = "/superComputerShort")
    public String sendScDataShort() throws Exception{
        return SuComputerScreen.sendSuComputer("qstat \n");
    }

    @RequestMapping(value = "/superComputerLong")
    public String sendScDataLong() throws Exception{
        return SuComputerScreen.sendSuComputer("pbsnodes \n");
    }
}
