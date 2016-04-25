package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmMiddle;
import cn.edu.shou.monitor.service.HostManagementRepository;
import cn.edu.shou.monitor.service.MiddleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/1/2.
 */
@Controller
@RequestMapping(value = "/predictCenter")
public class OracleManagementController {
    @Autowired
    MiddleRepository middleRepository;
    @Autowired
    HostManagementRepository hostManagementRepository;

    //获取Oracle数据库列表
    @RequestMapping(value = "/getOracleInfo")
    public String getModelsInfo(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "oracleManagement";
    }
    @RequestMapping(value = "/getOracleView/{oracleid}")
    public String getOracleInfoView(Model model,@PathVariable Long oracleid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user",currentUser);
        //根据oracleid查找数据对象信息
        predictMmMiddle mmMiddle = middleRepository.findOne(oracleid);//查找oracle数据
        String hostName="";
        if(mmMiddle!=null)
        {
            hostName = hostManagementRepository.findOne(Long.parseLong(mmMiddle.getMiddleHost())).getHosts();
            mmMiddle.setMiddleHost(hostName);
        }
        model.addAttribute("oracle", mmMiddle);
        return "oracleManagementView";
    }
    //获取虚拟机展示列表
    @RequestMapping(value = "/getOracleShow")
    public String getOracleShow(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "oracleShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getOracleShowView/{hostname}/{hostid}")
    public String getOracleShowView(Model model,@PathVariable String hostname,String hostid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("oracleHostname",hostname);
        model.addAttribute("hostid",hostid);
        return "oracleShowView";
    }
}
