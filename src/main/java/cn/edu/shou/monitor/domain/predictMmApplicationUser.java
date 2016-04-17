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
public class predictMmApplicationUser extends BaseEntity {
    @Getter @Setter
    private  String applicationUserCompany;//应用用户所在单位
    @Getter @Setter
    private  String applicationUserName;//应用用户名称
    @Getter @Setter
    private  String applicationUserIp;//应用用户IP

    public String getApplicationUserCompany() {
        return applicationUserCompany;
    }

    public void setApplicationUserCompany(String applicationUserCompany) {
        this.applicationUserCompany = applicationUserCompany;
    }

    public String getApplicationUserName() {
        return applicationUserName;
    }

    public void setApplicationUserName(String applicationUserName) {
        this.applicationUserName = applicationUserName;
    }

    public String getApplicationUserIp() {
        return applicationUserIp;
    }

    public void setApplicationUserIp(String applicationUserIp) {
        this.applicationUserIp = applicationUserIp;
    }
}
