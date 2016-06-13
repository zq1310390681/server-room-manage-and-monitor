package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmHostForm;
import cn.edu.shou.monitor.domain.predictMmApplications;
import cn.edu.shou.monitor.domain.predictMmHost;
import cn.edu.shou.monitor.domain.predictMmOperatingSystem;
import cn.edu.shou.monitor.domain.predictMmServers;
import cn.edu.shou.monitor.service.*;
import cn.edu.shou.monitor.service.impl.ZbxHostServiceImpl;
import cn.edu.shou.monitor.util.ChineseCharToAbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/25.
 */
@RestController
@RequestMapping(value ="/predictCenter/api/host" )
public class HostManagementApiController {
    @Autowired
    HostManagementRepository hostManagementRepository;
    @Autowired
    ServerManagementRepository serverManagementRepository;
    @Autowired
    OperatingSystemRepository operatingSystemRepository;
    @Autowired
    ApplicationManagementRepository applicationManagementRepository;
    @Autowired
    HostTypeRepository hostTypeRepository;
    //获取所有模型数据信息
    @RequestMapping(value = "/getAllHost")
    public List<predictMmHost> getAllHost() {
        return hostManagementRepository.findAll();
    }
    //根据ID集合获取主机信息   应用显示主机列表  top结构使用
    @RequestMapping(value = "/getHostsByIds/{ids}")
    public List<predictMmHost>getHostsByIds(@PathVariable String ids){
        //使显示主机类型名称，而不是编号
        List <predictMmHost> hostes = hostManagementRepository.findAll();
        if (hostes!=null){
            for (predictMmHost host1:hostes){
                String hostTypeId = host1.getHostType();
                if (hostTypeId != null){
                    hostTypeId = hostTypeRepository.findOne(Long.parseLong(hostTypeId))==null?"":
                            hostTypeRepository.findOne(Long.parseLong(hostTypeId)).getHostTypeName();
                    host1.setHostType(hostTypeId);
                }
            }
        }
        List<predictMmHost> hosts=new ArrayList<predictMmHost>();
        if (ids!=null && ids!="" && !ids.contains("null")){
            String [] topRelIds = ids.split(",");//拆分拓扑主机ids
            String [] hostsIds=ids.split(",");//拆分主机IDs
            for (String id:hostsIds){
                predictMmHost host=new predictMmHost();
                List<predictMmApplications> applications=new ArrayList<predictMmApplications>();
                host=hostManagementRepository.findOne(Long.parseLong(id));
               /* List<predictMmHost> host1=new ArrayList<predictMmHost>();
                for (String relId : topRelIds){
                    host1 = hostManagementRepository.getHostByTopId("%"+host.getId()+"%");
                }*/
                //根据hostId获取主机信息
                //获取每台主机所有的程序名
                applications=applicationManagementRepository.getApplicationsByHostId("%"+host.getId()+"%");
                String applicationContents="";
                //取出程序名
                for (predictMmApplications application:applications){
                    applicationContents+=application.getHostContent()+",";
                }
                applicationContents=applicationContents==""?"暂无数据":applicationContents.substring(0,applicationContents.length()-1);
                host.setAppName(applicationContents);

                hosts.add(host);
            }
        }
        return hosts;
    }
    //张倩 20160513 根据主机ID集合获取主机名称
    @RequestMapping(value = "/getHostsNameById/{ids}")
    public String  getHostsNameById(@PathVariable String ids){
        String  hosts="";
        if (ids!=null && ids!="" && !ids.contains("null")){
            String [] hostsIds=ids.split(",");//拆分主机IDs
            for (String id:hostsIds){
                predictMmHost host=new predictMmHost();
                host=hostManagementRepository.findOne(Long.parseLong(id));
                hosts+=host.getHosts()+",";
            }
            hosts = hosts.substring(0,hosts.length()-1);
        }
        return hosts;
    }
    //根据主机类型获得主机  hostType=1代表Vmware虚拟机， hostType=2代表物理主机
    @RequestMapping(value = "/getHost/{hostType}")
    public  List<predictMmHost> getHost(@PathVariable String hostType){
        List<predictMmHost> hosts =hostManagementRepository.getHostsByHostType(hostType);

        if(hosts!=null){
            for (predictMmHost host:hosts){
                predictMmServers serverNum=new predictMmServers();
                List<predictMmApplications> applications=new ArrayList<predictMmApplications>();

                serverNum=serverManagementRepository.findOne(Long.parseLong(host.getHostServer()));
                host.setHostServer(serverNum.getServerSerialNumber());
                //获取操作系统名称
                predictMmOperatingSystem opSys=new predictMmOperatingSystem();
                opSys = operatingSystemRepository.findOne(Long.parseLong(host.getHostOS()));
                host.setHostOS(opSys.getOperatingSystem());
                //获取每台主机所有的应用
                applications=applicationManagementRepository.getApplicationsByHostId("%"+host.getId()+"%");
                String applicationNames="";
                //取出应用名称
                for (predictMmApplications application:applications){
                    applicationNames+=application.getApplicationName()+",";
                }
                applicationNames=applicationNames==""?"暂无数据":applicationNames.substring(0,applicationNames.length()-1);
                host.setAppName(applicationNames);
            }
        }

        return hosts;
    }

    //创建主机
    @RequestMapping(value = "/createAndUpdateHost", method = RequestMethod.GET)
    public List<predictMmHost> createAndUpdateHost(predictMmHostForm hostForm) {
        long recordId = hostForm.getId();//获取记录ID
        predictMmHost predictHost = null;
        if (recordId == 0) {
            predictHost = new predictMmHost();
        } else {
            predictHost = hostManagementRepository.findOne(recordId);
        }
        predictHost.setHosts(hostForm.getHosts());
        predictHost.setHostIP(hostForm.getHostIP());
        predictHost.setHostOS(hostForm.getHostOS());
        predictHost.setHostNote(hostForm.getHostNote());
        predictHost.setHostType(hostForm.getHostType());
        predictHost.setHostServer(hostForm.getHostServer());
        predictHost.setHostPassword(hostForm.getHostPassword());
        predictHost.setHostUserName(hostForm.getHostUserName());
        predictHost.setOperational(hostForm.getOperational());
        predictHost.setVmwareUserName(hostForm.getVmwareUserName());
        if(hostForm.getHostType().equals("1")) {
            predictHost.setSMSName("虚拟机");
        }
        if(hostForm.getHostType().equals("2")) {
            predictHost.setSMSName("物理主机");
        }

        String zbxHostname = ChineseCharToAbc.getPinYin(hostForm.getHosts());
        predictHost.setZbxHostname(zbxHostname);

        String createResult = null;
        ZbxHostServiceImpl zbxHostService = new ZbxHostServiceImpl();
        List<predictMmHost> list = new ArrayList<predictMmHost>();
        if (recordId == 0) {
            createResult = zbxHostService.createHostHost(zbxHostname, hostForm.getHostIP());//添加服务
            String hostId = createResult.replaceAll("[^0-9]", "");
            predictHost.setHostId(hostId);
            if (createResult.contains("success")) {
                hostManagementRepository.save(predictHost);
                list.add(predictHost);
                return list;
            } else {
                hostManagementRepository.save(predictHost);
                list.add(predictHost);
                return list;
            }
        }
        return list;
    }
    //top关系
    @RequestMapping(value = "/createAndUpTop")
    public List<predictMmHost>createAndUpTop(predictMmHostForm hostForm){
        predictMmHost predictHost = hostManagementRepository.findOne(hostForm.getId());;
        predictHost.setTopRelation(hostForm.getTopRelation());
        List<predictMmHost> list = new ArrayList<predictMmHost>();
        hostManagementRepository.save(predictHost);
        list.add(predictHost);
        return list;
    }
    //删除虚拟机
    @RequestMapping(value = "/deleteHost/{id}")
    public List<predictMmHost> deleteHost(@PathVariable long id) {
        predictMmHost predictHost = hostManagementRepository.findOne(id);
        String hostId; //关联ZBX的hostid
        ZbxHostServiceImpl zbxHostService = new ZbxHostServiceImpl();
        hostId = predictHost.getHostId();
        zbxHostService.ZbxDeleteServer(hostId);
        hostManagementRepository.delete(predictHost);
        List<predictMmHost> list = new ArrayList<predictMmHost>();
        list.add(predictHost);
        return list;

    }

    //根据主机ID字符串获取名称
    @RequestMapping(value = "/getHostsById/{Id}")
    public String getHostsById(@PathVariable String Id) {
        String[] ids = Id.split(",");
        String Hosts = "";
        for (int i = 0; i < ids.length; ++i) {
            Hosts += hostManagementRepository.findOne(Long.parseLong(ids[i])).getHosts() + ",";
        }
        Hosts = Hosts.substring(0, Hosts.length() - 1);
        return Hosts;
    }
}
