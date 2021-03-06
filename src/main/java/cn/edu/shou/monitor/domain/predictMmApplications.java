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
    private String applicationServiceObject;//服务对象    取到的值为id
    @Getter @Setter
    private String applicationHost;//所在主机
    @Getter @Setter
    private String hostContent;//添加所在主机内容
    @Getter @Setter
    private String applicationMiddlewareName;//中间件   取到的值为id
    @Getter @Setter
    private String applicationRemark;//备注
    @Getter @Setter
    private String hostName;//所在主机名称
    @Getter @Setter
    private String hostid;
    @Getter @Setter
    private String keyApp; //重点应用
    @Getter @Setter
    private String  appState;//应用状态

    public String getAppState() {
        return appState;
    }

    public void setAppState(String appState) {
        this.appState = appState;
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

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
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

    public String getKeyApp() {
        return keyApp;
    }

    public void setKeyApp(String keyApp) {
        this.keyApp = keyApp;
    }
}
