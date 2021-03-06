package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmMiddle;
import cn.edu.shou.monitor.service.HostManagementRepository;
import cn.edu.shou.monitor.service.MiddleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/1/2.
 */
@Controller
@RequestMapping(value = "/predictCenter")
public class SqlserverManagementController {
    @Autowired
    MiddleRepository middleRepository;
    @Autowired
    HostManagementRepository hostManagementRepository;
    //获取SQL Server数据库列表
    @RequestMapping(value = "/getSqlserverInfo")
    public String getModelsInfo(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "sqlserverManagement";
    }
    @RequestMapping(value = "/getSqlserverView/{sqlserverid}")
    public String getSqlserverView(Model model,@PathVariable Long sqlserverid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据sqlserverid查找数据对象信息
        predictMmMiddle mmSqlserver=middleRepository.findOne(sqlserverid);//查找SQLserver数据库数据
        String hostName="";
        if(mmSqlserver!=null)
        {
            hostName = hostManagementRepository.findOne(Long.parseLong(mmSqlserver.getMiddleHost())).getHosts();
            mmSqlserver.setMiddleHost(hostName);
        }
        model.addAttribute("sqlserver",mmSqlserver);
        return "sqlserverManagementView";
    }
    //获取虚拟机展示列表
    @RequestMapping(value = "/getSqlserverShow")
    public String getSqlserverShow(Model model, @AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "sqlserverShow";
    }
    //获取监控展示详细页面
    @RequestMapping(value = "/getSqlserverShowView/{hostname}/{hostid}")
    public String getSqlserverShowView(Model model,@PathVariable String hostname,String hostid,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        model.addAttribute("sqlserverHostname",hostname);
        model.addAttribute("hostid",hostid);
        return "sqlserverShowView";
    }
}
