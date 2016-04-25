package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmHost;
import cn.edu.shou.monitor.service.HostManagementRepository;
import cn.edu.shou.monitor.service.MiddleRepository;
import cn.edu.shou.monitor.service.OperatingSystemRepository;
import cn.edu.shou.monitor.service.ServerManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/1/25.
 */
@Controller
@RequestMapping(value = "/predictCenter")
public class HostManagementController {
    @Autowired
    MiddleRepository middleRepository;
    @Autowired
    OperatingSystemRepository osRepository;
    @Autowired
    ServerManagementRepository serverRepository;
    @Autowired
    HostManagementRepository hostManagementRepository;


    /* 获取物理主机列表 */
    @RequestMapping(value = "/getHostInfo")
    public String getHostInfo(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "hostManagement";
    }
    //20160414 张倩修改
    @RequestMapping(value = "/getHostViewInfo/{hostid}.html")
    public String getHostViewInfo(Model model,@PathVariable Long hostid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据hostid查找数据对象信息
        predictMmHost host= hostManagementRepository.findOne(hostid);//查找主机数据
        //20160414 张倩 显示主机操作系统名称
        String osName="";//操作系统名称
        if (host!=null){
            osName=osRepository.findOne(Long.parseLong(host.getHostOS())).getOperatingSystem();
            host.setHostOS(osName);
        }
        //显示主机所在服务器名称
        String serverName="";//服务器名称zaini
        if (host!=null){
            serverName = serverRepository.findOne(Long.parseLong(host.getHostServer())).getServerSerialNumber();
            host.setHostServer(serverName);
        }
        model.addAttribute("host",host);
        return "hostManagementView";
    }



    //获取监控展示页面列表
    @RequestMapping(value = "/getHostShow")
    public String getHostShow(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "hostShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getHostShowView/{hostname}/{hostId}")
    public String getHostShowView(Model model,@PathVariable("hostname") String hostname,@PathVariable("hostId") String hostId,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("hostname",hostname);
        model.addAttribute("hostid",hostId);
        return "hostShowView";
    }
}
