package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.transmission.MQAsset;
import cn.edu.shou.monitor.transmission.MQSendMessage;
import cn.edu.shou.monitor.domain.missiveDataForm.predictMmServersForm;
import cn.edu.shou.monitor.domain.predictMmServers;
import cn.edu.shou.monitor.service.ServerManagementRepository;
import cn.edu.shou.monitor.service.impl.ZbxHostServiceImpl;
import cn.edu.shou.monitor.service.PredictMmEquipmentCabinetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@EnableTransactionManagement
@RestController
@RequestMapping(value ="/predictCenter/api/server" )
public class ServerManagementApiController {
    @Autowired
    ServerManagementRepository ServerManagementRepository;
    @Autowired
    PredictMmEquipmentCabinetRepository pmecDAO;


    //获取所有服务器数据信息
    @RequestMapping(value = "/getAllServers")
    public List<predictMmServers> getAllServers(){
        return ServerManagementRepository.findAll();
    }
    //创建服务器
    @RequestMapping(value = "/createAndUpdateServer",method = RequestMethod.GET)
    public List<predictMmServers> createServer(predictMmServersForm serversForm) {
        long recordId=serversForm.getId();//获取记录ID
        predictMmServers predictServer;
        if(recordId==0){
            predictServer=new predictMmServers();
        }else {
            predictServer=ServerManagementRepository.findOne(recordId);
        }
        predictServer.setServerSN(serversForm.getServerSN());
        predictServer.setServerPurchasingDate(serversForm.getServerPurchasingDate());
        predictServer.setServerMaintenanceDueTime(serversForm.getServerMaintenanceDueTime());
        predictServer.setServerSerialNumber(serversForm.getServerSerialNumber()); // this val is name
        predictServer.setServerBrand(serversForm.getServerBrand());
        predictServer.setServerType(serversForm.getServerType());
        predictServer.setServerIP(serversForm.getServerIP());
        predictServer.setServerIPMI(serversForm.getServerIPMI());
        predictServer.setServerPort(serversForm.getServerPort());
        predictServer.setServerStorageDevice(serversForm.getServerStorageDevice());
        predictServer.setServerEquipmentCabinet(serversForm.getServerEquipmentCabinet());
        predictServer.setServerU(serversForm.getServerU());
        predictServer.setServerRemark(serversForm.getServerRemark());



        String createResult;
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        String cabinetName = pmecDAO.findOne(Long.parseLong(predictServer.getServerEquipmentCabinet())).getEquipmentCabinetName();
        List<predictMmServers> list=new ArrayList<predictMmServers>();
        if(recordId==0) {
            createResult = zbxHostService.createHostServer(serversForm.getServerSerialNumber(),serversForm.getServerIP(),serversForm.getServerPort());//添加server
            String hostId = createResult.replaceAll("[^0-9]","");
            predictServer.setHostId(hostId);
            if(createResult.contains("success")){
                ServerManagementRepository.save(predictServer);
                list.add(predictServer);

                MQAsset asset = new MQAsset();
                String assetMQ = asset.addAndUpdAndDelAsset("add", predictServer.getId(), predictServer.getServerSerialNumber(), hostId, predictServer.getServerSN(),
                        predictServer.getServerPurchasingDate(), predictServer.getServerMaintenanceDueTime(), predictServer.getServerBrand(),
                        predictServer.getServerType(), predictServer.getServerIP(), predictServer.getServerStorageDevice(), cabinetName,
                        predictServer.getServerU(), predictServer.getServerRemark());

                MQSendMessage.sendMessages(assetMQ, "asset"); //static 方法不需通过实例化对象
                return list;
            }else if(createResult.contains("error")){
                return list;
            }
        }else{
            // update
            MQAsset asset = new MQAsset();
//            String assetMQ = asset.addAndUpdAndDelAsset("upd", predictServer.getId(), predictServer.getServerSerialNumber(), predictServer.getHostId(),
//                    predictServer.getServerSN(), predictServer.getServerPurchasingDate(), predictServer.getServerMaintenanceDueTime(),
//                    predictServer.getServerBrand(), predictServer.getServerType(), predictServer.getServerIP(),
//                    predictServer.getServerStorageDevice(), cabinetName, predictServer.getServerU(), predictServer.getServerRemark());

//            MQSendMessage.sendMessages(assetMQ, "asset");
            ServerManagementRepository.save(predictServer);
            list.add(predictServer);
            return list;
        }
        return list;
    }
    //删除服务器
    @RequestMapping(value = "/deleteServer/{id}")
    public List<predictMmServers> deleteServer(@PathVariable long id){
        predictMmServers predictServer=ServerManagementRepository.findOne(id);

        String hostId; //关联ZBX的hostid
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        hostId=predictServer.getHostId();
        predictServer.getId();
        zbxHostService.ZbxDeleteServer(hostId);

        ServerManagementRepository.delete(predictServer);
        String cabinetName = pmecDAO.findOne(Long.parseLong(predictServer.getServerEquipmentCabinet())).getEquipmentCabinetName();
        MQAsset asset = new MQAsset();
        String assetMQ = asset.addAndUpdAndDelAsset("del", predictServer.getId(), predictServer.getServerSerialNumber(),
                hostId, predictServer.getServerSN(), predictServer.getServerPurchasingDate(), predictServer.getServerMaintenanceDueTime(),
                predictServer.getServerBrand(), predictServer.getServerType(), predictServer.getServerIP(),
                predictServer.getServerStorageDevice(), cabinetName, predictServer.getServerU(), predictServer.getServerRemark());

        MQSendMessage.sendMessages(assetMQ, "asset");
        System.out.println(assetMQ);
        List<predictMmServers> list=new ArrayList<predictMmServers>();
        list.add(predictServer);
        return list;
    }
}
