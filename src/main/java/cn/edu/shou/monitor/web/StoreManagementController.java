package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmStores;
import cn.edu.shou.monitor.service.predictMmBrandRepository;
import cn.edu.shou.monitor.service.predictMmEquipmentCabinetRepository;
import cn.edu.shou.monitor.service.predictMmURepository;
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
    @Autowired
    predictMmURepository uRepository;
    @Autowired
    predictMmBrandRepository brandRepository;
    @Autowired
    predictMmEquipmentCabinetRepository cabinetRepository;
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
        //显示机柜名称
        String cabinetName = "";
        if (MmStores!=null){
            if (MmStores.getStoreEquipmentCabinet()!=null&&MmStores.getStoreEquipmentCabinet()!=""){
                cabinetName = cabinetRepository.findOne(Long.parseLong(MmStores.getStoreEquipmentCabinet())).getEquipmentCabinetName();
                MmStores.setStoreEquipmentCabinet(cabinetName);
            }else{
                MmStores.setStoreEquipmentCabinet("暂无数据");
            }
        }
        //显示所在U名称
      /* String uName="";
        if (MmStores!=null){
            uName = uRepository.findOne(Long.parseLong(MmStores.getStoreU())).getuName();
            MmStores.setStoreU(uName);
        }*/
        //显示品牌名称
        String brandName = "";
        if (MmStores != null){
            brandName = brandRepository.findOne(Long.parseLong(MmStores.getStoreBrand())).getBrandName();
            MmStores.setStoreBrand(brandName);
        }
        model.addAttribute("store",MmStores);
        return "storeManagementView";
    }
    @RequestMapping(value = "/getStoreQrCode/{storeids}.html")
    public String getStoreQrCode(Model model,@PathVariable Long storeids,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据storeId查找数据对象信息
        predictMmStores MmStores= StoreManagementRepository.findOne(storeids);//查找存储设备数据
        //显示机柜名称
        String cabinetName = "";
        if (MmStores!=null){
            cabinetName = cabinetRepository.findOne(Long.parseLong(MmStores.getStoreEquipmentCabinet())).getEquipmentCabinetName();
            MmStores.setStoreEquipmentCabinet(cabinetName);
        }
        //显示所在U名称
       /* String uName="";
        if (MmStores!=null){
            uName = uRepository.findOne(Long.parseLong(MmStores.getStoreU())).getuName();
            MmStores.setStoreU(uName);
        }*/
        //显示品牌名称
        String brandName = "";
        if (MmStores != null){
            brandName = brandRepository.findOne(Long.parseLong(MmStores.getStoreBrand())).getBrandName();
            MmStores.setStoreBrand(brandName);
        }
        model.addAttribute("store",MmStores);
        return "storeQrCode";
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
        model.addAttribute("hostid",hostid);
        return "storeShowView";
    }
}
