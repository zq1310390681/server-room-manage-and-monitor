package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/1/5.
 */
@Controller
@RequestMapping(value = "/predictCenter")
public class IISManagementController {
    //获取IIS列表
    @RequestMapping(value = "/getIISInfo")
    public String getModelsInfo(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "IISManagement";
    }
    //获取虚拟机展示列表
    @RequestMapping(value = "/getIisShow")
    public String getIisShow(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "iisShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getIisShowView/{hostname}/{hostid}")
    public String getIisShowView(Model model,@PathVariable String hostname,String hostid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("iisHostname",hostname);
        model.addAttribute("iisHostname",hostid);
        return "iisShowView";
    }
}
