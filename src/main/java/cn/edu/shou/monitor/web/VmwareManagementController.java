package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmHost;
import cn.edu.shou.monitor.service.HostManagementRepository;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2015/12/31.
 */
@Controller
@RequestMapping(value = "/predictCenter")
public class VmwareManagementController {
    HostManagementRepository hostManagementRepository;
    /* 获取VMWare虚拟机列表 */
    @RequestMapping(value = "/getVmwareInfo")
    public String getServicesInfo(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "vmwareManagement";
    }
    //获取每个虚拟机的详细页面
    @RequestMapping(value = "/getVmwareView/{vmwareid}.html")
    public String getVmwareView(Model model,@PathVariable Long vmwareid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据vmwareid查找数据对象信息
        predictMmHost mmPing=hostManagementRepository.findOne(vmwareid);//查找ping数据
        model.addAttribute("ping",mmPing);
        return "vmwareManagementView";
    }
    //获取虚拟机展示列表
    @RequestMapping(value = "/getVmwareShow")
    public String getVmwareShow(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "vmwareShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getVmwareShowView/{hostname}/{hostid}")
    public String getVmwareShowView(Model model,@PathVariable String hostname,String hostid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("vmwareHostname",hostname);
        model.addAttribute("vmwareHostname",hostid);
        return "vmwareShowView";
    }
}
