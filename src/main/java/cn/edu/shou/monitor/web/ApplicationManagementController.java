package cn.edu.shou.monitor.web;

import cn.edu.shou.monitor.domain.User;
import cn.edu.shou.monitor.domain.predictMmApplications;
import cn.edu.shou.monitor.service.ApplicationManagementRepository;
import cn.edu.shou.monitor.service.HostManagementRepository;
import cn.edu.shou.monitor.service.MiddleRepository;
import cn.edu.shou.monitor.service.PredictMmServiceObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@Controller
@RequestMapping(value = "/predictCenter/applications")
public class ApplicationManagementController {
    @Autowired
    ApplicationManagementRepository applicationManagementRepository;
    @Autowired
    PredictMmServiceObjectRepository serviceObjectRepository;
    @Autowired
    HostManagementRepository hostManagementRepository;
    @Autowired
    MiddleRepository middleRepository;
    @RequestMapping(value = "/getApplicationInfo")
    public String getApplicationInfo(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        return "applicationManagement";
    }
    @RequestMapping(value = "/getApplicationViewInfo/{applicationid}.html")
    public String getApplicationViewInfo(Model model,@PathVariable Long applicationid,@AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("user", currentUser);
        //根据routerId查找数据对象信息
        predictMmApplications mmApplication= applicationManagementRepository.findOne(applicationid);//查找application数据
        //服务对象
        String[]serviceObjIds = null;
        if (mmApplication.getApplicationServiceObject()!=null&&mmApplication.getApplicationServiceObject()!=""&&!mmApplication.getApplicationServiceObject().isEmpty()){
            serviceObjIds= mmApplication.getApplicationServiceObject().split(",");
        }
        //中间件
        String[]middleIds = null;
        if(mmApplication.getApplicationMiddlewareName()!="" && mmApplication.getApplicationMiddlewareName()!=null && !mmApplication.getApplicationMiddlewareName().isEmpty()){
            middleIds=mmApplication.getApplicationMiddlewareName().split(",");
        }
        //所在主机
        String [] hostIds=null;
        if(mmApplication.getApplicationHost()!=null && mmApplication.getApplicationHost()!=""&&!mmApplication.getApplicationHost().isEmpty()){
            hostIds=mmApplication.getApplicationHost().split(",");
        }

        //所在主机内容
        String [] hostContentIds=null;
        if (mmApplication.getHostContent()!=null && mmApplication.getHostContent()!=""){
            hostContentIds=mmApplication.getHostContent().split(",");
        }
        //返回前台所在主机对象
        List<predictMmApplications>hostsInfo= new ArrayList<predictMmApplications>();
        //处理中间件  显示中间件的名称
        if (middleIds!=null && middleIds.toString()!="[Ljava.lang.String;@3c7b8ce4"){
            String MiddleNames="";
            for (String id:middleIds){
                MiddleNames+=middleRepository.findOne(Long.parseLong(id)).getMiddleName()+",";
            }
            MiddleNames=MiddleNames.substring(0,MiddleNames.length()-1);//去掉最后一个逗号
            mmApplication.setApplicationMiddlewareName(MiddleNames);
        }
        //处理服务对象   显示服务对象名称
        if (serviceObjIds!=null){
            String SevriceNames="";
            for (String id:serviceObjIds){
                    SevriceNames+=serviceObjectRepository.findOne(Long.parseLong(id)).getServiceObjectName()+",";
            }
            SevriceNames=SevriceNames.substring(0,SevriceNames.length()-1);//去掉最后一个逗号
            mmApplication.setApplicationServiceObject(SevriceNames);
        }
        //处理所在主机
        if (hostIds!=null){
            for (int i=0;i<hostIds.length;++i){
                predictMmApplications applications=new predictMmApplications();
                String hostNames=hostManagementRepository.findOne(Long.parseLong(hostIds[i])).getHosts();
                if (hostContentIds!=null){
                    String hostCotent="";
                    try {
                            hostCotent=hostContentIds[i];
                            hostNames=hostNames+"-"+hostCotent;
                        }catch (Exception e){

                        }
                }
                applications.setApplicationHost(hostNames);
                hostsInfo.add(applications);
            }
        }else {
            mmApplication.setApplicationHost("暂无数据");
            hostsInfo.add(mmApplication);
        }
        model.addAttribute("app",mmApplication);
        model.addAttribute("hostsInfo",hostsInfo);
        return "applicationManagementView";
    }
    @RequestMapping(value = "/getAppShow")
    public String getAppShow(Model model,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user",currentUser);

        return  "appShow";
    }
    @RequestMapping(value = "/getAppShowView/{id}")
    public String getAppShowView(Model model,@PathVariable Long id,@AuthenticationPrincipal User currentUser){
        model.addAttribute("user", currentUser);
        predictMmApplications mmApplication=applicationManagementRepository.findOne(id);

        model.addAttribute("apps",mmApplication);
        model.addAttribute("hostIds",mmApplication.getApplicationHost());//把该应用的主机传到前端
        model.addAttribute("middleIds",mmApplication.getApplicationMiddlewareName());//把该应用的中间件传到前端
        model.addAttribute("serviceObjIds",mmApplication.getApplicationServiceObject());//把应用的服务对象传到前端
        return "appShowView";
    }
}
