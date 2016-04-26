package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmFwsForm;
import cn.edu.shou.monitor.domain.predictMmFws;
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
@RequestMapping(value ="/predictCenter/api/fw" )
public class FwManagementApiController {
    @Autowired
    cn.edu.shou.monitor.service.FwManagementRepository FwManagementRepository;
    //获取所有防火墙数据信息
    @RequestMapping(value = "/getAllFws")
    public List<predictMmFws> getAllFws(){
        return FwManagementRepository.findAll();
    }
    //创建防火墙
    @RequestMapping(value = "/createAndUpdateFw",method = RequestMethod.GET)
    public List<predictMmFws> createFw(predictMmFwsForm fwsForm) {
        long recordId=fwsForm.getId();//获取记录ID
        predictMmFws predictFw=null;
        if(recordId==0){
            predictFw=new predictMmFws();
        }else {
            predictFw=FwManagementRepository.findOne(recordId);
        }
        predictFw.setFwSerialNumber(fwsForm.getFwSerialNumber());
        predictFw.setFwSN(fwsForm.getFwSN());
        predictFw.setFwPurchasingDate(fwsForm.getFwPurchasingDate());
        predictFw.setFwMaintenanceDueTime(fwsForm.getFwMaintenanceDueTime());
        predictFw.setFwBrand(fwsForm.getFwBrand());
        predictFw.setFwType(fwsForm.getFwType());
        predictFw.setFwIP(fwsForm.getFwIP());
        predictFw.setFwSNMP(fwsForm.getFwSNMP());
        predictFw.setFwPort(fwsForm.getFwPort());
        predictFw.setFwRemark(fwsForm.getFwRemark());
        predictFw.setSMSName("防火墙");

        String createResult=null;
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        List<predictMmFws> list=new ArrayList<predictMmFws>();
        if(recordId==0) {
            createResult=zbxHostService.createHostFw(fwsForm.getFwSerialNumber(),fwsForm.getFwIP());//添加服务
            String hostId = createResult.replaceAll("[^0-9]","");
            predictFw.setHostId(hostId);
            if(createResult.contains("success")){
                FwManagementRepository.save(predictFw);
                list.add(predictFw);
                return list;
            }else {
                FwManagementRepository.save(predictFw);
                list.add(predictFw);
                return list;
            }
        }
        return list;
//        FwManagementRepository.save(predictFw);
//        List<predictMmFws> list=new ArrayList<predictMmFws>();
//        list.add(predictFw);
//        return list;
    }
    //删除防火墙
    @RequestMapping(value = "/deleteFw/{id}")
    public List<predictMmFws> deleteFw(@PathVariable long id){
        predictMmFws predictFw=FwManagementRepository.findOne(id);
        String hostId; //关联ZBX的hostid
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        hostId=predictFw.getHostId();
        zbxHostService.ZbxDeleteServer(hostId);
        FwManagementRepository.delete(predictFw);
        List<predictMmFws> list=new ArrayList<predictMmFws>();
        list.add(predictFw);
        return list;

    }
}
