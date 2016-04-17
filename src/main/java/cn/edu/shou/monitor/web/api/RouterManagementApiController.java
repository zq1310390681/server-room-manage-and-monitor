package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmRoutersForm;
import cn.edu.shou.monitor.domain.predictMmRouters;
import cn.edu.shou.monitor.service.RouterManagementRepository;
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
@RequestMapping(value ="/predictCenter/api/router" )
public class RouterManagementApiController {
    @Autowired
    RouterManagementRepository routerManagementRepository;
    //获取所有路由器数据信息
    @RequestMapping(value = "/getAllRouters")
    public List<predictMmRouters> getAllRouters(){
        return routerManagementRepository.findAll();
    }
    //创建路由器
    @RequestMapping(value = "/createAndUpdateRouter",method = RequestMethod.GET)
    public List<predictMmRouters> createRouter(predictMmRoutersForm routersForm) {

        long recordId=routersForm.getId();//获取记录ID
        predictMmRouters predictRouter=null;
        if(recordId==0){
            predictRouter=new predictMmRouters();
        }else {
            predictRouter = routerManagementRepository.findOne(recordId);
        }
        predictRouter.setRouterSN(routersForm.getRouterSN());
        predictRouter.setRouterPurchasingDate(routersForm.getRouterPurchasingDate());
        predictRouter.setRouterMaintenanceDueTime(routersForm.getRouterMaintenanceDueTime());
        predictRouter.setRouterSerialNumber(routersForm.getRouterSerialNumber());
        predictRouter.setRouterBrand(routersForm.getRouterBrand());
        predictRouter.setRouterType(routersForm.getRouterType());
        predictRouter.setRouterIP(routersForm.getRouterIP());
        predictRouter.setRouterSNMP(routersForm.getRouterSNMP());
        predictRouter.setRouterPort(routersForm.getRouterPort());
        predictRouter.setRouterEquipmentCabinet(routersForm.getRouterEquipmentCabinet());
        predictRouter.setRouterU(routersForm.getRouterU());
        predictRouter.setRouterRemark(routersForm.getRouterRemark());

        String createResult=null;
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        List<predictMmRouters> list=new ArrayList<predictMmRouters>();
        if(recordId==0) {
            createResult=zbxHostService.createHostRouter(routersForm.getRouterSerialNumber(), routersForm.getRouterIP());//添加服务
            String hostId = createResult.replaceAll("[^0-9]","");
            predictRouter.setHostId(hostId);
            if(createResult.contains("success")){
                routerManagementRepository.save(predictRouter);
                list.add(predictRouter);
                return list;
            }else {
                return list;
            }
        }
        return list;
//        routerManagementRepository.save(predictRouter);
//        List<predictMmRouters> list=new ArrayList<predictMmRouters>();
//        list.add(predictRouter);
//        return list;
    }
    //删除路由器
    @RequestMapping(value = "/deleteRouter/{id}")
    public List<predictMmRouters> deleteRouter(@PathVariable long id){
        predictMmRouters predictRouter=routerManagementRepository.findOne(id);

        String hostId; //关联ZBX的hostid
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        hostId=predictRouter.getHostId();
        zbxHostService.ZbxDeleteServer(hostId);

        routerManagementRepository.delete(predictRouter);
        List<predictMmRouters> list=new ArrayList<predictMmRouters>();
        list.add(predictRouter);
        return list;

    }
}
