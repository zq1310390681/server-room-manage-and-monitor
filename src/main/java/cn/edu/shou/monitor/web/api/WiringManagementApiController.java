package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.PredictMmWiring;
import cn.edu.shou.monitor.domain.missiveDataForm.PredictMmWiringForm;
import cn.edu.shou.monitor.service.PredictMmWiringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by light on 2016/5/18.
 */
@RestController
@RequestMapping(value ="/predictCenter/api/wiring" )
public class WiringManagementApiController {
    @Autowired
    PredictMmWiringRepository wiringRepository;


    @RequestMapping(value = "/getAllWiring")
    public List<PredictMmWiring> getAllWiring(){
        return  wiringRepository.findAll();
    }


    @RequestMapping(value = "/createAndUpdateWiring")
    public List<PredictMmWiring> createAndUpdateWiring(PredictMmWiringForm wiringForm){
        long recordId = wiringForm.getId();//获取记录ID
        PredictMmWiring predictWiring=null;
        if(recordId==0){
            predictWiring = new PredictMmWiring();
        }else {
            predictWiring = wiringRepository.findOne(recordId);
        }
        predictWiring.setSource(wiringForm.getSource());
        predictWiring.setTarget(wiringForm.getTarget());
        predictWiring.setSourcePort(wiringForm.getSourcePort());
        predictWiring.setTargetPort(wiringForm.getTargetPort());
        predictWiring.setWiringMark(wiringForm.getWiringMark());
        predictWiring.setWiringNote(wiringForm.getWiringNote());

        List<PredictMmWiring> list = new ArrayList<>();
        wiringRepository.save(predictWiring);
        list.add(predictWiring);
        return list;
    }

    @RequestMapping(value = "/deleteSwitchboard/{id}")
    public List<PredictMmWiring> deleteSwitchboard(@PathVariable long id){
        PredictMmWiring predictWiring = wiringRepository.findOne(id);

        wiringRepository.delete(predictWiring);
        List<PredictMmWiring> list=new ArrayList<>();
        list.add(predictWiring);
        return list;
    }
}
