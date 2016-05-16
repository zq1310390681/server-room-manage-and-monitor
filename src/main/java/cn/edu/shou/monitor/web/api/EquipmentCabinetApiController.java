package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmEquipmentCabinetForm;
import cn.edu.shou.monitor.domain.predictMmEquipmentCabinet;
import cn.edu.shou.monitor.service.PredictMmEquipmentCabinetRepository;
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
 * Created by sqhe18 on 2016/4/5.
 */
@RestController
@RequestMapping(value = "/predictCenter/api/cabinet")
public class EquipmentCabinetApiController {
    @Autowired
    PredictMmEquipmentCabinetRepository equipmentCabinetRepository;
    @RequestMapping(value = "/getAllCabinet")
    public List<predictMmEquipmentCabinet> getAllCabinet(){
        return equipmentCabinetRepository.findAll();
    }
    @RequestMapping(value = "/createAndUpdateCabinet",method = RequestMethod.GET)
    public List<predictMmEquipmentCabinet> createAndUpdateCabinet(predictMmEquipmentCabinetForm cabinetForm){
        long recordId = cabinetForm.getId();
        predictMmEquipmentCabinet equipmentCabinet = null;
        if (recordId == 0){
            equipmentCabinet = new predictMmEquipmentCabinet();
        }else {
            equipmentCabinet = equipmentCabinetRepository.findOne(recordId);
        }
        equipmentCabinet.setEquipmentCabinetName(cabinetForm.getEquipmentCabinetName());
        equipmentCabinet.setEquipmentCabinetUsedU(cabinetForm.getEquipmentCabinetUsedU());

        equipmentCabinetRepository.save(equipmentCabinet);
        List<predictMmEquipmentCabinet> list=new ArrayList<predictMmEquipmentCabinet>();
        list.add(equipmentCabinet);
        return list;
    }
    @RequestMapping(value = "/deleteCabinet/{id}" )
    public List<predictMmEquipmentCabinet> deleteCabinet(@PathVariable long id){
        predictMmEquipmentCabinet equipmentCabinet = equipmentCabinetRepository.findOne(id);
        equipmentCabinetRepository.delete(equipmentCabinet);
        List<predictMmEquipmentCabinet> list = new ArrayList<predictMmEquipmentCabinet>();
        list.add(equipmentCabinet);
        return list;
    }
    @RequestMapping(value = "/cabinetExit/{cabinet}")
    public Map<String,Boolean>cabinetExit(@PathVariable String cabinet){
        predictMmEquipmentCabinet equCabinet = new predictMmEquipmentCabinet();
        Map<String,Boolean> results = new HashMap<String ,Boolean>();
        boolean cabinetExit = false;// 机柜是否存在
        equCabinet = equipmentCabinetRepository.getCabByName(cabinet);
        if(equCabinet == null){
            cabinetExit=true;
            results.put("cabinet",cabinetExit);
        }else{
            results.put("cabinet",false);
        }
        return results;
    }
}
