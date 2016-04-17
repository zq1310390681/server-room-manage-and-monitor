package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmMiddle;
import cn.edu.shou.monitor.service.MiddleRepository;
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
    /* 获取物理主机列表 */
    @RequestMapping(value = "/getHostInfo")
    public String getHostInfo(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "hostManagement";
    }
    @RequestMapping(value = "/getHostViewInfo/{hostid}.html")
    public String getHostViewInfo(Model model,@PathVariable Long hostid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据hostid查找数据对象信息
        predictMmMiddle predictMiddle= middleRepository.findOne(hostid);//查找主机数据
        model.addAttribute("phyHost",predictMiddle);
        return "hostManagementView";
    }
    //获取监控展示页面列表
    @RequestMapping(value = "/getHostShow")
    public String getHostShow(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "hostShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getHostShowView/{hostname}/{hostid}")
    public String getHostShowView(Model model,@PathVariable String hostname,String hostid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("hostname",hostname);
        model.addAttribute("hostid",hostid);
        return "hostShowView";
    }
}
