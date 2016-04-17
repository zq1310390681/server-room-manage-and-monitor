package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmRouters;
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
@RequestMapping(value = "/predictCenter/routers")
public class RouterManagementController {
    @Autowired
    cn.edu.shou.monitor.service.RouterManagementRepository RouterManagementRepository;
    @RequestMapping(value = "/getRouterInfo")
    public String getRouterInfo(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "routerManagement";
    }
    @RequestMapping(value = "/getRouterViewInfo/{routerid}.html")
    public String getRouterViewInfo(Model model,@PathVariable Long routerid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据routerId查找数据对象信息
        predictMmRouters mmRouter= RouterManagementRepository.findOne(routerid);//查找router数据
        model.addAttribute("router",mmRouter);
        return "routerManagementView";
    }
    //获取监控展示页面列表
    @RequestMapping(value = "/getRouterShow")
    public String getRouterShow(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "routerShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getRouterShowView/{hostname}")
    public String getRouterShowView(Model model,@PathVariable  String hostname,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("routerHostname",hostname);
        return "routerShowView";
    }
}
