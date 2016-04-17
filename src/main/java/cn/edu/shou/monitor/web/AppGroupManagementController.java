package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.PredictMmAppGroup;
import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.service.AppGroupManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sqhe18 on 2016/4/4.
 */
@Controller
@RequestMapping(value = "/predictCenter")
public class AppGroupManagementController {
    @Autowired
    AppGroupManagementRepository appGroupManagementRepository;

    //获取告警规则列表
    @RequestMapping(value = "/getAppGroupInfo")
    public String getAppGroupInfo(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute("user", currentUser);
        return "appGroupManagement";
    }
    @RequestMapping(value = "/getAppGroupView/{appGroupid}.html")
    public String getAppGroupView(Model model,@PathVariable Long appGroupid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据pingId查找数据对象信息
        PredictMmAppGroup appGroup=appGroupManagementRepository.findOne(appGroupid);//查找ping数据
        model.addAttribute("appGroup",appGroup);
        return "appGroupManagementView";
    }
}
