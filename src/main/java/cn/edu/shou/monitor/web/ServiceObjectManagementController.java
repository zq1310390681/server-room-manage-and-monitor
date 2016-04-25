package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmServiceObject;
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
public class ServiceObjectManagementController {
    cn.edu.shou.monitor.service.ServiceObjectManagementRepository ServiceObjectManagementRepository;
    @RequestMapping(value = "/getServiceObjectInfo")
    public String getServiceObjectInfo(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "serviceObjectManagement";
    }
    @RequestMapping(value = "/getServiceObjectViewInfo/{serviceObjectid}.html")
    public String getServiceObjectViewInfo(Model model,@PathVariable Long serviceObjectid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据serviceObjectid查找数据对象信息
        predictMmServiceObject MmServiceObject= ServiceObjectManagementRepository.findOne(serviceObjectid);//查找服务对象数据
        model.addAttribute("serviceObject",MmServiceObject);
        return "serviceObjectManagementView";
    }
    //获取虚拟机展示列表
    @RequestMapping(value = "/getSerObjShow")
    public String getSerObjShow(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "serObjShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getSerObjShowView/{hostname}/{hostid}")
    public String getSerObjShowView(Model model,@PathVariable String hostname,String hostid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("serObjHostname",hostname);
        model.addAttribute("hostid",hostid);
        return "servicrObjectShowView";
    }
}
