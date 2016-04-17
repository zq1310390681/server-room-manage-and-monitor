package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmSwitchboards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@Controller
@RequestMapping(value = "/predictCenter/switchboards")
public class SwitchboardManagementController {
    @Autowired
    cn.edu.shou.monitor.service.SwitchboardManagementRepository SwitchboardManagementRepository;
    @RequestMapping(value = "/getSwitchboardInfo")
    public String getSwitchboardInfo(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "switchboardManagement";
    }
    @RequestMapping(value = "/getSwitchboardViewInfo/{switchboardid}.html")
    public String getSwitchboardViewInfo(Model model,@PathVariable Long switchboardid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据switchboardId查找数据对象信息
        predictMmSwitchboards mmSwitchboard= SwitchboardManagementRepository.findOne(switchboardid);//查找switchboard数据
        model.addAttribute("switchboard",mmSwitchboard);
        return "switchboardManagementView";
    }
    //获取监控展示页面列表
    @RequestMapping(value = "/getSwitchShow")
    public String getSwitchShow(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "switchShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getSwitchShowView/{hostname}/{hostid}")
    public String getSwitchShowView(Model model,@PathVariable  String hostname,String hostid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("switchHostname",hostname);
        model.addAttribute("switchHostname",hostid);
        return "switchShowView";
    }
}
