package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmHostForm;
import cn.edu.shou.monitor.domain.predictMmHost;
import cn.edu.shou.monitor.service.HostManagementRepository;
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

    //获取所有模型数据信息
    @RequestMapping(value = "/getAllHost")
    public List<predictMmHost> getAllHost() {
        return hostManagementRepository.findAll();
    }

    //根据ID集合获取主机信息
    @RequestMapping(value = "/getHostsByIds/{ids}")
    public List<predictMmHost>getHostsByIds(@PathVariable String ids){
        List<predictMmHost> hosts=new ArrayList<predictMmHost>();
        if (ids!=null && ids!="" && !ids.contains("null")){
            String [] hostsIds=ids.split(",");//拆分主机IDs
            for (String id:hostsIds){
                predictMmHost host=new predictMmHost();
                host=hostManagementRepository.findOne(Long.parseLong(id));
                hosts.add(host);
            }
        }
        return hosts;
    }
    //根据主机类型获得主机  hostType=1代表Vmware虚拟机， hostType=2代表物理主机
    @RequestMapping(value = "/getHost/{hostType}")
    public  List<predictMmHost> getHost(@PathVariable String hostType){
        List hosts =hostManagementRepository.getHostsByHostType(hostType);
        return hosts;
    }

    //创建VMWare虚拟机和物理主机
    @RequestMapping(value = "/createAndUpdateHost", method = RequestMethod.GET)
    public List<predictMmHost> createAndUpdateHost(predictMmHostForm hostForm) {
        long recordId = hostForm.getId();//获取记录ID
        predictMmHost predictHost = null;
        if (recordId == 0) {
            predictHost = new predictMmHost();
        } else {
            predictHost = hostManagementRepository.findOne(recordId);
        }
        predictHost.setHosts(hostForm.getHosts());  //name
        predictHost.setHostIP(hostForm.getHostIP());
        predictHost.setHostOS(hostForm.getHostOS());
        predictHost.setHostNote(hostForm.getHostNote());
        predictHost.setHostType(hostForm.getHostType());
        predictHost.setHostServer(hostForm.getHostServer());
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
                return list;
            }
        }
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
