package cn.edu.shou.monitor.service.impl;

import cn.edu.shou.monitor.domain.base.Host;
import cn.edu.shou.monitor.domain.base.HostGroup;
import cn.edu.shou.monitor.domain.base.HostInterface;
import cn.edu.shou.monitor.domain.base.Template;
import cn.edu.shou.monitor.service.zbxapi.IHostService;


/**
 * Created by light on 2016/3/9.
 */
public class CreateDuplicate {
    public static IHostService hostService = new HostServiceImpl();

    public String createHostEasy(String hostName, String interfaceIp, String interfacePort,String templateId,int type) {
        Host host = new Host();
        HostGroup group = new HostGroup();
        HostInterface hostInterface = new HostInterface();
        Template template = new Template();
        String createHostResponse = null;
        try {
//		host.setName("test");
            host.setHost(hostName);
            host.setStatus(0); //0 - 监控开启 1-监控关闭
            group.setGroupid("8");
//		host.setProxy_hostid("10828");
            hostInterface.setType(type); // 1 - agent; 2 - SNMP; 3 - IPMI 4 - JMX.
            hostInterface.setIp(interfaceIp);
            hostInterface.setDns("");
            hostInterface.setUseip(1); // 0 - connect using host DNS name;1 - connect using host IP address.
            hostInterface.setMain(1); // 0 - not default; 1 - default.
            hostInterface.setPort(interfacePort); // agent监控默认端口
            template.setTemplateid(templateId);
            createHostResponse = hostService
                    .createHost(host, hostInterface, group, template);
        } catch (Exception e) {
            e.printStackTrace();
        }
//      System.out.println("createHostResponse 的返回值String " + createHostResponse);
        return createHostResponse;
    }

    public String createHostWithGroup(String hostName, String interfaceIp,String groupid, String interfacePort,String templateId,int type) {
        Host host = new Host();
        HostGroup group = new HostGroup();
        HostInterface hostInterface = new HostInterface();
        Template template = new Template();
        String createHostResponse = null;
        try {
//		host.setName("test");
            host.setHost(hostName);
            host.setStatus(0); //0 - 监控开启 1-监控关闭
            group.setGroupid(groupid);
//		host.setProxy_hostid("10828");
            hostInterface.setType(type); // 1 - agent; 2 - SNMP; 3 - IPMI 4 - JMX.
            hostInterface.setIp(interfaceIp);
            hostInterface.setDns("");
            hostInterface.setUseip(1); // 0 - connect using host DNS name;1 - connect using host IP address.
            hostInterface.setMain(1); // 0 - not default; 1 - default.
            hostInterface.setPort(interfacePort); // agent监控默认端口
            template.setTemplateid(templateId);
            createHostResponse = hostService
                    .createHost(host, hostInterface, group, template);
        } catch (Exception e) {
            e.printStackTrace();
        }
//      System.out.println("createHostResponse 的返回值String " + createHostResponse);
        return createHostResponse;
    }
}
