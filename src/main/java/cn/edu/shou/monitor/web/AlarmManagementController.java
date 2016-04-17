package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmAlarm;
import cn.edu.shou.monitor.service.AlarmManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/1/13.
 */
@Controller
@RequestMapping(value = "/predictCenter")
public class AlarmManagementController {
    @Autowired
    AlarmManagementRepository alarmManagementRepository;
    //获取告警规则列表
    @RequestMapping(value = "/getAlarmInfo")
    public String getModelsInfo(Model model,@AuthenticationPrincipal User currentUser) {
        model.addAttribute("user", currentUser);
        return "alarmManagement";
    }
    @RequestMapping(value = "/getAlarmViewInfo/{alarmid}.html")
    public String getAlarmViewInfo(Model model,@PathVariable Long alarmid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据alarmid查找数据对象信息
        predictMmAlarm mmAlarm= alarmManagementRepository.findOne(alarmid);//查找告警规则数据
        model.addAttribute("alarm",mmAlarm);
        return "alarmManagementView";
    }
}
