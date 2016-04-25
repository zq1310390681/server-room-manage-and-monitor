package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmServiceObjGroup;
import cn.edu.shou.monitor.service.SerObjGroupManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by sqhe18 on 2016/4/4.
 */
@Controller
@RequestMapping(value = "/predictCenter")
public class SerObjGroupManagementController {
    @Autowired
    SerObjGroupManagementRepository serObjGroupManagementRepository;

    //获取告警规则列表
    @RequestMapping(value = "/getSerObjGroupInfo")
    public String getSerObjGroupInfo(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute("user", currentUser);
        return "serObjGroupManagement";
    }
    @RequestMapping(value = "/getSerObjGroupView/{serObjGroupid}.html")
    public String getSerObjGroupView(Model model,@PathVariable Long serObjGroupid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据serObjGroupid查找数据对象信息
        predictMmServiceObjGroup serObjGroup=serObjGroupManagementRepository.findOne(serObjGroupid);//查找serObjGroupid数据
        model.addAttribute("serObjGroup",serObjGroup);
        return "serObjGroupManagementView";
    }
}
