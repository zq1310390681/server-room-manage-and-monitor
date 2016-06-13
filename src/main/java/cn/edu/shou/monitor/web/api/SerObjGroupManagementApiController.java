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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //判断组序号与组名称是否已经存在
    @RequestMapping(value = "/serObjGroupExit/{groupOrder}/{groupName}")
    public Map<String,Boolean> serObjGroupExit(@PathVariable String groupOrder,@PathVariable String groupName){
        PredictMmServiceObjGroup serviceObjGroup=new PredictMmServiceObjGroup();
        Map<String,Boolean>results = new HashMap<String,Boolean>();
        boolean orderExit=false;//组序号是否存在
        boolean groupNameExit = false; //组名称是否存在
        serviceObjGroup = serObjGroupManagementRepository.getSerObjByOrder(Long.parseLong(groupOrder));
        if (serviceObjGroup == null){
            orderExit = true;
            results.put("order",orderExit);
        }else {
            results.put("order",false);
        }
        //重新加载组名称
        serviceObjGroup = serObjGroupManagementRepository.getSerObjByName(groupName);
        if (serviceObjGroup ==null){
            groupNameExit = true;
            results.put("groupName",groupNameExit);
        }else {
            results.put("groupName",false);
        }
        return results;
    }
}
