package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmMiddle;
import cn.edu.shou.monitor.service.HostManagementRepository;
import cn.edu.shou.monitor.service.MiddleRepository;
import cn.edu.shou.monitor.service.OperatingSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/1/12.
 */
@Controller
@RequestMapping(value = "/predictCenter")
public class JavaManagementController {
    @Autowired
    MiddleRepository middleRepository;
    @Autowired
    HostManagementRepository hostManagementRepository;
    @Autowired
    OperatingSystemRepository operatingSystemRepository;
    @RequestMapping(value = "/getJavaInfo")
    public String getHostInfo(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "javaManagement";
    }
    @RequestMapping(value = "/getJavaView/{javaid}.html")
    public String getJavaView(Model model,@PathVariable Long javaid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据javaid查找数据对象信息
        predictMmMiddle mmJava=middleRepository.findOne(javaid);//查找Java数据库数据
        // 在详细页面显示主机名称
        String hostName = "";
        if (mmJava != null){
            hostName = hostManagementRepository.findOne(Long.parseLong(mmJava.getMiddleHost())).getHosts();
            mmJava.setMiddleHost(hostName);
        }
        // 在详细页面显示操作系统名称
        String os = "";
        if (mmJava != null){
            os = operatingSystemRepository.findOne(Long.parseLong(mmJava.getMiddleOS())).getOperatingSystem();
            mmJava.setMiddleOS(os);
        }
        model.addAttribute("java",mmJava);
        return "javaManagementView";
    }
    //获取虚拟机展示列表
    @RequestMapping(value = "/getJavaShow")
    public String getJavaShow(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "javaShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getJavaShowView/{hostname}/{hostid}")
    public String getJavaShowView(Model model,@PathVariable String hostname,String hostid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("javaHostname",hostname);
        model.addAttribute("hostid",hostid);
        return "javaShowView";
    }
}
