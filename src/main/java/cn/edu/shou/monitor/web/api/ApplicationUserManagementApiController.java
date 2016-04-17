package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmApplicationUserForm;
import cn.edu.shou.monitor.domain.predictMmApplicationUser;
import cn.edu.shou.monitor.service.ApplicationUserManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/6.
 */
@RestController
@RequestMapping(value ="/predictCenter/api/applicationUser" )
public class ApplicationUserManagementApiController {
    @Autowired
    ApplicationUserManagementRepository applicationUserManagementRepository;
    //获取所有应用用户数据信息
    @RequestMapping(value = "/getAllApplicationUser")
    public List<predictMmApplicationUser> getAllApplicationUser(){
        return applicationUserManagementRepository.findAll();
    }
    //创建应用用户
    @RequestMapping(value = "/createAndUpdateApplicationUser",method = RequestMethod.GET)
    public List<predictMmApplicationUser> createAndUpdateApplicationUser(predictMmApplicationUserForm applicationUserForm) {
        long recordId=applicationUserForm.getId();//获取记录ID
        predictMmApplicationUser predictApplicationUser= null;
        if(recordId==0){
            predictApplicationUser=new predictMmApplicationUser();
        }else {
            predictApplicationUser=applicationUserManagementRepository.findOne(recordId);
        }
        predictApplicationUser.setApplicationUserCompany(applicationUserForm.getApplicationUserCompany());
        predictApplicationUser.setApplicationUserName(applicationUserForm.getApplicationUserName());
        predictApplicationUser.setApplicationUserIp(applicationUserForm.getApplicationUserIp());
        applicationUserManagementRepository.save(predictApplicationUser);
        List<predictMmApplicationUser> list=new ArrayList<predictMmApplicationUser>();
        list.add(predictApplicationUser);
        return list;
    }

    //删除应用用户
    @RequestMapping(value = "/deleteApplicationUser/{id}")
    public List<predictMmApplicationUser> deleteApplicationUser(@PathVariable long id){
        predictMmApplicationUser predictApplicationUser=applicationUserManagementRepository.findOne(id);
        applicationUserManagementRepository.delete(predictApplicationUser);
        List<predictMmApplicationUser> list=new ArrayList<predictMmApplicationUser>();
        list.add(predictApplicationUser);
        return list;

    }
    //根据应用用户ID字符串获取名称
    @RequestMapping(value = "/getApplicationUserNamesByIds/{Id}")
    public String getApplicationUserNamesByIds(@PathVariable String Id){
        String [] ids=Id.split(",");
        String applicationUserName="";
        for (int i=0;i<ids.length;++i){
            applicationUserName+=applicationUserManagementRepository.findOne(Long.parseLong(ids[i])).getApplicationUserName() +",";
        }
        applicationUserName=applicationUserName.substring(0,applicationUserName.length()-1);
        return applicationUserName;
    }
}
