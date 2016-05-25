package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmServersForm;
import cn.edu.shou.monitor.domain.predictMmApplications;
import cn.edu.shou.monitor.domain.predictMmHost;
import cn.edu.shou.monitor.domain.predictMmServers;
import cn.edu.shou.monitor.service.*;
import cn.edu.shou.monitor.service.impl.ZbxHostServiceImpl;
import cn.edu.shou.monitor.web.FileOperate;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@EnableTransactionManagement
@RestController
@RequestMapping(value ="/predictCenter/api/server" )
public class ServerManagementApiController {
    @Value("${spring.main.homedir}")
    String homedir;
    @Autowired
    ServerManagementRepository serverManagementRepository;
    @Autowired
    PredictMmEquipmentCabinetRepository pmecDAO;
    @Autowired
    HostManagementRepository hostManagementRepository;
    @Autowired
    ApplicationManagementRepository applicationManagementRepository;
    @Autowired
    CsvUtilRepository csvUtilRepository;
    @Autowired
    PredictMmBrandRepository brandRepository;
    @Autowired
    StoreManagementRepository storeManagementRepository;
    @Autowired
    ZActiveMQRepository activeMq;
    @Autowired
    KvmRepository kvmRepository;

    //获取所有服务器数据信息
    @RequestMapping(value = "/getAllServers")
    public List<predictMmServers> getAllServers(){
        List<predictMmServers> serverses =serverManagementRepository.findAll();
        if (serverses != null){
            for (predictMmServers servers : serverses){
                //处理品牌
                String brandId = servers.getServerBrand();
                if (brandId != null){
                    brandId = brandRepository.findOne(Long.parseLong(brandId))==null?"":
                            brandRepository.findOne(Long.parseLong(brandId)).getBrandName();
                    brandId = brandId == ""?"暂无数据":brandId;
                    servers.setServerBrand(brandId);
                }
                //处理存储设备
                String storeId = servers.getServerStorageDevice();
                if (storeId != null){
                    storeId = storeManagementRepository.findOne(Long.parseLong(storeId))==null?"":
                            storeManagementRepository.findOne(Long.parseLong(storeId)).getStoreSerialNumber();
                    storeId = storeId == ""?"暂无数据":storeId;
                    servers.setServerStorageDevice(storeId);
                }
                //处理机柜
                String cabinetId = servers.getServerEquipmentCabinet();
                if (cabinetId != null){
                    cabinetId = pmecDAO.findOne(Long.parseLong(cabinetId)) == null ? "":
                            pmecDAO.findOne(Long.parseLong(cabinetId)).getEquipmentCabinetName();
                    cabinetId = cabinetId == ""?"暂无数据":cabinetId;
                    servers.setServerEquipmentCabinet(cabinetId);
                }
                //处理KVM编号
                String kvmId = servers.getServerKvm();
                if (kvmId != null){
                    kvmId = kvmRepository.findOne(Long.parseLong(kvmId)) == null ?"":
                            kvmRepository.findOne(Long.parseLong(kvmId)).getKvmNum();
                    kvmId = kvmId == ""?"暂无数据":kvmId;
                    servers.setServerKvm(kvmId);
                }
            }
        }

        List<predictMmServers> results =serverses;

        if (serverses!=null){
            for (int i=0;i<serverses.size();++i){
                String appNames="";
                List<predictMmHost>hosts=hostManagementRepository.getHostBySeverId(String.valueOf(serverses.get(i).getId()));//获取到服务对应的主机信息
                for (predictMmHost host:hosts){
                    //执行查找主机对应的应用
                     List<predictMmApplications> applicationses=applicationManagementRepository.getApplicationsByHostId("%"+host.getId()+"%");
                    for (predictMmApplications app:applicationses){
                        appNames+=app.getApplicationName()+",";
                    }//end three for
                    if(!appNames.equals("") &&appNames.length()>0){
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
        predictServer.setLastModifiedDate(DateTime.now());
        predictServer.setSMSName("服务器硬件");

        String createResult;
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        String cabinetName = pmecDAO.findOne(Long.parseLong(predictServer.getServerEquipmentCabinet())).getEquipmentCabinetName();
        List<predictMmServers> list=new ArrayList<predictMmServers>();
        if(recordId==0) {
            createResult = zbxHostService.createHostServer(serversForm.getServerSerialNumber(),serversForm.getServerIP(),serversForm.getServerPort());//添加server
            if(createResult.contains("success")){
                String hostId = createResult.replaceAll("[^0-9]","");
                predictServer.setHostId(hostId);
                serverManagementRepository.save(predictServer);
                list.add(predictServer);

                activeMq.addAndUpdAndDelAssetForServer("add", predictServer.getId(), predictServer.getServerSerialNumber(), hostId, predictServer.getServerSN(),
                        predictServer.getServerPurchasingDate(), predictServer.getServerMaintenanceDueTime(), predictServer.getServerBrand(),
                        predictServer.getServerType(), predictServer.getServerIP(), predictServer.getServerStorageDevice(), cabinetName,
                        predictServer.getServerU(), predictServer.getServerRemark(), serversForm.getServerKvm());

                return list;
            }else if(createResult.contains("error")){
                return list;
            }
        }else{
            // update
            activeMq.addAndUpdAndDelAssetForServer("upd", predictServer.getId(), predictServer.getServerSerialNumber(), predictServer.getHostId(),
                    predictServer.getServerSN(), predictServer.getServerPurchasingDate(), predictServer.getServerMaintenanceDueTime(),
                    predictServer.getServerBrand(), predictServer.getServerType(), predictServer.getServerIP(),
                    predictServer.getServerStorageDevice(), cabinetName, predictServer.getServerU(), predictServer.getServerRemark(), serversForm.getServerKvm());

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

        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        predictServer.getId();
        zbxHostService.ZbxDeleteServer(predictServer.getHostId());

        serverManagementRepository.delete(predictServer);
        String cabinetName = pmecDAO.findOne(Long.parseLong(predictServer.getServerEquipmentCabinet())).getEquipmentCabinetName();
        activeMq.addAndUpdAndDelAssetForServer("del", predictServer.getId(), predictServer.getServerSerialNumber(),
                predictServer.getHostId(), predictServer.getServerSN(), predictServer.getServerPurchasingDate(), predictServer.getServerMaintenanceDueTime(),
                predictServer.getServerBrand(), predictServer.getServerType(), predictServer.getServerIP(),
                predictServer.getServerStorageDevice(), cabinetName, predictServer.getServerU(), predictServer.getServerRemark(), predictServer.getServerKvm());

        List<predictMmServers> list=new ArrayList<predictMmServers>();
        list.add(predictServer);
        return list;
    }

    //数据导出
    @RequestMapping(value = "/export")
    public ResponseEntity<byte[]> exportData(){
        String filePath=homedir+"/download/";
        String fileName="服务器硬件.csv";
        List exportDa = new ArrayList<>();
        List<predictMmServers> serverses = getAllServers();//获取到需要导出的数据
        List<String>results=new ArrayList<String>();
        String header="服务器编号,服务器IP,服务器S/N号,服务器品牌,KVM编号,购买时间,维保到期时间,服务器型号,IPMI,端口号,存储设备,所在机柜,所在U,备注";
        for (predictMmServers servers:serverses){
            String values="";
            if (servers.getServerSerialNumber()!=null){
                values+=servers.getServerSerialNumber().replace(",","、")+",";
            }else {
                values+=servers.getServerSerialNumber()+",";
            }
            if (servers.getServerIP()!=null){
                values+=servers.getServerIP().replace(",","、")+",";
            }else {
                values+=servers.getServerIP()+",";
            }
            if (servers.getServerSN()!=null){
                values+=servers.getServerSN().replace(",","、")+",";
            }else {
                values+=servers.getServerSN()+",";
            }
            if (servers.getServerBrand()!=null){
                values+=servers.getServerBrand().replace(",","、")+",";
            }else {
                values+=servers.getServerBrand()+",";
            }
            if (servers.getServerKvm()!=null){
                values+=servers.getServerKvm().replace(",","、")+",";
            }else {
                values+=servers.getServerKvm()+",";
            }
            if (servers.getServerPurchasingDate()!= null){
                values+=servers.getServerPurchasingDate().replace(",","、")+",";
            }else {
                values+=servers.getServerPurchasingDate()+",";
            }
            if (servers.getServerMaintenanceDueTime()!=null){
                values+=servers.getServerMaintenanceDueTime().replace(",","、")+",";
            }else {
                values+=servers.getServerMaintenanceDueTime();
            }
            if (servers.getServerType()!=null){
                values+=servers.getServerType().replace(",","、")+",";
            }else {
                values+=servers.getServerType();
            }
            if (servers.getServerIPMI()!=null){
                values+=servers.getServerIPMI().replace(",","、")+",";
            }else {
                values+=servers.getServerIPMI();
            }
            if (servers.getServerPort()!=null){
                values+=servers.getServerPort().replace(",","、")+",";
            }else {
                values+=servers.getServerPort();
            }
            if (servers.getServerStorageDevice()!=null){
                values+=servers.getServerStorageDevice().replace(",","、")+",";
            }else {
                values+=servers.getServerStorageDevice();
            }
            if (servers.getServerEquipmentCabinet()!=null){
                values+=servers.getServerEquipmentCabinet().replace(",","、")+",";
            }else {
                values+=servers.getServerEquipmentCabinet();
            }
            if (servers.getServerU()!=null){
                values+=" "+servers.getServerU().replace(",","、")+",";
              }else {
                values+=" "+servers.getServerU();
            }
            if (servers.getServerRemark()!=null){
                values+=servers.getServerRemark().replace(",","、")+",";
            }else {
                values+=servers.getServerRemark();
            }
            results.add(values);
        }
        csvUtilRepository.exportCsv(new File(filePath+fileName),results,header);
        ResponseEntity<byte[]>  result = downFile("服务器硬件.csv");
        return result;
    }
    public ResponseEntity<byte[]> downFile(String fileName)  {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //String path = req.getSession().getServletContext().getRealPath("/");
        String filePath=homedir+"/download/"+fileName;//文件路径
        try {
            //filePath = URLEncoder.encode(filePath, "UTF-8");//转码解决IE下文件名乱码问题
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Http响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", filePath);
        if (!FileOperate.exitFile(filePath)){
            return null;
        }
        try {
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(filePath)),
                    headers,
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            //日志
        }
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "error.txt");
        return new ResponseEntity<byte[]>("文件不存在.".getBytes(), headers, HttpStatus.OK);
    }
}
