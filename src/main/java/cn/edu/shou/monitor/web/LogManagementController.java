package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@Controller
@RequestMapping(value = "/predictCenter/logs")
public class LogManagementController {
    @RequestMapping(value = "/getLogInfo")
    public String getLogInfo(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "logManagement";
    }
}
