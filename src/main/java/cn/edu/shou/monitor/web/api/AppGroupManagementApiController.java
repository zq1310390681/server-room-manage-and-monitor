package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.PredictMmAppGroupForm;
import cn.edu.shou.monitor.domain.PredictMmAppGroup;
import cn.edu.shou.monitor.service.AppGroupManagementRepository;
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
@RequestMapping(value ="/predictCenter/api/appGroup")
public class AppGroupManagementApiController {
    @Autowired
    AppGroupManagementRepository appGroupManagementRepository;
    //获取所有应用分组数据信息
    @RequestMapping(value = "/getAllAppGroup")
    public List<PredictMmAppGroup> getAllAppGroup(){
        List<PredictMmAppGroup> PredictMmAppGroup =appGroupManagementRepository.findAll();
        return PredictMmAppGroup;
    }
    //创建和更新应用分组
    @RequestMapping(value = "/createAndUpdateAppGroup",method = RequestMethod.GET)
    public List<PredictMmAppGroup> createAndUpdateAppGroup(PredictMmAppGroupForm appGroupForm) {
        long recordId=appGroupForm.getId();//获取记录ID
        PredictMmAppGroup predictAppGroup= null;
        PredictMmAppGroup modifiedGroup=null;//被修改的分组对象
        long requstModifyOrder=appGroupForm.getGroupOrder();//需要修改的序号
        long modifiedOrder=0;//被修改的序号
        if(recordId==0){
            predictAppGroup=new PredictMmAppGroup();
        }else {
            predictAppGroup=appGroupManagementRepository.findOne(recordId);//需要修改的对象
            modifiedGroup=appGroupManagementRepository.getAppGroupByOrder(appGroupForm.getGroupOrder());
        }
        if(modifiedGroup!=null){
            modifiedGroup.setGroupOrder(predictAppGroup.getGroupOrder());
            appGroupManagementRepository.save(modifiedGroup);
        }
        predictAppGroup.setGroupOrder(appGroupForm.getGroupOrder());
        predictAppGroup.setAppGroupName(appGroupForm.getAppGroupName());
        appGroupManagementRepository.save(predictAppGroup);

        List<PredictMmAppGroup> list=new ArrayList<PredictMmAppGroup>();
        list.add(predictAppGroup);
        return list;
    }

    //删除应用分组
    @RequestMapping(value = "/deleteAppGroup/{id}")
    public List<PredictMmAppGroup> deleteAppGroup(@PathVariable long id){
        PredictMmAppGroup predictAppGroup=appGroupManagementRepository.findOne(id);
        appGroupManagementRepository.delete(predictAppGroup);
        List<PredictMmAppGroup> list=new ArrayList<PredictMmAppGroup>();
        list.add(predictAppGroup);
        return list;
    }
    //判断分组是否已存在
    @RequestMapping(value = "/appGroupExit/{orderGroup}/{groupName}")
    public Map<String,Boolean> appGroupExit(@PathVariable String orderGroup,@PathVariable String groupName){
        PredictMmAppGroup appGroup= new PredictMmAppGroup();
        Map<String,Boolean>results=new HashMap<String,Boolean>();
        boolean orderExist=false;//组序号是否存在标示
        boolean groupNameExist=false;//组名称是否存在标示
        appGroup=appGroupManagementRepository.getAppGroupByOrder(Long.parseLong(orderGroup));//根据组序号获取对象
        if (appGroup==null){
            orderExist=true;
            results.put("order",orderExist);
        }else {
            results.put("order",false);
        }
       //重新加载组对象
        appGroup=appGroupManagementRepository.getAppGroupByName(groupName);//根据组名称获取该组对象
        if (appGroup==null){
            groupNameExist=true;
            results.put("groupName",groupNameExist);
        }else {
            results.put("groupName",false);
        }
        return results;
    }
}
