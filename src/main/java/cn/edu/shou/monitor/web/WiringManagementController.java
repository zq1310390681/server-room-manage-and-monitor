package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by light on 2016/5/17.
 */
@Controller
@RequestMapping(value = "/predictCenter/wiring")
public class WiringManagementController {
    @RequestMapping(value = "/getWiringInfo")
    public String getWiringInfo(Model model,@AuthenticationPrincipal User currentUser) {
        model.addAttribute("user", currentUser);
        return "wiringManagement";
    }
}
