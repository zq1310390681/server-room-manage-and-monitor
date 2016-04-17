package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.PredictMmServiceObjGroup;
import cn.edu.shou.monitor.domain.missiveDataForm.PredictMmServiceObjGroupForm;
import cn.edu.shou.monitor.service.SerObjGroupManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sqhe18 on 2016/4/4.
 */
@RestController
@RequestMapping(value ="/predictCenter/api/serObjGroup")
public class SerObjGroupManagementApiController {
    @Autowired
    SerObjGroupManagementRepository serObjGroupManagementRepository;
    //获取所有服务对象分组数据信息
    @RequestMapping(value = "/getAllSerObjGroup")
    public List<PredictMmServiceObjGroup> getAllSerObjGroup(){
        return serObjGroupManagementRepository.findAll();
    }
    //创建和更新服务对象分组
    @RequestMapping(value = "/createAndUpdateSerObjGroup",method = RequestMethod.GET)
    public List<PredictMmServiceObjGroup> createAndUpdateSerObjGroup(PredictMmServiceObjGroupForm serObjGroupForm) {
        long recordId=serObjGroupForm.getId();//获取记录ID
        PredictMmServiceObjGroup predictSerObjGroup= null;
        if(recordId==0){
            predictSerObjGroup=new PredictMmServiceObjGroup();
        }else {
            predictSerObjGroup=serObjGroupManagementRepository.findOne(recordId);
        }
        predictSerObjGroup.setGroupOrder(serObjGroupForm.getGroupOrder());
        predictSerObjGroup.setSerObjGroupName(serObjGroupForm.getSerObjGroupName());
        serObjGroupManagementRepository.save(predictSerObjGroup);
        List<PredictMmServiceObjGroup> list=new ArrayList<PredictMmServiceObjGroup>();
        list.add(predictSerObjGroup);
        return list;
    }

    //删除服务对象分组
    @RequestMapping(value = "/deleteSerObjGroup/{id}")
    public List<PredictMmServiceObjGroup> deleteSerObjGroup(@PathVariable long id){
        PredictMmServiceObjGroup predictSerObjGroup=serObjGroupManagementRepository.findOne(id);
        serObjGroupManagementRepository.delete(predictSerObjGroup);
        List<PredictMmServiceObjGroup> list=new ArrayList<PredictMmServiceObjGroup>();
        list.add(predictSerObjGroup);
        return list;
    }
}
