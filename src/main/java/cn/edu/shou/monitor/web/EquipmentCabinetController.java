package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sqhe18 on 2016/4/5.
 */
@Controller
@RequestMapping(value = "/predictCenter")
public class EquipmentCabinetController {
    @RequestMapping(value = "/getCabinetInfo")
    public String getCabinetInfo(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "cabinetManagement";
    }
}
