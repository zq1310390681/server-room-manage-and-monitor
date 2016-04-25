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
public class IISManagementController {
    @Autowired
    MiddleRepository middleRepository;
    @Autowired
    HostManagementRepository hostManagementRepository;
    @Autowired
    VersionRepository versionRepository;
    //获取IIS列表
    @RequestMapping(value = "/getIISInfo")
    public String getModelsInfo(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "IISManagement";
    }
    @RequestMapping(value = "/getIISView/{iisid}.html")
    public String getIISView(Model model,@PathVariable Long iisid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据iisid查找数据对象信息
        predictMmMiddle mmIIS=middleRepository.findOne(iisid);//查找IIS数据库数据
        // 在详细页面显示主机名称
        String hostName = "";
        if (mmIIS != null){
            hostName = hostManagementRepository.findOne(Long.parseLong(mmIIS.getMiddleHost())).getHosts();
            mmIIS.setMiddleHost(hostName);
        }
        //在详细页面显示版本信息名称
        String version = "";
        if (mmIIS  != null){
            version = versionRepository.findOne(Long.parseLong(mmIIS.getMiddleVersion())).getVersions();
            mmIIS.setMiddleVersion(version);
        }
        model.addAttribute("iis",mmIIS);
        return "iisManagementView";
    }
    //获取虚拟机展示列表
    @RequestMapping(value = "/getIisShow")
    public String getIisShow(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "iisShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getIisShowView/{hostname}/{hostid}")
    public String getIisShowView(Model model,@PathVariable String hostname,String hostid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("iisHostname",hostname);
        model.addAttribute("hostid",hostid);
        return "iisShowView";
    }
}
