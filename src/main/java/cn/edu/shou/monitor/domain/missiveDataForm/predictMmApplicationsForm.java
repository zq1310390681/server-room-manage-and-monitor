package cn.edu.shou.monitor.domain.missiveDataForm;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/1/14 0014.
 */
public class predictMmApplicationsForm {

    @Getter @Setter
    private long id;
    @Getter @Setter
    private String applicationName;//应用名称
    @Getter @Setter
    private String applicationUser;//应用用户
    @Getter @Setter
    private String appGroup;//应用所在分组
    @Getter @Setter
    private String applicationServiceObject;//服务对象
    @Getter @Setter
    private String applicationHost;//所在主机de id
    @Getter @Setter
    private String hostContent;//添加主机内容
    @Getter @Setter
    private String applicationNetName;//网络名称
    @Getter @Setter
    private String applicationNetServiceObjectIP;//服务对象IP
    @Getter @Setter
    private String applicationIP;//应用IP
    @Getter @Setter
    private String applicationMiddlewareType;//中间件类型
    @Getter @Setter
    private String applicationMiddlewareName;//中间件名称
    @Getter @Setter
    private String serverRemark;//备注
    @Getter @Setter
    private String hostName;
    @Getter @Setter
    private String hostid;

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

    public String getApplicationNetServiceObjectIP() {
        return applicationNetServiceObjectIP;
    }

    public void setApplicationNetServiceObjectIP(String applicationNetServiceObjectIP) {
        this.applicationNetServiceObjectIP = applicationNetServiceObjectIP;
    }

    public String getApplicationIP() {
        return applicationIP;
    }

    public void setApplicationIP(String applicationIP) {
        this.applicationIP = applicationIP;
    }

    public String getApplicationMiddlewareType() {
        return applicationMiddlewareType;
    }

    public void setApplicationMiddlewareType(String applicationMiddlewareType) {
        this.applicationMiddlewareType = applicationMiddlewareType;
    }

    public String getApplicationMiddlewareName() {
        return applicationMiddlewareName;
    }

    public void setApplicationMiddlewareName(String applicationMiddlewareName) {
        this.applicationMiddlewareName = applicationMiddlewareName;
    }

    public String getServerRemark() {
        return serverRemark;
    }

    public void setServerRemark(String serverRemark) {
        this.serverRemark = serverRemark;
    }

    public String getHostContent() {
        return hostContent;
    }

    public void setHostContent(String hostContent) {
        this.hostContent = hostContent;
    }

    public String getAppGroup() {
        return appGroup;
    }

    public void setAppGroup(String appGroup) {
        this.appGroup = appGroup;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostid() {
        return hostid;
    }

    public void setHostid(String hostid) {
        this.hostid = hostid;
    }
}
