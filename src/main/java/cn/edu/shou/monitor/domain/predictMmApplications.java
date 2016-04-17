package cn.edu.shou.monitor.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class predictMmApplications extends BaseEntity {
    @Getter @Setter
    private String applicationName;//应用名称
    @Getter @Setter
    private String appGroup;//应用所在分组
    @Getter @Setter
    private String applicationUser;//应用用户
    @Getter @Setter
    private String applicationServiceObject;//服务对象
    @Getter @Setter
    private String applicationHost;//所在主机
    @Getter @Setter
    private String hostContent;//添加所在主机内容
    @Getter @Setter
    private String applicationNetName;//网络名称
    @Getter @Setter
    private String applicationIP;//应用IP
    @Getter @Setter
    private String applicationNetServiceObjectIP;//服务对象IP
    @Getter @Setter
    private String applicationMiddlewareName;//中间件名称
    @Getter @Setter
    private String applicationRemark;//备注
    @Getter @Setter
    private String hostName;
    @Getter @Setter
    private String hostid;


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

    public String getHostid() {
        return hostid;
    }

    public void setHostid(String hostid) {
        this.hostid = hostid;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}
