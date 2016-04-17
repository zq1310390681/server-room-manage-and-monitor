package cn.edu.shou.monitor.domain.missiveDataForm;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/3/6.
 */
public class predictMmApplicationUserForm {
    @Getter @Setter
    private long id;
    @Getter @Setter
    private  String applicationUserCompany;//应用用户所在单位
    @Getter @Setter
    private  String applicationUserName;//应用用户名称
    @Getter @Setter
    private  String applicationUserIp;//应用用户IP

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
