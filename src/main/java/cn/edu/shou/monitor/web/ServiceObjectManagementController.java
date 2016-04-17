package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/3/6.
 */
@Controller
@RequestMapping(value = "/predictCenter")
public class ServiceObjectManagementController {
    @RequestMapping(value = "/getServiceObjectInfo")
    public String getServiceObjectInfo(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "serviceObjectManagement";
    }

    //获取虚拟机展示列表
    @RequestMapping(value = "/getSerObjShow")
    public String getSerObjShow(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "serObjShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getSerObjShowView/{hostname}/{hostid}")
    public String getSerObjShowView(Model model,@PathVariable String hostname,String hostid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("serObjHostname",hostname);
        model.addAttribute("serObjHostname",hostid);
        return "servicrObjectShowView";
    }
}
