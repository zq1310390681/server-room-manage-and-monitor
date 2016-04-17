package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmStores;
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
@RequestMapping(value = "/predictCenter/stores")
public class StoreManagementController {
    @Autowired
    cn.edu.shou.monitor.service.StoreManagementRepository StoreManagementRepository;
    @RequestMapping(value = "/getStoreInfo")
    public String getStoreInfo(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "storeManagement";
    }
    @RequestMapping(value = "/getStoreInfo/{storeid}.html")
    public String getStoreInfo(Model model,@PathVariable Long storeid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据storeId查找数据对象信息
        predictMmStores MmStores= StoreManagementRepository.findOne(storeid);//查找存储设备数据
        model.addAttribute("store",MmStores);
        return "storeManagementView";
    }
    //获取监控展示页面列表
    @RequestMapping(value = "/getStoreShow")
    public String getStoreShow(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "storeShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getStoreShowView/{hostname}/{hostid}")
    public String getStoreShowView(Model model,@PathVariable  String hostname,String hostid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("storeHostname",hostname);
        model.addAttribute("storeHostname",hostid);
        return "storeShowView";
    }
}
