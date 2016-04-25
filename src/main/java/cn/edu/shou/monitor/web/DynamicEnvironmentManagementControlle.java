package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmDynamicEnvironment;
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
public class DynamicEnvironmentManagementControlle {
    cn.edu.shou.monitor.service.DynamicEnvironmentManagementRepository DynamicEnvironmentManagementRepository;
    @RequestMapping(value = "/getDynamicEnvironmentInfo")
    public String getDynamicEnvironmentInfo(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "dynamicEnvironmentManagement";
    }
    @RequestMapping(value = "/getDynamicEnvironmentViewInfo/{dynamicEnvironmentid}.html")
    public String getDynamicEnvironmentViewInfo(Model model,@PathVariable Long dynamicEnvironmentid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据dynamicEnvironmentid查找数据对象信息
        predictMmDynamicEnvironment MmDynamicEnvironment= DynamicEnvironmentManagementRepository.findOne(dynamicEnvironmentid);//查找动力环境数据
        model.addAttribute("dynamicEnvironment",MmDynamicEnvironment);
        return "dynamicEnvironmentManagementView";
    }
    //获取虚拟机展示列表
    @RequestMapping(value = "/getEnvironShow")
    public String getEnvironShow(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "environShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getEnvironShowView/{hostname}/{hostid}")
    public String getEnvironShowView(Model model,@PathVariable String hostname,String hostid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("environHostname",hostname);
        model.addAttribute("hostid",hostid);
        return "environShowView";
    }
}
