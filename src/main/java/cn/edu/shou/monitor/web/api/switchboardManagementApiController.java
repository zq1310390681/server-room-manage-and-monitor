package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmSwitchboardsForm;
import cn.edu.shou.monitor.domain.predictMmSwitchboards;
import cn.edu.shou.monitor.service.impl.ZbxHostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@RestController
@RequestMapping(value ="/predictCenter/api/switchboard" )
public class switchboardManagementApiController {
    @Autowired
    cn.edu.shou.monitor.service.SwitchboardManagementRepository SwitchboardManagementRepository;
    //获取所有交换机数据信息
    @RequestMapping(value = "/getAllSwitchboards")
    public List<predictMmSwitchboards> getAllSwitchboards(){
        return SwitchboardManagementRepository.findAll();
    }
    //创建交换机
    @RequestMapping(value = "/createAndUpdateSwitchboard",method = RequestMethod.GET)
    public List<predictMmSwitchboards> createSwitchboard(predictMmSwitchboardsForm switchboardsForm) {
        long recordId=switchboardsForm.getId();//获取记录ID
        predictMmSwitchboards predictSwitchboard=null;
        if(recordId==0){
            predictSwitchboard=new predictMmSwitchboards();
        }else {
            predictSwitchboard=SwitchboardManagementRepository.findOne(recordId);
        }
        predictSwitchboard.setSwitchboardSN(switchboardsForm.getSwitchboardSN());
        predictSwitchboard.setSwitchboardPurchasingDate(switchboardsForm.getSwitchboardPurchasingDate());
        predictSwitchboard.setSwitchboardMaintenanceDueTime(switchboardsForm.getSwitchboardMaintenanceDueTime());
        predictSwitchboard.setSwitchboardSerialNumber(switchboardsForm.getSwitchboardSerialNumber());
        predictSwitchboard.setSwitchboardBrand(switchboardsForm.getSwitchboardBrand());
        predictSwitchboard.setSwitchboardType(switchboardsForm.getSwitchboardType());
        predictSwitchboard.setSwitchboardIP(switchboardsForm.getSwitchboardIP());
        predictSwitchboard.setSwitchboardSNMP(switchboardsForm.getSwitchboardSNMP());
        predictSwitchboard.setSwitchboardPort(switchboardsForm.getSwitchboardPort());
        predictSwitchboard.setSwitchboardEquipmentCabinet(switchboardsForm.getSwitchboardEquipmentCabinet());
        predictSwitchboard.setSwitchboardU(switchboardsForm.getSwitchboardU());
        predictSwitchboard.setSwitchboardRemark(switchboardsForm.getSwitchboardRemark());

        String createResult;
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        List<predictMmSwitchboards> list=new ArrayList<predictMmSwitchboards>();
        if(recordId==0) {
            createResult=zbxHostService.createHostSwitchboard(switchboardsForm.getSwitchboardSerialNumber(), switchboardsForm.getSwitchboardIP());//添加服务
            String hostId = createResult.replaceAll("[^0-9]","");
            predictSwitchboard.setHostId(hostId);
            if(createResult.contains("success")){
                SwitchboardManagementRepository.save(predictSwitchboard);
                list.add(predictSwitchboard);
                return list;
            }else {
                // the update
                return list;
            }
        }
        return list;
    }
    //删除交换机
    @RequestMapping(value = "/deleteSwitchboard/{id}")
    public List<predictMmSwitchboards> deleteSwitchboard(@PathVariable long id){
        predictMmSwitchboards predictSwitchboard=SwitchboardManagementRepository.findOne(id);

        String hostId; //关联ZBX的hostid
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        hostId=predictSwitchboard.getHostId();
        zbxHostService.ZbxDeleteServer(hostId);

        SwitchboardManagementRepository.delete(predictSwitchboard);
        List<predictMmSwitchboards> list=new ArrayList<predictMmSwitchboards>();
        list.add(predictSwitchboard);
        return list;

    }
}