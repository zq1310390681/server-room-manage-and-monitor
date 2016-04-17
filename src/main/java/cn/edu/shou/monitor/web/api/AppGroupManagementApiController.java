package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.PredictMmAppGroup;
import cn.edu.shou.monitor.domain.missiveDataForm.PredictMmAppGroupForm;
import cn.edu.shou.monitor.service.AppGroupManagementRepository;
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
@RequestMapping(value ="/predictCenter/api/appGroup")
public class AppGroupManagementApiController {
    @Autowired
    AppGroupManagementRepository appGroupManagementRepository;
    //获取所有应用分组数据信息
    @RequestMapping(value = "/getAllAppGroup")
    public List<PredictMmAppGroup> getAllAppGroup(){
        List<PredictMmAppGroup> predictMmAppGroup=appGroupManagementRepository.findAll();
        return predictMmAppGroup;
    }
    //创建和更新应用分组
    @RequestMapping(value = "/createAndUpdateAppGroup",method = RequestMethod.GET)
    public List<PredictMmAppGroup> createAndUpdateAppGroup(PredictMmAppGroupForm appGroupForm) {
        long recordId=appGroupForm.getId();//获取记录ID
        PredictMmAppGroup predictAppGroup= null;
        if(recordId==0){
            predictAppGroup=new PredictMmAppGroup();
        }else {
            predictAppGroup=appGroupManagementRepository.findOne(recordId);
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
}
