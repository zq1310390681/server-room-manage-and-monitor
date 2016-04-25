package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmFws;
import cn.edu.shou.monitor.service.FwManagementRepository;
import cn.edu.shou.monitor.service.predictMmBrandRepository;
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
@RequestMapping(value = "/predictCenter/fws")
public class FwManagementController {
    @Autowired
    FwManagementRepository fwManagementRepository;
    @Autowired
    predictMmBrandRepository brandRepository;
    @RequestMapping(value = "/getFwInfo")
    public String getFwInfo(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "fwManagement";
    }
    @RequestMapping(value = "/getFwInfo/{fwid}.html")
    public String getFwInfo(Model model,@PathVariable Long fwid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据fwId查找数据对象信息
        predictMmFws MmFws= fwManagementRepository.findOne(fwid);//查找防火墙数据
        //显示品牌名称
        String brandName = "";
        if (MmFws != null){
            brandName = brandRepository.findOne(Long.parseLong(MmFws.getFwBrand())).getBrandName();
            MmFws.setFwBrand(brandName);
        }
        model.addAttribute("fw",MmFws);
            return "fwManagementView";
    }
    @RequestMapping(value = "/getFwQrCode/{fwids}.html")
    public String getFwQrCode(Model model,@PathVariable Long fwids,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据fwId查找数据对象信息
        predictMmFws MmFws= fwManagementRepository.findOne(fwids);//查找防火墙数据
        //显示品牌名称
        String brandName = "";
        if (MmFws != null){
            brandName = brandRepository.findOne(Long.parseLong(MmFws.getFwBrand())).getBrandName();
            MmFws.setFwBrand(brandName);
        }
        model.addAttribute("fw",MmFws);
        return "fwQrCode";
    }
    //获取监控展示页面列表
    @RequestMapping(value = "/getFwShow")
    public String getFwShow(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "fwShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getFwShowView/{hostname}/{hostid}")
    public String getFwShowView(Model model,@PathVariable  String hostname,String hostid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("fwHostname",hostname);
        model.addAttribute("hostid",hostid);
        return "fwShowView";
    }
}
