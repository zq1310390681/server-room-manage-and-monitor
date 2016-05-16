package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmRouters;
import cn.edu.shou.monitor.service.PredictMmBrandRepository;
import cn.edu.shou.monitor.service.PredictMmEquipmentCabinetRepository;
import cn.edu.shou.monitor.service.PredictMmURepository;
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
    @Autowired
    PredictMmURepository uRepository;
    @Autowired
    PredictMmBrandRepository brandRepository;
    @Autowired
    PredictMmEquipmentCabinetRepository cabinetRepository;
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
        //显示机柜名称
        String cabinetName = "";
        if (mmRouter!=null){
            cabinetName = cabinetRepository.findOne(Long.parseLong(mmRouter.getRouterEquipmentCabinet())).getEquipmentCabinetName();
            mmRouter.setRouterEquipmentCabinet(cabinetName);
        }
        //显示所在U名称
        /*String uName="";
        if (mmRouter!=null){
            uName = uRepository.findOne(Long.parseLong(mmRouter.getRouterU())).getuName();
            mmRouter.setRouterU(uName);
        }*/
        //显示品牌名称
        String brandName = "";
        if (mmRouter != null){
            brandName = brandRepository.findOne(Long.parseLong(mmRouter.getRouterBrand())).getBrandName();
            mmRouter.setRouterBrand(brandName);
        }
        model.addAttribute("router",mmRouter);
        return "routerManagementView";
    }
    @RequestMapping(value = "/getRouterQrCode/{routerids}.html")
    public String getRouterQrCode(Model model,@PathVariable Long routerids,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据routerId查找数据对象信息
        predictMmRouters mmRouter= RouterManagementRepository.findOne(routerids);//查找router数据
        //显示机柜名称
        String cabinetName = "";
        if (mmRouter!=null){
            cabinetName = cabinetRepository.findOne(Long.parseLong(mmRouter.getRouterEquipmentCabinet())).getEquipmentCabinetName();
            mmRouter.setRouterEquipmentCabinet(cabinetName);
        }
        //显示所在U名称
       /* String uName="";
        if (mmRouter!=null){
            uName = uRepository.findOne(Long.parseLong(mmRouter.getRouterU())).getuName();
            mmRouter.setRouterU(uName);
        }*/
        //显示品牌名称
        String brandName = "";
        if (mmRouter != null){
            brandName = brandRepository.findOne(Long.parseLong(mmRouter.getRouterBrand())).getBrandName();
            mmRouter.setRouterBrand(brandName);
        }
        model.addAttribute("router",mmRouter);
        return "routerQrCode";
    }
    //获取监控展示页面列表
    @RequestMapping(value = "/getRouterShow")
    public String getRouterShow(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "routerShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getRouterShowView/{hostname}/{hostid}")
    public String getRouterShowView(Model model,@PathVariable  String hostname,String hostid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("routerHostname",hostname);
        model.addAttribute("hostid",hostid);
        return "routerShowView";
    }
}
