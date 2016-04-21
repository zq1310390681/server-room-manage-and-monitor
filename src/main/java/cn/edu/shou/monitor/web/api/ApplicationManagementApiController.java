package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmApplicationsForm;
import cn.edu.shou.monitor.domain.predictMmApplications;
import cn.edu.shou.monitor.domain.predictMmHost;
import cn.edu.shou.monitor.domain.predictMmServers;
import cn.edu.shou.monitor.domain.predictMmServiceObjectAndApplication;
import cn.edu.shou.monitor.service.*;
import cn.edu.shou.monitor.transmission.MQAsset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@RestController
@RequestMapping(value ="/predictCenter/api/application" )
public class ApplicationManagementApiController {
    @Autowired
    ApplicationManagementRepository ApplicationManagementRepository;
    @Autowired
    ServiceObjectAndApplicationRepository SoAndAppRepository;
    @Autowired
    HostManagementRepository hostRepository;
    @Autowired
    ServerManagementRepository serverRepository;
    @Autowired
    PredictMmEquipmentCabinetRepository cabinetRepository;

    //获取所有应用数据信息
    @RequestMapping(value = "/getAllApplications")
    public List<predictMmApplications> getAllApplications() {
        return ApplicationManagementRepository.findAll();
    }

    //创建应用
    @RequestMapping(value = "/createAndUpdateApplication", method = RequestMethod.GET)
    public List<predictMmApplications> createApplication(predictMmApplicationsForm applicationsForm) {
        long recordId = applicationsForm.getId();//获取记录ID
        predictMmApplications predictApplication = null;
        predictMmServiceObjectAndApplication predictSoAndApp = null;
        if (recordId == 0) {
            predictApplication = new predictMmApplications();
            predictSoAndApp = new predictMmServiceObjectAndApplication();
        } else {
            predictApplication = ApplicationManagementRepository.findOne(recordId);
            predictSoAndApp = SoAndAppRepository.getServerObjAndApp(recordId);//获取中间对象
        }
        predictApplication.setApplicationName(applicationsForm.getApplicationName());
        predictApplication.setAppGroup(applicationsForm.getAppGroup());
        predictApplication.setApplicationServiceObject(applicationsForm.getApplicationServiceObject());
        predictApplication.setApplicationHost(applicationsForm.getApplicationHost()); // 所在主机id
        predictApplication.setApplicationMiddlewareName(applicationsForm.getApplicationMiddlewareName());

        String[] predictHostId = applicationsForm.getApplicationHost().split(",");
        predictMmHost predictHost;
        List<String> nameList = new ArrayList<>();
        List<String> hostidList = new ArrayList<>();
        for (String str : predictHostId) {
            long id = Long.valueOf(str);
            predictHost = hostRepository.findOne(id);
            String nameStr = predictHost.getHosts();
            String idStr = predictHost.getHostId();
            nameList.add(nameStr);
            hostidList.add(idStr);
        }
        predictApplication.setHostName(nameList.toString().replace("[", "").replace("]", ""));
        predictApplication.setHostid(hostidList.toString().replace("[", "").replace("]", ""));


        if (applicationsForm.getHostContent().isEmpty()) {
            predictApplication.setHostContent("");//主机内容
        } else {
            predictApplication.setHostContent(applicationsForm.getHostContent());//主机内容
        }
        ApplicationManagementRepository.save(predictApplication); //保存至后台
        List<predictMmApplications> list = new ArrayList<predictMmApplications>();
        list.add(predictApplication);

        //添加中间表
        predictSoAndApp.setApplicationId(predictApplication.getId());//应用ID
        predictSoAndApp.setServiceObjectId(applicationsForm.getApplicationServiceObject());//服务对象ID
        SoAndAppRepository.save(predictSoAndApp);

        //3d 推送
        MQAsset asset = new MQAsset();
        predictMmServers predictServer;
        String appName = predictApplication.getApplicationName();
        for (String str : predictHostId) {  //predictHostId : app所在 host的 predict id
            long id = Long.valueOf(str);
            predictHost = hostRepository.findOne(id); // 得到host repository
            long predictServerId = Long.valueOf(predictHost.getHostServer()); //server id
            predictServer = serverRepository.findOne(predictServerId);

            String cabinetName = cabinetRepository.findOne(Long.parseLong(predictServer.getServerEquipmentCabinet())).getEquipmentCabinetName();
            String zbxServerHostId = predictServer.getHostId();
            asset.updApp("upd", predictServer.getId(), predictServer.getServerSerialNumber(), zbxServerHostId, predictServer.getServerSN(),
                    predictServer.getServerPurchasingDate(), predictServer.getServerMaintenanceDueTime(), predictServer.getServerBrand(),
                    predictServer.getServerType(), predictServer.getServerIP(), predictServer.getServerStorageDevice(), cabinetName,
                    predictServer.getServerU(), appName, predictServer.getServerRemark());
        }
        return list;
    }

    //删除应用
    @RequestMapping(value = "/deleteApplication/{id}")
    public List<predictMmApplications> deleteApplication(@PathVariable long id) {
        predictMmApplications predictApplication = ApplicationManagementRepository.findOne(id);//删除应用表数据
        ApplicationManagementRepository.delete(predictApplication);
        predictMmServiceObjectAndApplication predictSoAndApp = SoAndAppRepository.getServerObjAndApp(id);//删除中间表数据
        SoAndAppRepository.delete(predictSoAndApp);
        List<predictMmApplications> list = new ArrayList<predictMmApplications>();
        list.add(predictApplication);
        return list;

    }
}
