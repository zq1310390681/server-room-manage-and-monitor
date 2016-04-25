package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmAlarmForm;
import cn.edu.shou.monitor.domain.predictMmAlarm;
import cn.edu.shou.monitor.domain.predictMmEquipType;
import cn.edu.shou.monitor.service.AlarmManagementRepository;
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
    AlarmManagementRepository alarmManagementRepository;
    @Autowired
    EquipTypeRepository alarmInfoToSearch;
    @Autowired
    ZbxTriggerRepository zbxTrigger;

    //获取所有告警规则数据信息
    @RequestMapping(value = "/getAllAlarm")
    public List<predictMmAlarm> getAllAlarm(){
        return alarmManagementRepository.findAll();
    }
    //创建告警规则
    @RequestMapping(value = "/createAndUpdateAlarm",method = RequestMethod.GET)
    public List<predictMmAlarm> createSurveillancePro(predictMmAlarmForm alarmForm) {
        long recordId=alarmForm.getId();//获取记录ID
        predictMmAlarm predictAlarm= null;
        if(recordId==0){
            predictAlarm=new predictMmAlarm();

        }else {
            predictAlarm=alarmManagementRepository.findOne(recordId);
//            predictAlarm.setEquipTypeName(alarmForm.getEquipTypeName()); //设备名称的id
//            predictAlarm.setEquipTypeElement(alarmForm.getEquipTypeElement());//告警设备监控要素 id
//            predictAlarm.setAlarmThreshold(alarmForm.getAlarmThreshold());//阈值
//            predictAlarm.setAlarmGrade(alarmForm.getAlarmGrade());//告警等级
//            predictAlarm.setAlarmNote(alarmForm.getAlarmNote());//备注信息
//
//            String name = alarmInfoToSearch.findOne(alarmForm.getEquipTypeName()).getEquipTypeName();
//            predictMmEquipType alarmRelate = alarmInfoToSearch.findOne(alarmForm.getEquipTypeElement());
//            String searchKey = alarmRelate.getZbxItemKey();
//            if (name.contains("虚拟机") || name.contains("操作系统") || name.contains("物理主机")) {   //need changes
//                zbxTrigger.updTrigger(searchKey, alarmForm.getAlarmThreshold());
//            }
//            alarmManagementRepository.save(predictAlarm);
//            List<PredictMmAlarm> list=new ArrayList<PredictMmAlarm>();
//            list.add(predictAlarm);
//            return list;
        }
//        predictAlarm.setEquipTypeName(alarmForm.getEquipTypeName()); //设备名称的id
//        predictAlarm.setEquipTypeElement(alarmForm.getEquipTypeElement()); //告警设备监控要素 id
//        predictAlarm.setAlarmThreshold(alarmForm.getAlarmThreshold());//阈值
//        predictAlarm.setAlarmGrade(alarmForm.getAlarmGrade());//告警等级
//        predictAlarm.setAlarmNote(alarmForm.getAlarmNote());//备注信息
//
//        String name = alarmInfoToSearch.findOne(alarmForm.getEquipTypeName()).getEquipTypeName();
//
//        //判断重复
//        long equipName =0;
//        PredictMmAlarm temp =alarmManagementRepository.getEquipName(alarmForm.getEquipTypeElement());
//        if(!temp.toString().isEmpty()&&temp.getEquipTypeName()!= alarmForm.getEquipTypeName()) {
////            equipName = temp.getEquipTypeName();
////        }
////
//////            long existence = predictAlarm.getEquipTypeElement();
////        if(equipName != alarmForm.getEquipTypeName()) {
//            predictMmEquipType alarmRelate = alarmInfoToSearch.findOne(alarmForm.getEquipTypeElement());
//            String searchKey = alarmRelate.getZbxItemKey();
//            if (name.contains("虚拟机") || name.contains("操作系统") || name.contains("物理主机")) {   //need changes
//                zbxTrigger.updTrigger(searchKey, alarmForm.getAlarmThreshold());
//            }
//
//            alarmManagementRepository.save(predictAlarm);
//        }
//        List<PredictMmAlarm> list=new ArrayList<PredictMmAlarm>();
//        list.add(predictAlarm);
//        return list;


        // 有错误，先用着
        predictAlarm.setEquipTypeName(alarmForm.getEquipTypeName()); //设备名称的id
        predictAlarm.setEquipTypeElement(alarmForm.getEquipTypeElement());//告警设备监控要素 id
        predictAlarm.setAlarmThreshold(alarmForm.getAlarmThreshold());//阈值
        predictAlarm.setAlarmGrade(alarmForm.getAlarmGrade());//告警等级
        predictAlarm.setAlarmNote(alarmForm.getAlarmNote());//备注信息

        String name = alarmInfoToSearch.findOne(alarmForm.getEquipTypeName()).getEquipTypeName();
        predictMmEquipType alarmRelate = alarmInfoToSearch.findOne(alarmForm.getEquipTypeElement());
        String searchKey = alarmRelate.getZbxItemKey();
        if (name.contains("虚拟机") || name.contains("操作系统") || name.contains("物理主机")) {   //need changes
            zbxTrigger.updTrigger(searchKey, alarmForm.getAlarmThreshold());
        }
        alarmManagementRepository.save(predictAlarm);
        List<predictMmAlarm> list=new ArrayList<predictMmAlarm>();
        list.add(predictAlarm);
        return list;
    }

    //删除告警规则
    @RequestMapping(value = "/deleteAlarm/{id}")
    public List<predictMmAlarm> deleteSurveillancePro(@PathVariable long id){
        predictMmAlarm predictAlarm = alarmManagementRepository.findOne(id);
        alarmManagementRepository.delete(predictAlarm);
        List<predictMmAlarm> list=new ArrayList<predictMmAlarm>();
        list.add(predictAlarm);
        return list;

    }
}
