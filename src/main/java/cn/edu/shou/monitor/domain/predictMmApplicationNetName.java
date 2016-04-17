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
public class predictMmApplicationNetName extends BaseEntity {
    @Getter @Setter
    private  String applicationNetName;//网络名称
    @Getter @Setter
    private  String applicationIP;//应用IP
    @Getter @Setter
    private  String applicationNetServiceObjectIP;//服务对象IP

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
}
