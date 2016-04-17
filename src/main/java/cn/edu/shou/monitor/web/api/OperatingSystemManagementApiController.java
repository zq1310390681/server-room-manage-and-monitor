package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmOperatingSystemForm;
import cn.edu.shou.monitor.domain.predictMmOperatingSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/27.
 */
@RestController
@RequestMapping(value ="/predictCenter/api/operatingSystem")
public class OperatingSystemManagementApiController {
    @Autowired
    cn.edu.shou.monitor.service.OperatingSystemRepository OperatingSystemRepository;
    //获取所有操作系统数据信息
    @RequestMapping(value = "/getAllOperatingSystem")
    public List<predictMmOperatingSystem> getAllOperatingSystem(){
        return OperatingSystemRepository.findAll();
    }
    //创建和更新操作系统
    @RequestMapping(value = "/createAndUpdateOperatingSystem",method = RequestMethod.GET)
    public List<predictMmOperatingSystem> createAndUpdateOperatingSystem(predictMmOperatingSystemForm operatingSystemForm) {
        long recordId=operatingSystemForm.getId();//获取记录ID
        predictMmOperatingSystem predictOperatingSystem=null;
        if (recordId==0){
            predictOperatingSystem=new predictMmOperatingSystem();
        }else {
            predictOperatingSystem=OperatingSystemRepository.findOne(recordId);
        }
        predictOperatingSystem.setOperatingSystem(operatingSystemForm.getOperatingSystem());
        OperatingSystemRepository.save(predictOperatingSystem);
        List<predictMmOperatingSystem> list=new ArrayList<predictMmOperatingSystem>();
        list.add(predictOperatingSystem);
        return list;
    }
    //操作系统
    @RequestMapping(value = "/deleteOperatingSystem/{id}")
    public List<predictMmOperatingSystem> deleteOperatingSystem(@PathVariable long id){
        predictMmOperatingSystem predictOperatingSystem=OperatingSystemRepository.findOne(id);
        OperatingSystemRepository.delete(predictOperatingSystem);
        List<predictMmOperatingSystem> list=new ArrayList<predictMmOperatingSystem>();
        list.add(predictOperatingSystem);
        return list;

    }
}
