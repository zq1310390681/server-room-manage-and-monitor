package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.predictMmLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@RestController
@RequestMapping(value ="/predictCenter/api/log" )
public class LogManagementApiController {
    @Autowired
    cn.edu.shou.monitor.service.LogManagementRepository LogManagementRepository;
    //获取所有日志数据信息
    @RequestMapping(value = "/getAllLogs")
    public List<predictMmLogs> getAllLogs(){
        return LogManagementRepository.findAll();
    }
    //创建日志
    @RequestMapping(value = "/createLog",method = RequestMethod.GET)
    public List<predictMmLogs> createLog(@RequestParam String log) {
        predictMmLogs predictLog=new predictMmLogs();
        predictLog.setLog(log);


        LogManagementRepository.save(predictLog);
        List<predictMmLogs> list=new ArrayList<predictMmLogs>();
        list.add(predictLog);
        return list;
    }
    //更新日志
    @RequestMapping(value = "updateLog")
    public List<predictMmLogs> updateLog(@RequestParam long id,@RequestParam String log){
        predictMmLogs predictLog=LogManagementRepository.findOne(id);
        predictLog.setLog(log);


        LogManagementRepository.save(predictLog);
        List<predictMmLogs> list=new ArrayList<predictMmLogs>();
        list.add(predictLog);
        return list;
    }
    //删除日志
    @RequestMapping(value = "deleteLog")
    public List<predictMmLogs> deleteLog(@RequestParam long id){
        predictMmLogs predictLog=LogManagementRepository.findOne(id);
        LogManagementRepository.delete(predictLog);
        List<predictMmLogs> list=new ArrayList<predictMmLogs>();
        list.add(predictLog);
        return list;

    }
}
