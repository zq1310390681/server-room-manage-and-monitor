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
public class TomcatManagementController {
    //获取Tomcat列表
    @RequestMapping(value = "/getTomcatInfo")
    public String getModelsInfo(Model model,@AuthenticationPrincipal User currentUser) {
        model.addAttribute("user", currentUser);
        return "tomcatManagement";
    }
    //获取虚拟机展示列表
    @RequestMapping(value = "/getTomcatShow")
    public String getTomcatShow(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "tomcatShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getTomcatShowView/{hostname}/{hostid}")
    public String getTomcatShowView(Model model,@PathVariable String hostname,String hostid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("tomcatHostname",hostname);
        model.addAttribute("tomcatHostname",hostid);
        return "tomcatShowView";
    }
}
