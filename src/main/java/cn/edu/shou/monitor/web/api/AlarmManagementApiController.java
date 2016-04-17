package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmAlarmForm;
import cn.edu.shou.monitor.domain.predictMmAlarm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
@RestController
@RequestMapping(value ="/predictCenter/api/alarm")
public class AlarmManagementApiController {
    @Autowired
    cn.edu.shou.monitor.service.AlarmManagementRepository AlarmManagementRepository;
    //获取所有告警规则数据信息
    @RequestMapping(value = "/getAllAlarm")
    public List<predictMmAlarm> getAllAlarm(){
        return AlarmManagementRepository.findAll();
    }
    //创建告警规则
    @RequestMapping(value = "/createAndUpdateAlarm",method = RequestMethod.GET)
    public List<predictMmAlarm> createSurveillancePro(predictMmAlarmForm alarmForm) {
        long recordId=alarmForm.getId();//获取记录ID
        predictMmAlarm predictAlarm= null;
        if(recordId==0){
            predictAlarm=new predictMmAlarm();
        }else {
            predictAlarm=AlarmManagementRepository.findOne(recordId);
        }
        predictAlarm.setEquipTypeName(alarmForm.getEquipTypeName());
        predictAlarm.setEquipTypeElement(alarmForm.getEquipTypeElement());
        predictAlarm.setAlarmThreshold(alarmForm.getAlarmThreshold());
        predictAlarm.setAlarmNote(alarmForm.getAlarmNote());

        AlarmManagementRepository.save(predictAlarm);
        List<predictMmAlarm> list=new ArrayList<predictMmAlarm>();
        list.add(predictAlarm);
        return list;
    }

    //删除告警规则
    @RequestMapping(value = "/deleteAlarm/{id}")
    public List<predictMmAlarm> deleteSurveillancePro(@PathVariable long id){
        predictMmAlarm predictAlarm=AlarmManagementRepository.findOne(id);
        AlarmManagementRepository.delete(predictAlarm);
        List<predictMmAlarm> list=new ArrayList<predictMmAlarm>();
        list.add(predictAlarm);
        return list;

    }
}
