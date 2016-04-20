package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmAlarmForm;
import cn.edu.shou.monitor.domain.PredictMmAlarm;
import cn.edu.shou.monitor.domain.predictMmEquipType;
import cn.edu.shou.monitor.service.EquipTypeRepository;
import cn.edu.shou.monitor.service.ZbxTriggerRepository;
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
    @Autowired
    EquipTypeRepository alarmInfoToSearch;
    @Autowired
    ZbxTriggerRepository zbxTrigger;

    //获取所有告警规则数据信息
    @RequestMapping(value = "/getAllAlarm")
    public List<PredictMmAlarm> getAllAlarm(){
        return AlarmManagementRepository.findAll();
    }
    //创建告警规则
    @RequestMapping(value = "/createAndUpdateAlarm",method = RequestMethod.GET)
    public List<PredictMmAlarm> createSurveillancePro(predictMmAlarmForm alarmForm) {
        long recordId=alarmForm.getId();//获取记录ID
        PredictMmAlarm predictAlarm= null;
        if(recordId==0){
            predictAlarm=new PredictMmAlarm();
            predictAlarm.setEquipTypeName(alarmForm.getEquipTypeName()); //设备名称的id
            predictAlarm.setEquipTypeElement(alarmForm.getEquipTypeElement());//告警设备监控要素 id
            predictAlarm.setAlarmThreshold(alarmForm.getAlarmThreshold());//阈值
            predictAlarm.setAlarmGrade(alarmForm.getAlarmGrade());//告警等级
            predictAlarm.setAlarmNote(alarmForm.getAlarmNote());//备注信息

            long existence = predictAlarm.getEquipTypeElement();
            if(existence !=alarmForm.getEquipTypeElement()) {
                predictMmEquipType alarmRelate = alarmInfoToSearch.findOne(alarmForm.getEquipTypeElement());
                String searchKey = alarmRelate.getZbxItemKey();
                if (alarmForm.getEquipTypeName() == 33 || alarmForm.getEquipTypeName() == 38 || alarmForm.getEquipTypeName() == 5) {   //need changes
                    zbxTrigger.updTrigger(searchKey, alarmForm.getAlarmThreshold());
                }

                AlarmManagementRepository.save(predictAlarm);
            }
            List<PredictMmAlarm> list=new ArrayList<PredictMmAlarm>();
            list.add(predictAlarm);
            return list;
        }else {
            predictAlarm=AlarmManagementRepository.findOne(recordId);
            predictAlarm.setEquipTypeName(alarmForm.getEquipTypeName()); //设备名称的id
            predictAlarm.setEquipTypeElement(alarmForm.getEquipTypeElement());//告警设备监控要素 id
            predictAlarm.setAlarmThreshold(alarmForm.getAlarmThreshold());//阈值
            predictAlarm.setAlarmGrade(alarmForm.getAlarmGrade());//告警等级
            predictAlarm.setAlarmNote(alarmForm.getAlarmNote());//备注信息

            predictMmEquipType alarmRelate = alarmInfoToSearch.findOne(alarmForm.getEquipTypeElement());
            String searchKey = alarmRelate.getZbxItemKey();
            if (alarmForm.getEquipTypeName() == 33 || alarmForm.getEquipTypeName() == 38 || alarmForm.getEquipTypeName() == 5) {   //need changes
                zbxTrigger.updTrigger(searchKey, alarmForm.getAlarmThreshold());
            }
            AlarmManagementRepository.save(predictAlarm);
            List<PredictMmAlarm> list=new ArrayList<PredictMmAlarm>();
            list.add(predictAlarm);
            return list;
        }
    }

    //删除告警规则
    @RequestMapping(value = "/deleteAlarm/{id}")
    public List<PredictMmAlarm> deleteSurveillancePro(@PathVariable long id){
        PredictMmAlarm predictAlarm=AlarmManagementRepository.findOne(id);
        AlarmManagementRepository.delete(predictAlarm);
        List<PredictMmAlarm> list=new ArrayList<PredictMmAlarm>();
        list.add(predictAlarm);
        return list;

    }
}
