package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmApplicationUser;
import cn.edu.shou.monitor.service.ApplicationUserManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ApplicationUserManagementController {
    @Autowired
    ApplicationUserManagementRepository applicationUserManagementRepository;
    @RequestMapping(value = "/getApplicationUserInfo")
    public String getApplicationUserInfo(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "ApplicationUserManagement";
    }
    @RequestMapping(value = "/getApplicationUserViewInfo/{applicationUserid}.html")
    public String getApplicationUserViewInfo(Model model,@PathVariable Long applicationUserid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据applicationUserId查找数据对象信息
        predictMmApplicationUser mmApplicationUser=applicationUserManagementRepository.findOne(applicationUserid);//查找应用用户数据
        model.addAttribute("applicationUser",mmApplicationUser);
        return "applicationUserManagementView";
    }
}
