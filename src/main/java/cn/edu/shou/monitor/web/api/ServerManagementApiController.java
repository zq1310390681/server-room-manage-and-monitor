package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmServersForm;
import cn.edu.shou.monitor.domain.predictMmApplications;
import cn.edu.shou.monitor.domain.predictMmHost;
import cn.edu.shou.monitor.domain.predictMmServers;
import cn.edu.shou.monitor.service.ApplicationManagementRepository;
import cn.edu.shou.monitor.service.HostManagementRepository;
import cn.edu.shou.monitor.service.ServerManagementRepository;
import cn.edu.shou.monitor.service.impl.ZbxHostServiceImpl;
import cn.edu.shou.monitor.service.predictMmEquipmentCabinetRepository;
import cn.edu.shou.monitor.transmission.MQAsset;
import cn.edu.shou.monitor.transmission.MQSendMessage;
import cn.edu.shou.monitor.util.CSVUtils;
import cn.edu.shou.monitor.util.ClassAttributeName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@EnableTransactionManagement
@RestController
@RequestMapping(value ="/predictCenter/api/server" )
public class ServerManagementApiController {
    @Autowired
    ServerManagementRepository serverManagementRepository;
    @Autowired
    predictMmEquipmentCabinetRepository pmecDAO;
    @Autowired
    HostManagementRepository hostManagementRepository;
    @Autowired
    ApplicationManagementRepository applicationManagementRepository;


    //获取所有服务器数据信息 20160421 张倩写的 郑小罗不负责的
    @RequestMapping(value = "/getAllServers")
    public List<predictMmServers> getAllServers(){
        List<predictMmServers> serverses = serverManagementRepository.findAll();
        List<predictMmServers> results = serverses;

        if (serverses!=null){
            for (int i=0;i<serverses.size();++i){
                String appNames="";
                List<predictMmHost>hosts=hostManagementRepository.getHostBySeverId(String.valueOf(serverses.get(i).getId()));//获取到服务对应的主机信息
                for (predictMmHost host:hosts){
                    //执行查找主机对应的应用 20160421 张倩写的 郑小罗不负责的
                     List<predictMmApplications> applicationses=applicationManagementRepository.getApplicationsByHostId("%"+host.getId()+"%");
                    for (predictMmApplications app:applicationses){
                        appNames+=app.getApplicationName()+",";
                    }//end three for
                    if(appNames!=""){
                        appNames=appNames.substring(0,appNames.length()-1);//去掉最后逗号
                    }
                    results.get(i).setServerApp(appNames);
                }//end second for

            }//end first for
        }
        return results;
    }
    //创建服务器
    @RequestMapping(value = "/createAndUpdateServer",method = RequestMethod.GET)
    public List<predictMmServers> createServer(predictMmServersForm serversForm) {
        long recordId=serversForm.getId();//获取记录ID
        predictMmServers predictServer;
        if(recordId==0){
            predictServer=new predictMmServers();
        }else {
            predictServer=serverManagementRepository.findOne(recordId);
        }
        predictServer.setServerSN(serversForm.getServerSN());
        predictServer.setServerPurchasingDate(serversForm.getServerPurchasingDate());
        predictServer.setServerMaintenanceDueTime(serversForm.getServerMaintenanceDueTime());
        predictServer.setServerSerialNumber(serversForm.getServerSerialNumber());
        predictServer.setServerBrand(serversForm.getServerBrand());
        predictServer.setServerType(serversForm.getServerType());
        predictServer.setServerIP(serversForm.getServerIP());
        predictServer.setServerIPMI(serversForm.getServerIPMI());
        predictServer.setServerPort(serversForm.getServerPort());
        predictServer.setServerStorageDevice(serversForm.getServerStorageDevice());
        predictServer.setServerEquipmentCabinet(serversForm.getServerEquipmentCabinet());
        predictServer.setServerU(serversForm.getServerU());
        predictServer.setServerRemark(serversForm.getServerRemark());
        predictServer.setServerKvm(serversForm.getServerKvm());
        predictServer.setSMSName("服务器硬件");

        String createResult;
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        String cabinetName = pmecDAO.findOne(Long.parseLong(predictServer.getServerEquipmentCabinet())).getEquipmentCabinetName();
        List<predictMmServers> list=new ArrayList<predictMmServers>();
        if(recordId==0) {
            createResult = zbxHostService.createHostServer(serversForm.getServerSerialNumber(),serversForm.getServerIP(),serversForm.getServerPort());//添加server
            String hostId = createResult.replaceAll("[^0-9]","");
            predictServer.setHostId(hostId);
            if(createResult.contains("success")){
                serverManagementRepository.save(predictServer);
                list.add(predictServer);

                MQAsset asset = new MQAsset();
                String assetMQ = asset.addAndUpdAndDelAssetForServer("add", predictServer.getId(), predictServer.getServerSerialNumber(), hostId, predictServer.getServerSN(),
                        predictServer.getServerPurchasingDate(), predictServer.getServerMaintenanceDueTime(), predictServer.getServerBrand(),
                        predictServer.getServerType(), predictServer.getServerIP(), predictServer.getServerStorageDevice(), cabinetName,
                        predictServer.getServerU(), predictServer.getServerRemark(), serversForm.getServerKvm());

                MQSendMessage.sendMessages(assetMQ, "asset"); //static 方法不需通过实例化对象
                return list;
            }else if(createResult.contains("error")){
                return list;
            }
        }else{
            // update
            MQAsset asset = new MQAsset();
            String assetMQ = asset.addAndUpdAndDelAssetForServer("upd", predictServer.getId(), predictServer.getServerSerialNumber(), predictServer.getHostId(),
                    predictServer.getServerSN(), predictServer.getServerPurchasingDate(), predictServer.getServerMaintenanceDueTime(),
                    predictServer.getServerBrand(), predictServer.getServerType(), predictServer.getServerIP(),
                    predictServer.getServerStorageDevice(), cabinetName, predictServer.getServerU(), predictServer.getServerRemark(), serversForm.getServerKvm());

            MQSendMessage.sendMessages(assetMQ, "asset");
            serverManagementRepository.save(predictServer);
            list.add(predictServer);
            return list;
        }
        return list;
    }
    //删除服务器
    @RequestMapping(value = "/deleteServer/{id}")
    public List<predictMmServers> deleteServer(@PathVariable long id){
        predictMmServers predictServer=serverManagementRepository.findOne(id);

        String hostId; //关联ZBX的hostid
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        hostId=predictServer.getHostId();
        predictServer.getId();
        zbxHostService.ZbxDeleteServer(hostId);

        serverManagementRepository.delete(predictServer);
        String cabinetName = pmecDAO.findOne(Long.parseLong(predictServer.getServerEquipmentCabinet())).getEquipmentCabinetName();
        MQAsset asset = new MQAsset();
        String assetMQ = asset.addAndUpdAndDelAssetForServer("del", predictServer.getId(), predictServer.getServerSerialNumber(),
                hostId, predictServer.getServerSN(), predictServer.getServerPurchasingDate(), predictServer.getServerMaintenanceDueTime(),
                predictServer.getServerBrand(), predictServer.getServerType(), predictServer.getServerIP(),
                predictServer.getServerStorageDevice(), cabinetName, predictServer.getServerU(), predictServer.getServerRemark(), predictServer.getServerKvm());

        MQSendMessage.sendMessages(assetMQ, "asset");
        System.out.println(assetMQ);
        List<predictMmServers> list=new ArrayList<predictMmServers>();
        list.add(predictServer);
        return list;
    }


    // export csv
    @RequestMapping(value = "/import")
    public void exportCSV(){
        String[] fieldNameArr = {"服务器编号","服务器S/N号","购买时间","维保到期时间","服务器品牌","服务器型号","服务器IP","KVM",
        "Group","IPMI","存储设备","所在机柜","所在U","备注"};
//        String fieldNames = Arrays.toString(fieldNameArr);
//        List<predictMmServers> servers = serverManagementRepository.findAll();
//        List<String> serverStr = new ArrayList<>();
//        for(predictMmServers server:servers){
//            serverStr.add(server.toString());
//        }
//
//        CSVUtils exportCsv = new CSVUtils();
//        boolean isSuccess = exportCsv.exportCsv(new File("D:\\server.csv"),fieldNames,serverStr);
//
//        ClassAttributeName.testReflect(predictMmServers);

    }
}
