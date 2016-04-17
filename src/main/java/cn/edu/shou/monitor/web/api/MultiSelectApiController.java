package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.predictMmApplicationUser;
import cn.edu.shou.monitor.domain.predictMmHost;
import cn.edu.shou.monitor.domain.predictMmMiddle;
import cn.edu.shou.monitor.domain.predictMmServiceObject;
import cn.edu.shou.monitor.service.ApplicationUserManagementRepository;
import cn.edu.shou.monitor.service.MiddleRepository;
import cn.edu.shou.monitor.service.ServiceObjectManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2016/3/7 0007.
 */
@RestController
@RequestMapping(value ="/predictCenter/api/MultiSelect" )
public class MultiSelectApiController {
    @Autowired
    ServiceObjectManagementRepository serviceObjectManagementRepository;
    @Autowired
    ApplicationUserManagementRepository applicationUserManagementRepository;
    @Autowired
    MiddleRepository middleRepository;
    @Autowired
    cn.edu.shou.monitor.service.HostManagementRepository HostManagementRepository;

    //获取所有应用用户数据
    @RequestMapping(value = "/getAllApplicationUsers")
    public List<predictMmApplicationUser> getAllApplicationUsers(){
        List<predictMmApplicationUser> ApplicationUsers=applicationUserManagementRepository.findAll();
        return ApplicationUsers;
    }

    //根据Id获取应用用户数据
    @RequestMapping(value = "/getApplicationUserNameById/{id}")
    public predictMmApplicationUser getApplicationUserNameById(@PathVariable long id){
        predictMmApplicationUser ApplicationUsers=applicationUserManagementRepository.findOne(id);
        return ApplicationUsers;
    }
    //获取所有服务对象数据
    @RequestMapping(value = "/getAllServiceObjects")
    public List<predictMmServiceObject> getAllServiceObjects(){
        List<predictMmServiceObject> ServiceObjects=serviceObjectManagementRepository.findAll();
        return ServiceObjects;
    }

    //根据Id获取服务器对象数据
    @RequestMapping(value = "/getServiceObjectNameById/{id}")
    public predictMmServiceObject getServiceObjectNameById(@PathVariable long id){
        predictMmServiceObject ServiceObjects=serviceObjectManagementRepository.findOne(id);
        return ServiceObjects;
    }
    //获取所有主机数据
    @RequestMapping(value = "/getAllHosts")
    public List<predictMmHost> getAllHosts(){
        List<predictMmHost> Hosts=HostManagementRepository.findAll();
        return Hosts;
    }

    //根据Id获取主机数据
    @RequestMapping(value = "/getHostsById/{id}")
    public predictMmHost getHostById(@PathVariable long id){

        predictMmHost Hosts=HostManagementRepository.findOne(id);
        return Hosts;
    }

    //获取所有中间件数据
    @RequestMapping(value = "/getAllMiddle")
    public List<predictMmMiddle> getAllMiddle(){
        List<predictMmMiddle> Middles=middleRepository.findAll();
        return Middles;
    }
}
