package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.spring.TargetDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by light on 2016/5/11.
 */
@RestController
@Repository
public class ZbxScreenThreeToOneRepository {
    @Autowired
    ZbxScreenRepository screenRepository;

    //关于disk的
    @TargetDataSource(name = "zabbix")
    public List<Map<String,Object>> getDisk(){
        return screenRepository.getDiskDis();
    }
    @RequestMapping(value = "/disk192")
    @TargetDataSource(name = "zabbix192")
    public List<Map<String,Object>> getDisk192(){
        return screenRepository.getDiskDis();
    }

    //关于cpu的
    @TargetDataSource(name = "zabbix")
    public List<Map<String,Object>> getCpu(){
        return screenRepository.getCpuDis();
    }

    @RequestMapping(value = "/cpu192")
    @TargetDataSource(name = "zabbix192")
    public List<Map<String,Object>> getCpu192(){
        return screenRepository.getCpuDis();
    }

    //关于ram的
    @TargetDataSource(name = "zabbix")
    public List<Map<String,Object>> getRam(){
        return screenRepository.getRamDis();
    }

    @RequestMapping(value = "/ram192")
    @TargetDataSource(name = "zabbix192")
    public List<Map<String,Object>> getRam192(){
        return screenRepository.getRamDis();
    }

}
