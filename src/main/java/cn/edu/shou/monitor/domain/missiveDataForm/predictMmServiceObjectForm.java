package cn.edu.shou.monitor.domain.missiveDataForm;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/3/6.
 */
public class predictMmServiceObjectForm {
    @Getter @Setter
    private long id;
    @Getter  @Setter
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

    public String getServiceObjectGroup() {
        return serviceObjectGroup;
    }

    public void setServiceObjectGroup(String serviceObjectGroup) {
        this.serviceObjectGroup = serviceObjectGroup;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServiceObjectName() {
        return serviceObjectName;
    }

    public void setServiceObjectName(String serviceObjectName) {
        this.serviceObjectName = serviceObjectName;
    }

    public String getServiceObjectIp() {
        return serviceObjectIp;
    }

    public void setServiceObjectIp(String serviceObjectIp) {
        this.serviceObjectIp = serviceObjectIp;
    }
}
