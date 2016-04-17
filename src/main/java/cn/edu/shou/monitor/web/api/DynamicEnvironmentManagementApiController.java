package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmDynamicEnvironmentForm;
import cn.edu.shou.monitor.domain.predictMmDynamicEnvironment;
import cn.edu.shou.monitor.service.DynamicEnvironmentManagementRepository;
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
@RequestMapping(value ="/predictCenter/api/dynamicEnvironment" )
public class DynamicEnvironmentManagementApiController {
    @Autowired
    DynamicEnvironmentManagementRepository dynamicEnvironmentManagementRepository;
    //获取所有模型数据信息
    @RequestMapping(value = "/getAllDynamicEnvironment")
    public List<predictMmDynamicEnvironment> getAllDynamicEnvironment(){
        List<predictMmDynamicEnvironment> predictMmDynamicEnvironment=dynamicEnvironmentManagementRepository.findAll();
        return predictMmDynamicEnvironment;
    }
    //创建VMWare虚拟机
    @RequestMapping(value = "/createAndUpdateDynamicEnvironment",method = RequestMethod.GET)
    public List<predictMmDynamicEnvironment> createAndUpdateDynamicEnvironment(predictMmDynamicEnvironmentForm dynamicEnvironmentForm) {
        long recordId=dynamicEnvironmentForm.getId();//获取记录ID
        predictMmDynamicEnvironment predictDynamicEnvironment= null;
        if(recordId==0){
            predictDynamicEnvironment=new predictMmDynamicEnvironment();
        }else {
            predictDynamicEnvironment=dynamicEnvironmentManagementRepository.findOne(recordId);
        }
        predictDynamicEnvironment.setEnvironmentDataSource(dynamicEnvironmentForm.getEnvironmentDataSource() ) ;
        predictDynamicEnvironment.setEnvironmentUsername(dynamicEnvironmentForm.getEnvironmentUsername());
        predictDynamicEnvironment.setEnvironmentPassword(dynamicEnvironmentForm.getEnvironmentPassword());
        dynamicEnvironmentManagementRepository.save(predictDynamicEnvironment);
        List<predictMmDynamicEnvironment> list=new ArrayList<predictMmDynamicEnvironment>();
        list.add(predictDynamicEnvironment);
        return list;
    }

    //删除虚拟机
    @RequestMapping(value = "/deleteDynamicEnvironment/{id}")
    public List<predictMmDynamicEnvironment> deleteDynamicEnvironment(@PathVariable long id){
        predictMmDynamicEnvironment predictDynamicEnvironment=dynamicEnvironmentManagementRepository.findOne(id);
        dynamicEnvironmentManagementRepository.delete(predictDynamicEnvironment);
        List<predictMmDynamicEnvironment> list=new ArrayList<predictMmDynamicEnvironment>();
        list.add(predictDynamicEnvironment);
        return list;

    }
}
