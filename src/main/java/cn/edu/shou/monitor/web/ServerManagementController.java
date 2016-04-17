package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmPing;
import cn.edu.shou.monitor.domain.predictMmServers;
import cn.edu.shou.monitor.service.ServerManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@Controller
@RequestMapping(value = "/predictCenter/servers")
public class ServerManagementController {
    @Autowired
    ServerManagementRepository ServerManagementRepository;
    @RequestMapping(value = "/getServerInfo")
    public String getServerInfo(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "serverManagement";
    }
    @RequestMapping(value = "/getServerInfo/{serverid}.html")
    public String getServerInfo(Model model,@PathVariable Long serverid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据serverId查找数据对象信息
        predictMmServers MmServers= ServerManagementRepository.findOne(serverid);//查找服务器数据
        model.addAttribute("server",MmServers);
        return "serverManagementView";
    }
    @RequestMapping(value = "/getServerShow")
    public String getServerShow(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user",currentUser);
        return "serverShow";
    }

    @RequestMapping(value = "/view/{hostname}/{hostid}")
    public String getServerGraph(Model model,@PathVariable String hostname,String hostid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据serverId查找数据对象信息

        model.addAttribute("serverHostname",hostname);
        model.addAttribute("hostid",hostid);
        return "serverShowView";
    }
}

