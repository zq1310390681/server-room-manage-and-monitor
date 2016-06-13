package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmKvmForm;
import cn.edu.shou.monitor.domain.predictMmKvm;
import cn.edu.shou.monitor.service.KvmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sqhe18 on 2016/5/9.
 */
@RestController
@RequestMapping(value ="/predictCenter/api/kvm")
public class KvmManagementApiController {
    @Autowired
    KvmRepository kvmRepository;
    @RequestMapping(value = "/getAllKvm")
    public List<predictMmKvm> getAllKvm(){
        List<predictMmKvm> list = kvmRepository.findAll();
        return list;
    }
    @RequestMapping(value = "/createAndUpdateKvm")
    public List<predictMmKvm>createAndUpdateKvm(predictMmKvmForm kvmForm){
        long recordId = kvmForm.getId();
        predictMmKvm predictKvm = null;
        if (recordId == 0){
            predictKvm = new predictMmKvm();
        }else {
            predictKvm = kvmRepository.findOne(recordId);
        }
        predictKvm.setKvmNum(kvmForm.getKvmNum());
        kvmRepository.save(predictKvm);
        List<predictMmKvm> list = new ArrayList<predictMmKvm>();
        list.add(predictKvm);
        return list;
    }
   @RequestMapping(value = "/deleteKvm/{id}")
    public List<predictMmKvm> deleteKvm(@PathVariable long id){
       predictMmKvm predictKvm = kvmRepository.findOne(id);
       kvmRepository.delete(predictKvm);
       List<predictMmKvm> list = new ArrayList<predictMmKvm>();
       list.add(predictKvm);
       return list;
   }
    //判断是否已存在
    @RequestMapping(value = "/kvmExist/{kvmName}")
    public Boolean kvmExist(@PathVariable String kvmName){
        predictMmKvm kvm= kvmRepository.getKvmByName(kvmName);//根据KVM获取对象
        boolean kvmExist=true;//KVM是否存在标示
        if (kvm==null){
            kvmExist=false;
        }
        return kvmExist;
    }
}
