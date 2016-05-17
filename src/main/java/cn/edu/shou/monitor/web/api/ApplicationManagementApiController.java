package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmApplicationsForm;
import cn.edu.shou.monitor.domain.predictMmApplications;
import cn.edu.shou.monitor.domain.predictMmHost;
import cn.edu.shou.monitor.domain.predictMmServers;
import cn.edu.shou.monitor.domain.predictMmServiceObjectAndApplication;
import cn.edu.shou.monitor.service.*;
import cn.edu.shou.monitor.web.FileOperate;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RestController
@RequestMapping(value ="/predictCenter/api/application" )
public class ApplicationManagementApiController {
    @Value("${spring.main.homedir}")
    String homedir;
    @Autowired
    cn.edu.shou.monitor.service.ApplicationManagementRepository ApplicationManagementRepository;
    @Autowired
    ServiceObjectAndApplicationRepository SoAndAppRepository;
    @Autowired
    HostManagementRepository hostRepository;
    @Autowired
    ServerManagementRepository serverRepository;
    @Autowired
    PredictMmEquipmentCabinetRepository cabinetRepository;
    @Autowired
    AppGroupManagementRepository groupManagementRepository;
    @Autowired
    ServiceObjectManagementRepository serviceObjectManagementRepository;
    @Autowired
    MiddleRepository middleRepository;
    @Autowired
    CsvUtilRepository csvUtilRepository;
    @Autowired
    ZActiveMQRepository activeMq;

    //获取所有应用数据信息
    @RequestMapping(value = "/getAllApplications")
    public List<predictMmApplications> getAllApplications(){
        List<predictMmApplications>applicationses=ApplicationManagementRepository.findAll();
        //处理应用所在组、服务对象、中间件
        if(applicationses!=null){
            for (predictMmApplications application:applicationses){
                //应用所在组
                String groupId=application.getAppGroup();//组ID
                String serviceObjId=application.getApplicationServiceObject();//服务对象ID
                String middleId=application.getApplicationMiddlewareName();//中间件ID
                //处理应用所在组
                if (groupId!=""){
                    groupId=groupManagementRepository.findOne(Long.parseLong(groupId))==null?"":
                            groupManagementRepository.findOne(Long.parseLong(groupId)).getAppGroupName();//组名称
                    groupId=groupId==""?"暂无数据":groupId;
                    application.setAppGroup(groupId);
                }
                //处理服务对象
                if (!serviceObjId.equals("")){
                    String [] serviceObjIds=serviceObjId.split(",");
                    String serviceObjNames="";//服务对象名称集合
                    for (String id:serviceObjIds){
                        String serviceObjName=serviceObjectManagementRepository.findOne(Long.parseLong(id))==null?"":
                                serviceObjectManagementRepository.findOne(Long.parseLong(id)).getServiceObjectName();//服务对象名称
                        serviceObjNames+=serviceObjName+",";
                    }
                    serviceObjNames=serviceObjNames.substring(0,serviceObjNames.length()-1);//去掉最后一个逗号
                    //serviceObjNames=serviceObjNames.length()
                    application.setApplicationServiceObject(application.getApplicationServiceObject()+"|"+serviceObjNames);
                }else {
                    application.setApplicationServiceObject("暂无数据");
                }
                //处理中间件
                if (!middleId.equals("")){
                    String[] middleIds=middleId.split(",");
                    String middleNames="";//中间件名称集合
                    for (String id:middleIds){
                        String middleName=middleRepository.findOne(Long.parseLong(id))==null?"":
                                middleRepository.findOne(Long.parseLong(id)).getMiddleName();
                        middleNames+=middleName+",";
                    }
                    middleNames=middleNames.substring(0,middleNames.length()-1);
                    application.setApplicationMiddlewareName(application.getApplicationMiddlewareName()+"|"+middleNames);
                }else {
                    application.setApplicationMiddlewareName("暂无数据");
                }
            }
        }
         return applicationses;
    }
    //创建应用
    @RequestMapping(value = "/createAndUpdateApplication",method = RequestMethod.GET)
        public predictMmApplications createApplication(predictMmApplicationsForm applicationsForm) {
        long recordId=applicationsForm.getId();//获取记录ID
        predictMmApplications predictApplication=null;
        predictMmServiceObjectAndApplication predictSoAndApp = null;
        if(recordId==0){
            predictApplication=new predictMmApplications();
            predictSoAndApp=new predictMmServiceObjectAndApplication();
        }else {
            predictApplication=ApplicationManagementRepository.findOne(recordId);
                predictSoAndApp=SoAndAppRepository.getServerObjAndApp(recordId);//获取中间对象
        }
        predictApplication.setApplicationName(applicationsForm.getApplicationName());
        predictApplication.setAppGroup(applicationsForm.getAppGroup());
        predictApplication.setApplicationServiceObject(applicationsForm.getApplicationServiceObject());
        predictApplication.setApplicationHost(applicationsForm.getApplicationHost());
        predictApplication.setApplicationMiddlewareName(applicationsForm.getApplicationMiddlewareName());
        predictApplication.setApplicationRemark(applicationsForm.getApplicationRemark());
        predictApplication.setKeyApp(applicationsForm.getKeyApp());

        String[] predictHostId = applicationsForm.getApplicationHost().split(",");
        predictMmHost predictHost ;
        List<String>  nameList = new ArrayList<>();
        List<String>  hostidList = new ArrayList<>();
        for(String str: predictHostId ){
            long id = Long.valueOf(str);
            predictHost = hostRepository.findOne(id);
            String nameStr = predictHost.getHosts();
            String idStr = predictHost.getHostId();
            nameList.add(nameStr);
            hostidList.add(idStr);
        }
        predictApplication.setHostName(nameList.toString().replace("[","").replace("]",""));
        predictApplication.setHostid(hostidList.toString().replace("[","").replace("]",""));


        if (applicationsForm.getHostContent().isEmpty()){
            predictApplication.setHostContent("");//主机内容
        }else {
            predictApplication.setHostContent(applicationsForm.getHostContent());//主机内容
        }
        ApplicationManagementRepository.save(predictApplication);
        List<predictMmApplications> list=new ArrayList<predictMmApplications>();
        list.add(predictApplication);
        //添加中间表
        predictSoAndApp.setApplicationId(predictApplication.getId());//应用ID
        predictSoAndApp.setServiceObjectId(applicationsForm.getApplicationServiceObject());//服务对象ID
        SoAndAppRepository.save(predictSoAndApp);

        //3d 推送
        predictMmServers predictServer;
        String appName = predictApplication.getApplicationName();
        for(String str: predictHostId ){  //predictHostId : app所在 host的 predict id
            long id = Long.valueOf(str);
            predictHost = hostRepository.findOne(id); // 得到host repository
            long predictServerId = Long.valueOf(predictHost.getHostServer()); //server id
            predictServer = serverRepository.findOne(predictServerId);

            String cabinetName = cabinetRepository.findOne(Long.parseLong(predictServer.getServerEquipmentCabinet())).getEquipmentCabinetName();
            String zbxServerHostId = predictServer.getHostId();
            activeMq.updApp("upd", predictServer.getId(), predictServer.getServerSerialNumber(), zbxServerHostId, predictServer.getServerSN(),
                    predictServer.getServerPurchasingDate(), predictServer.getServerMaintenanceDueTime(), predictServer.getServerBrand(),
                    predictServer.getServerType(), predictServer.getServerIP(), predictServer.getServerStorageDevice(), cabinetName,
                    predictServer.getServerU(), appName,predictServer.getServerRemark());
        }


        return predictApplication;
    }
    //删除应用
    @RequestMapping(value = "/delaeteApplication/{id}")
    public List<predictMmApplications> deleteApplication(@PathVariable long id){
        predictMmApplications predictApplication=ApplicationManagementRepository.findOne(id);//删除应用表数据
        ApplicationManagementRepository.delete(predictApplication);
        predictMmServiceObjectAndApplication predictSoAndApp=SoAndAppRepository.getServerObjAndApp(id);//删除中间表数据
        SoAndAppRepository.delete(predictSoAndApp);
        List<predictMmApplications> list=new ArrayList<predictMmApplications>();
        list.add(predictApplication);
        return list;

    }
    //数据导出
    @RequestMapping(value = "/export")
    public ResponseEntity<byte[]>  exportData(){
        String filePath=homedir+"/download/";
        String fileName="应用.csv";
        List exportDa = new ArrayList<>();
        List<predictMmApplications> applicationses = getAllApplications();//获取到需要导出的数据
        List<String>results=new ArrayList<String>();
        String header="应用名称,重点应用,应用所在组,服务对象,所在主机,子系统,中间件名称";
        for (predictMmApplications application:applicationses){
            String values="";
            if (application.getApplicationName()!=null){
                values+=application.getApplicationName().replace(",","、")+",";
            }else {
                values+=application.getApplicationName()+",";
            }
            if (application.getKeyApp()!=null){
                values+=application.getKeyApp().replace(",","、")+",";
            }else {
                values+=application.getKeyApp()+",";
            }
            if (application.getAppGroup()!=null){
                values+=application.getAppGroup().replace(",","、")+",";
            }else {
                values+=application.getAppGroup()+",";
            }
            if (application.getApplicationServiceObject()!=null){
                String appServiceObj=application.getApplicationServiceObject().replace(",","、")+",";
                int index=appServiceObj.indexOf("|");//获取到|的位置
                appServiceObj=appServiceObj.substring(index+1,appServiceObj.length());
                values+=appServiceObj;
            }else {
                values+=application.getApplicationServiceObject()+",";
            }
            if (application.getHostName()!=null){
                values+=application.getHostName().replace(",","、")+",";
            }else {
                values+=application.getHostName()+",";
            }
            if (application.getHostContent()!= null){
                values+=application.getHostContent().replace(",","、")+",";
            }else {
                values+=application.getHostContent()+",";
            }
            if (application.getApplicationMiddlewareName()!=null){
                String middleName=application.getApplicationMiddlewareName().replace(",","、")+",";
                int index = middleName.indexOf("|");
                middleName = middleName.substring(index+1,middleName.length());
                values+=middleName;
            }else {
                values+=application.getApplicationMiddlewareName();
            }
            results.add(values);
        }

        csvUtilRepository.exportCsv(new File(filePath+fileName),results,header);

        ResponseEntity<byte[]>  result = downFile("应用.csv");
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
