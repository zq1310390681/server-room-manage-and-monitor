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
public class DynamicEnvironmentManagementControlle {
    @RequestMapping(value = "/getDynamicEnvironmentInfo")
    public String getDynamicEnvironmentInfo(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "dynamicEnvironmentManagement";
    }
    //获取虚拟机展示列表
    @RequestMapping(value = "/getEnvironShow")
    public String getEnvironShow(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "environShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getEnvironShowView/{hostname}/{hostid}")
    public String getEnvironShowView(Model model,@PathVariable String hostname,String hostid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("environHostname",hostname);
        model.addAttribute("environHostname",hostid);
        return "environShowView";
    }
}
