package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmMiddle;
import cn.edu.shou.monitor.service.HostManagementRepository;
import cn.edu.shou.monitor.service.MiddleRepository;
import cn.edu.shou.monitor.service.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    MiddleRepository middleRepository;
    @Autowired
    HostManagementRepository hostManagementRepository;
    @Autowired
    VersionRepository versionRepository;
    //获取Tomcat列表
    @RequestMapping(value = "/getTomcatInfo")
    public String getModelsInfo(Model model,@AuthenticationPrincipal User currentUser) {
        model.addAttribute("user", currentUser);
        return "tomcatManagement";
    }
    @RequestMapping(value = "/getTomcatView/{tomcatid}.html")
    public String getTomcatView(Model model,@PathVariable Long tomcatid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据tomcatid查找数据对象信息
        predictMmMiddle mmTomcat = middleRepository.findOne(tomcatid);//查找Tomcat数据库数据
        // 显示主机名称在tomcatManagementView页面
        String hostName = "";
        if(mmTomcat != null){
            hostName = hostManagementRepository.findOne(Long.parseLong(mmTomcat.getMiddleHost())).getHosts();
            mmTomcat.setMiddleHost(hostName);
        }
        //显示版本信息名称在tomcatManagementView页面
        String version = "";
        if (mmTomcat != null){
            version = versionRepository.findOne(Long.parseLong(mmTomcat.getMiddleVersion())).getVersions();
            mmTomcat.setMiddleVersion(version);
        }
        model.addAttribute("tomcat",mmTomcat);
        return "tomcatManagementView";
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
        model.addAttribute("hostid",hostid);
        return "tomcatShowView";
    }
}
