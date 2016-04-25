package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmPing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/1/12.
 */
@Controller
@RequestMapping(value = "/predictCenter")
public class PingManagementController {
    @Autowired
    cn.edu.shou.monitor.service.PingManagementRepository pingManagementRepository;
    //获取Ping列表
    @RequestMapping(value = "/getPingInfo")
    public String getModelsInfo(Model model,@AuthenticationPrincipal User currentUser) {
        model.addAttribute("user", currentUser);
        return "pingManagement";
    }
    @RequestMapping(value = "/getPingViewInfo/{pingid}.html")
    public String getPingViewInfo(Model model,@PathVariable Long pingid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据pingId查找数据对象信息
        predictMmPing mmPing=pingManagementRepository.findOne(pingid);//查找ping数据
        model.addAttribute("ping",mmPing);
        return "pingManagementView";
    }
    @RequestMapping(value = "/getPingShow")
    public String getPingShow(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user",currentUser);
        return "pingShow";
    }
    @RequestMapping(value = "/getPingShowView/{hostname}/{hostid}")
    public String getPingShowView(Model model,@PathVariable  String hostname,String hostid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        model.addAttribute("pingHostname",hostname);
        model.addAttribute("hostid",hostid);
        return "pingShowView";
    }
}
