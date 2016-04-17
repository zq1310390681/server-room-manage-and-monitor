package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
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
public class JaveManagementControlle {
    @RequestMapping(value = "/getJaveInfo")
    public String getHostInfo(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "javaManagement";
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
        model.addAttribute("javaHostname",hostid);
        return "javaShowView";
    }
}
