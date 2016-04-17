package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/1/27.
 */
@Controller
@RequestMapping(value = "/predictCenter")
public class OperatingSystemManagementController {
    @RequestMapping(value = "/getOperatingSystemInfo")
    public String getOperatingSystemInfo(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "operatingSystemManagement";
    }
}
