package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmOperatingSystemForm;
import cn.edu.shou.monitor.domain.predictMmOperatingSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/27.
 */
@RestController
@RequestMapping(value ="/predictCenter/api/operatingSystem")
public class OperatingSystemManagementApiController {
    @Autowired
    cn.edu.shou.monitor.service.OperatingSystemRepository operatingSystemRepository;
    //获取所有操作系统数据信息
    @RequestMapping(value = "/getAllOperatingSystem")
    public List<predictMmOperatingSystem> getAllOperatingSystem(){
        return operatingSystemRepository.findAll();
    }
    //创建和更新操作系统
    @RequestMapping(value = "/createAndUpdateOperatingSystem",method = RequestMethod.GET)
    public List<predictMmOperatingSystem> createAndUpdateOperatingSystem(predictMmOperatingSystemForm operatingSystemForm) {
        long recordId=operatingSystemForm.getId();//获取记录ID
        predictMmOperatingSystem predictOperatingSystem=null;
        if (recordId==0){
            predictOperatingSystem=new predictMmOperatingSystem();
        }else {
            predictOperatingSystem= operatingSystemRepository.findOne(recordId);
        }
        predictOperatingSystem.setOperatingSystem(operatingSystemForm.getOperatingSystem());
        operatingSystemRepository.save(predictOperatingSystem);
        List<predictMmOperatingSystem> list=new ArrayList<predictMmOperatingSystem>();
        list.add(predictOperatingSystem);
        return list;
    }
    //操作系统
    @RequestMapping(value = "/deleteOperatingSystem/{id}")
    public List<predictMmOperatingSystem> deleteOperatingSystem(@PathVariable long id){
        predictMmOperatingSystem predictOperatingSystem= operatingSystemRepository.findOne(id);
        operatingSystemRepository.delete(predictOperatingSystem);
        List<predictMmOperatingSystem> list=new ArrayList<predictMmOperatingSystem>();
        list.add(predictOperatingSystem);
        return list;
    }
    //判断操作系统是否已经存在
    @RequestMapping(value = "/osExit/{opeSys}")
    public Map<String,Boolean>osExit(@PathVariable String opeSys){
        predictMmOperatingSystem operatingSystem = new predictMmOperatingSystem();
        Map<String,Boolean> results = new HashMap<String ,Boolean>();
        boolean osExit = false; //操作系统是否存在
        operatingSystem = operatingSystemRepository.getOsByOpeSys(opeSys);
        if (operatingSystem == null){
            osExit = true;
            results.put("os",osExit);
        }else {
            results.put("os",false);
        }
        return results;
    }
}
