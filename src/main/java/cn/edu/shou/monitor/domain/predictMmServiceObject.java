package cn.edu.shou.monitor.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by Administrator on 2016/1/15 0015.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class predictMmServiceObject extends BaseEntity {
    @Getter @Setter
    private  String serviceObjectName;//服务对象
    @Getter @Setter
    private  String serviceObjectIp;//服务IP
    @Getter @Setter
    private String serviceObjectGroup;//服务对象所在组
    @Getter @Setter
    private String keySerObj;//重点服务对象
    @Getter @Setter
    private String serviceObjectState;//服务对象状态

    public String getServiceObjectState() {
        return serviceObjectState;
    }

    public void setServiceObjectState(String serviceObjectState) {
        this.serviceObjectState = serviceObjectState;
    }

    public String getKeySerObj() {
        return keySerObj;
    }

    public void setKeySerObj(String keySerObj) {
        this.keySerObj = keySerObj;
    }

    public String getServiceObjectIp() {
        return serviceObjectIp;
    }

    public void setServiceObjectIp(String serviceObjectIp) {
        this.serviceObjectIp = serviceObjectIp;
    }

    public String getServiceObjectName() {
        return serviceObjectName;
    }

    public void setServiceObjectName(String serviceObjectName) {
        this.serviceObjectName = serviceObjectName;
    }

    public String getServiceObjectGroup() {
        return serviceObjectGroup;
    }

    public void setServiceObjectGroup(String serviceObjectGroup) {
        this.serviceObjectGroup = serviceObjectGroup;
    }
}
