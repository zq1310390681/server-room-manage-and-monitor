package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmServiceObjectForm;
import cn.edu.shou.monitor.domain.predictMmServiceObject;
import cn.edu.shou.monitor.service.PredictMmServiceObjectRepository;
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
@RequestMapping(value ="/predictCenter/api/serviceObject" )
public class ServiceObjectManagementApiController {
    @Autowired
    PredictMmServiceObjectRepository serviceObjectManagementRepository;
    //获取所有服务对象数据信息
    @RequestMapping(value = "/getAllServiceObject")
    public List<predictMmServiceObject> getAllServiceObject(){
        return serviceObjectManagementRepository.findAll();
    }
    //根据ID集合获取服务对象信息   显示在监控数据展示-》应用详细界面
    @RequestMapping(value = "/getSerObjByIds/{ids}")
    public List<predictMmServiceObject>getSerObjByIds(@PathVariable String ids){
        List<predictMmServiceObject> serObjs=new ArrayList<predictMmServiceObject>();
        if (ids!=null&&ids!=""&&!ids.contains("null")){
            String [] serviceObjIds=ids.split(",");//拆分服务对象IDS
            for (String id:serviceObjIds){
                predictMmServiceObject serviceObject=new predictMmServiceObject();
                serviceObject=serviceObjectManagementRepository.findOne(Long.parseLong(id));
                serObjs.add(serviceObject);
            }
        }
        return serObjs;
    }
    //创建服务对象
    @RequestMapping(value = "/createAndUpdateServiceObject",method = RequestMethod.GET)
    public List<predictMmServiceObject> createAndUpdateServiceObject(predictMmServiceObjectForm serviceObjectForm) {
        long recordId=serviceObjectForm.getId();//获取记录ID
        predictMmServiceObject predictServiceObject= null;
        if(recordId==0){
            predictServiceObject=new predictMmServiceObject();
        }else {
            predictServiceObject=serviceObjectManagementRepository.findOne(recordId);
        }
        predictServiceObject.setServiceObjectName(serviceObjectForm.getServiceObjectName());
        predictServiceObject.setServiceObjectIp(serviceObjectForm.getServiceObjectIp());
        predictServiceObject.setServiceObjectGroup(serviceObjectForm.getServiceObjectGroup());
        predictServiceObject.setKeySerObj(serviceObjectForm.getKeySerObj());
        serviceObjectManagementRepository.save(predictServiceObject);
        List<predictMmServiceObject> list=new ArrayList<predictMmServiceObject>();
        list.add(predictServiceObject);
        return list;
    }

    //删除服务对象
    @RequestMapping(value = "/deleteServiceObject/{id}")
    public List<predictMmServiceObject> deleteServiceObject(@PathVariable long id){
        predictMmServiceObject predictServiceObject=serviceObjectManagementRepository.findOne(id);
        serviceObjectManagementRepository.delete(predictServiceObject);
        List<predictMmServiceObject> list=new ArrayList<predictMmServiceObject>();
        list.add(predictServiceObject);
        return list;

    }
    //根据服务对象ID字符串获取名称
    @RequestMapping(value = "/getServiceObjectNamesByIds/{Id}")
    public String getServiceObjectNamesByIds(@PathVariable String Id){
        String [] ids=Id.split(",");
        String serviceName="";
        for (int i=0;i<ids.length;++i){
            serviceName+=serviceObjectManagementRepository.findOne(Long.parseLong(ids[i])).getServiceObjectName()+",";
        }
        serviceName=serviceName.substring(0,serviceName.length()-1);
        return serviceName;
    }
}
