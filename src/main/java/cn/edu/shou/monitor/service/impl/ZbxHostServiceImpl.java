package cn.edu.shou.monitor.service.impl;

import cn.edu.shou.monitor.domain.host.HostDeleteRequest;
import cn.edu.shou.monitor.domain.host.HostGetRequest;
import cn.edu.shou.monitor.service.zbxapi.IHostService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;

/**
 * Created by light on 2016/2/26.
 */

public class ZbxHostServiceImpl {
    public static IHostService hostService = new HostServiceImpl();

    static {
        // 登录
         Login.login();
    }

    // Type: 1 - Zbx_agent; 2 - SNMP; 3 - IPMI 4 - JMX.
    // Port: 1 - 10050; 2 - 161; 3 - 12345; 4 - 632
    public static CreateDuplicate createDuplicate = new CreateDuplicate();
    String  response;

    //创建防火墙
    public String createHostFw(String hostName,String interfaceIp) {
        response= createDuplicate.createHostEasy(hostName,interfaceIp,"161","10081",2);
        return response;
    }

    // 创建主机  收集服务器性能信息:CPU、Memory等
    public String createHostHost(String hostName,String interfaceIp) {
        response= createDuplicate.createHostEasy(hostName,interfaceIp,"10050","10081",1);
        return response;
    }

    //创建iis 服务
    public String createHostIIS(String hostName,String interfaceIp) {
        response= createDuplicate.createHostEasy(hostName,interfaceIp,"10050","10107",1);
        return response;
    }

    //创建jvm
    public String createHostJava(String hostName,String interfaceIp) {
        response= createDuplicate.createHostEasy(hostName,interfaceIp,"12345","10082",4);
        return response;
    }

    //创建 oracle 数据库
    public String createHostOracle(String hostName,String interfaceIp) {
        response= createDuplicate.createHostEasy(hostName,interfaceIp,"10050","10109",1);
        return response;
    }

    // 创建 ping 监控 changes needed here
    public String createHostPing(String hostName,String interfaceIp) {
        response= createDuplicate.createHostEasy(hostName,interfaceIp,"10050","10081",1);
        return response;
    }

    //创建路由
    public String createHostRouter(String hostName,String interfaceIp) {
        response= createDuplicate.createHostEasy(hostName,interfaceIp,"161","10081",2);
        return response;
    }

    // 收集服务器硬件信息：温度，转速等
    public String createHostServer(String hostName,String interfaceIp,String interfacePort) {
        response= createDuplicate.createHostEasy(hostName,interfaceIp,"10050","10081",1);
        return response;
    }

    //创建IPMI
    public String createHostServerIPMI(String hostName,String interfaceIp) {
        response= createDuplicate.createHostEasy(hostName,interfaceIp,"623","10081",4);
        return response;
    }

    //SQL_Server
    public String createHostSqlserver(String hostName,String interfaceIp) {
        response= createDuplicate.createHostEasy(hostName,interfaceIp,"10050","10111",1);
        return response;
    }

    //创建存储
    public String createHostStore(String hostName,String interfaceIp) {
        response= createDuplicate.createHostEasy(hostName,interfaceIp,"10050","10081",1);
        return response;
    }

    //创建交换机   With Group
    public String createHostSwitchboard(String hostName,String interfaceIp) {
        response= createDuplicate.createHostWithGroup(hostName, interfaceIp, "9","161","10208",2);
        return response;
    }

    //创建tomact服务
    public String createHostTomcat(String hostName,String interfaceIp) {
        response= createDuplicate.createHostEasy(hostName,interfaceIp,"12345","10082",4);
        return response;
    }

    // 创建虚拟机
    public String createHostVmware(String hostName,String interfaceIp) {
        response= createDuplicate.createHostEasy(hostName,interfaceIp,"10050","10081",1);
        return response;
    }

    // middle ware//根据中间件类型获得中间件  middleType=1代表iis 2代表tomact 3代表sql 4代表oracle 5代表java
    public String createMiddleWare(String middleType,String hostName,String interfaceIp){
        if(middleType.equals("1")){
            response= createDuplicate.createHostEasy(hostName,interfaceIp,"10050","10107",1);
        }
        if(middleType.equals("2")){
            response= createDuplicate.createHostEasy(hostName,interfaceIp,"12345","10082",4);
        }
        if(middleType.equals("3")){
            response= createDuplicate.createHostEasy(hostName,interfaceIp,"10050","10111",1);
        }
        if(middleType.equals("4")){
            response= createDuplicate.createHostEasy(hostName,interfaceIp,"10050","10109",1);
        }
        if(middleType.equals("5")){
            response= createDuplicate.createHostEasy(hostName,interfaceIp,"12345","10082",4);
        }
        return response;
    }

    public String ZbxDeleteServer(String hostId) {
        // 数据准备
        HostDeleteRequest delete = new HostDeleteRequest();
        delete.getParams().add(hostId);
        return hostService.delete(delete);
    }

    public JSONObject getHostIdAndName(){
        HostGetRequest hostGetRequestContent = new HostGetRequest();
        String[] outputs = {"name"};
        hostGetRequestContent.getParams().setOutput(outputs);
//        hostGetRequestContent.getParams();

        return hostService.get(hostGetRequestContent);
    }

    public JSONObject getHostIdAndName(String hostid){
        HostGetRequest hostGetRequestContent = new HostGetRequest();
        String[] outputs = {"name"};
        String[] hostids = { hostid };
        hostGetRequestContent.getParams().setOutput(outputs);
        hostGetRequestContent.getParams().setHostids(hostids);

        return hostService.get(hostGetRequestContent);
    }


    public static class HostArray{
        public static ArrayList<String> getHostId(){
            ZbxHostServiceImpl zbxHostIds = new ZbxHostServiceImpl();
            JSONObject hostIdsJsb = zbxHostIds.getHostIdAndName();
            JSONArray hostIdsJsa = hostIdsJsb.getJSONArray("result");
            ArrayList<String> hostIds = new ArrayList<String>();
            for(int i=0;i<hostIdsJsa.length();i++) {
                Object hostid = hostIdsJsa.getJSONObject(i).get("hostid");
                hostIds.add(i,String.valueOf(hostid));
            }
            return hostIds;
        }

        public static ArrayList<String> getHostName(){
            ZbxHostServiceImpl zbxHostNames = new ZbxHostServiceImpl();
            JSONObject hostNamesJsb = zbxHostNames.getHostIdAndName();
            JSONArray hostNameJsa = hostNamesJsb.getJSONArray("result");
            ArrayList<String> hostNames = new ArrayList<String>();
            for(int i=0;i<hostNameJsa.length();i++) {
                JSONObject name = hostNameJsa.getJSONObject(i);
                Object names = name.get("name");
                hostNames.add(i,String.valueOf(names));
            }
            return hostNames;
        }
    }
}


