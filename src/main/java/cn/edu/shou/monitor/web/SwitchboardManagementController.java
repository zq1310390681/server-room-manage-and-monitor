package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmSwitchboards;
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
@RequestMapping(value = "/predictCenter/switchboards")
public class SwitchboardManagementController {
    @Autowired
    cn.edu.shou.monitor.service.SwitchboardManagementRepository SwitchboardManagementRepository;
    @Autowired
    PredictMmURepository uRepository;
    @Autowired
    PredictMmBrandRepository brandRepository;
    @Autowired
    PredictMmEquipmentCabinetRepository cabinetRepository;
    @RequestMapping(value = "/getSwitchboardInfo")
    public String getSwitchboardInfo(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "switchboardManagement";
    }
    @RequestMapping(value = "/getSwitchboardViewInfo/{switchboardid}.html")
      public String getSwitchboardViewInfo(Model model,@PathVariable Long switchboardid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据switchboardId查找数据对象信息
        predictMmSwitchboards mmSwitchboard= SwitchboardManagementRepository.findOne(switchboardid);//查找switchboard数据
        //显示机柜名称
        String cabinetName = "";
        if (mmSwitchboard!=null){
            cabinetName = cabinetRepository.findOne(Long.parseLong(mmSwitchboard.getSwitchboardEquipmentCabinet())).getEquipmentCabinetName();
            mmSwitchboard.setSwitchboardEquipmentCabinet(cabinetName);
        }
        //显示所在U名称
       /* String uName="";
        if (mmSwitchboard!=null){
            uName = uRepository.findOne(Long.parseLong(mmSwitchboard.getSwitchboardU())).getuName();
            mmSwitchboard.setSwitchboardU(uName);
        }*/
        //显示品牌名称
        String brandName = "";
        if (mmSwitchboard != null){
            brandName = brandRepository.findOne(Long.parseLong(mmSwitchboard.getSwitchboardBrand())).getBrandName();
            mmSwitchboard.setSwitchboardBrand(brandName);
        }
        model.addAttribute("switchboard",mmSwitchboard);
        return "switchboardManagementView";
    }
    @RequestMapping(value = "/getSwitchboardQrCode/{switchid}.html")
    public String getSwitchboardQrCode(Model model,@PathVariable Long switchid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据switchid查找数据对象信息
        predictMmSwitchboards mmSwitchboard= SwitchboardManagementRepository.findOne(switchid);//查找switchboard数据
        //显示机柜名称
        String cabinetName = "";
        if (mmSwitchboard!=null){
            cabinetName = cabinetRepository.findOne(Long.parseLong(mmSwitchboard.getSwitchboardEquipmentCabinet())).getEquipmentCabinetName();
            mmSwitchboard.setSwitchboardEquipmentCabinet(cabinetName);
        }
        //显示所在U名称
       /* String uName="";
        if (mmSwitchboard!=null){
            uName = uRepository.findOne(Long.parseLong(mmSwitchboard.getSwitchboardU())).getuName();
            mmSwitchboard.setSwitchboardU(uName);
        }*/
        //显示品牌名称
        String brandName = "";
        if (mmSwitchboard != null){
            brandName = brandRepository.findOne(Long.parseLong(mmSwitchboard.getSwitchboardBrand())).getBrandName();
            mmSwitchboard.setSwitchboardBrand(brandName);
        }
        model.addAttribute("switchboard",mmSwitchboard);
        return "switchQrCode";
    }
    //获取监控展示页面列表
    @RequestMapping(value = "/getSwitchShow")
    public String getSwitchShow(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "switchShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getSwitchShowView/{hostname}/{hostid}")
    public String getSwitchShowView(Model model,@PathVariable  String hostname,String hostid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("switchHostname",hostname);
        model.addAttribute("hostid",hostid);
        return "switchShowView";
    }
}
