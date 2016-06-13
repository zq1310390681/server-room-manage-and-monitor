package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmServers;
import cn.edu.shou.monitor.service.PredictMmBrandRepository;
import cn.edu.shou.monitor.service.PredictMmEquipmentCabinetRepository;
import cn.edu.shou.monitor.service.PredictMmURepository;
import cn.edu.shou.monitor.service.StoreManagementRepository;
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
    cn.edu.shou.monitor.service.ServerManagementRepository ServerManagementRepository;
    @Autowired
    PredictMmEquipmentCabinetRepository cabinetRepository;
    @Autowired
    StoreManagementRepository storeRepository;
    @Autowired
    PredictMmURepository uRepository;
    @Autowired
    PredictMmBrandRepository brandRepository;
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
        //显示机柜名称
        String cabinetName = "";
        if (MmServers!=null){
            cabinetName = cabinetRepository.findOne(Long.parseLong(MmServers.getServerEquipmentCabinet())).getEquipmentCabinetName();
            MmServers.setServerEquipmentCabinet(cabinetName);
        }
        //显示所在U名称
       /* String uName="";
        if (MmServers!=null){
            uName = uRepository.findOne(Long.parseLong(MmServers.getServerU())).getuName();
            MmServers.setServerU(uName);
        }*/
        //显示品牌名称
        String brandName = "";
        if (MmServers != null){
            brandName = brandRepository.findOne(Long.parseLong(MmServers.getServerBrand())).getBrandName();
            MmServers.setServerBrand(brandName);
        }
        //显示存储设备名称
        String storeName = "";
        if (MmServers!=null){
            storeName = storeRepository.findOne(Long.parseLong(MmServers.getServerStorageDevice())).getStoreSerialNumber();
            MmServers.setServerSerialNumber(storeName);
        }
        model.addAttribute("server",MmServers);
        return "serverManagementView";
    }
    @RequestMapping(value = "/getServerQrCode/{serid}.html")
    public String getServerQrCode(Model model,@PathVariable Long serid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据serverId查找数据对象信息
        predictMmServers MmServers= ServerManagementRepository.findOne(serid);//查找服务器数据
        //显示机柜名称
        String cabinetName = "";
        if (MmServers!=null){
             cabinetName = cabinetRepository.findOne(Long.parseLong(MmServers.getServerEquipmentCabinet())).getEquipmentCabinetName();
             MmServers.setServerEquipmentCabinet(cabinetName);
        }
        //显示品牌名称
        String brandName = "";
        if (MmServers != null){
            brandName = brandRepository.findOne(Long.parseLong(MmServers.getServerBrand())).getBrandName();
            MmServers.setServerBrand(brandName);
        }
        //显示所在U名称
       /* String uName="";
        if (MmServers!=null){
            uName = uRepository.findOne(Long.parseLong(MmServers.getServerU())).getuName();
            MmServers.setServerU(uName);
        }*/
        //显示存储设备名称
        String storeName = "";
        if (MmServers!=null){
            storeName = storeRepository.findOne(Long.parseLong(MmServers.getServerStorageDevice())).getStoreSerialNumber();
            MmServers.setServerSerialNumber(storeName);
        }
        model.addAttribute("server",MmServers);
        return "serverQrCode";
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

