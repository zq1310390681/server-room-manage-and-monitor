package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmVersion;
import cn.edu.shou.monitor.service.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sqhe18 on 2016/4/5.
 */
@Controller
@RequestMapping(value = "/predictCenter")
public class VersionManagementController {
    @Autowired
    VersionRepository versionRepository;
    //获取版本信息列表
    @RequestMapping(value = "/getVersionInfo")
    public String getVersionInfo(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user",currentUser);
        return "versionManagement";
    }
    //获取版本信息的详细页面
    @RequestMapping(value = "/getVersionView/{versionid}")
    public String getVersionView(Model model,@PathVariable Long versionid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user",currentUser);
        predictMmVersion predictVersion = versionRepository.findOne(versionid);
        model.addAttribute("version",predictVersion);
        return "versionManagementView";
    }
}
