package cn.edu.shou.monitor.domain;

import java.util.List;

/**
 * Created by sqhe18 on 2016/3/27.
 */
public class predictVoApplicationService {
    private long id;
    private String applicationName;//应用名称
    private String applicationUser;//应用用户
    private String applicationServiceObject;//服务对象
    private String applicationHost;//所在主机
    private String applicationNetName;//网络名称
    private String applicationIP;//应用IP
    private String applicationNetServiceObjectIP;//服务对象IP
    private String applicationMiddlewareName;//中间件名称
    private String applicationRemark;//备注
    private List<predictMmServiceObject> serviceObjectList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(String applicationUser) {
        this.applicationUser = applicationUser;
    }

    public String getApplicationServiceObject() {
        return applicationServiceObject;
    }

    public void setApplicationServiceObject(String applicationServiceObject) {
        this.applicationServiceObject = applicationServiceObject;
    }

    public String getApplicationHost() {
        return applicationHost;
    }

    public void setApplicationHost(String applicationHost) {
        this.applicationHost = applicationHost;
    }

    public String getApplicationNetName() {
        return applicationNetName;
    }

    public void setApplicationNetName(String applicationNetName) {
        this.applicationNetName = applicationNetName;
    }

    public String getApplicationIP() {
        return applicationIP;
    }

    public void setApplicationIP(String applicationIP) {
        this.applicationIP = applicationIP;
    }

    public String getApplicationNetServiceObjectIP() {
        return applicationNetServiceObjectIP;
    }

    public void setApplicationNetServiceObjectIP(String applicationNetServiceObjectIP) {
        this.applicationNetServiceObjectIP = applicationNetServiceObjectIP;
    }

    public String getApplicationMiddlewareName() {
        return applicationMiddlewareName;
    }

    public void setApplicationMiddlewareName(String applicationMiddlewareName) {
        this.applicationMiddlewareName = applicationMiddlewareName;
    }

    public String getApplicationRemark() {
        return applicationRemark;
    }

    public void setApplicationRemark(String applicationRemark) {
        this.applicationRemark = applicationRemark;
    }

    public List<predictMmServiceObject> getServiceObjectList() {
        return serviceObjectList;
    }

    public void setServiceObjectList(List<predictMmServiceObject> serviceObjectList) {
        this.serviceObjectList = serviceObjectList;
    }
}
