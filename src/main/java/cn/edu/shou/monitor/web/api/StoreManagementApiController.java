package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmStoresForm;
import cn.edu.shou.monitor.domain.predictMmStores;
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
@RequestMapping(value ="/predictCenter/api/store" )
public class StoreManagementApiController {
    @Autowired
    cn.edu.shou.monitor.service.StoreManagementRepository StoreManagementRepository;
    //获取所有存储设备数据信息
    @RequestMapping(value = "/getAllStores")
    public List<predictMmStores> getAllStores(){
        return StoreManagementRepository.findAll();
    }
    //创建存储设备
    @RequestMapping(value = "/createAndUpdateStore",method = RequestMethod.GET)
    public List<predictMmStores> createStore(predictMmStoresForm storesForm) {
        long recordId=storesForm.getId();//获取记录ID
        predictMmStores predictStore=null;
        if(recordId==0){
            predictStore=new predictMmStores();
        }else {
            predictStore=StoreManagementRepository.findOne(recordId);
        }
        predictStore.setStoreSN(storesForm.getStoreSN());
        predictStore.setStorePurchasingDate(storesForm.getStorePurchasingDate());
        predictStore.setStoreMaintenanceDueTime(storesForm.getStoreMaintenanceDueTime());
        predictStore.setStoreSerialNumber(storesForm.getStoreSerialNumber());
        predictStore.setStoreBrand(storesForm.getStoreBrand());
        predictStore.setStoreType(storesForm.getStoreType());
        predictStore.setStoreIP(storesForm.getStoreIP());
        predictStore.setStoreEquipmentCabinet(storesForm.getStoreEquipmentCabinet());
        predictStore.setStoreU(storesForm.getStoreU());
        predictStore.setStoreRemark(storesForm.getStoreRemark());
        predictStore.setSMSName("存储设备");

//        StoreManagementRepository.save(predictStore);
//        List<predictMmStores> list=new ArrayList<predictMmStores>();
//        list.add(predictStore);
        String createResult=null;
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        List<predictMmStores> list=new ArrayList<predictMmStores>();
        if(recordId==0) {
            createResult=zbxHostService.createHostStore(storesForm.getStoreSerialNumber(), storesForm.getStoreIP());//添加服务
            String hostId = createResult.replaceAll("[^0-9]","");
            predictStore.setHostId(hostId);
            if(createResult.contains("success")){
                StoreManagementRepository.save(predictStore);
                list.add(predictStore);
                return list;
            }else {
                StoreManagementRepository.save(predictStore);
                list.add(predictStore);
                return list;
            }
        }
        return list;
    }
    //删除存储设备
    @RequestMapping(value = "/deleteStore/{id}")
    public List<predictMmStores> deleteStore(@PathVariable long id){
        predictMmStores predictStore=StoreManagementRepository.findOne(id);

        String hostId; //关联ZBX的hostid
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        hostId=predictStore.getHostId();
        zbxHostService.ZbxDeleteServer(hostId);

        StoreManagementRepository.delete(predictStore);
        List<predictMmStores> list=new ArrayList<predictMmStores>();
        list.add(predictStore);
        return list;

    }
}
