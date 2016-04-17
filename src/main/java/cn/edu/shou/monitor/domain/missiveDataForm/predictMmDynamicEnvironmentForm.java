package cn.edu.shou.monitor.domain.missiveDataForm;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/3/6.
 */
public class predictMmDynamicEnvironmentForm {
    @Getter @Setter
    private long id;
    @Getter  @Setter
    private long environmentDataSource;//数据库地址
    @Getter @Setter
    private long environmentUsername;//用户名
    @Getter @Setter
    private String environmentPassword;//密码

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEnvironmentDataSource() {
        return environmentDataSource;
    }

    public void setEnvironmentDataSource(long environmentDataSource) {
        this.environmentDataSource = environmentDataSource;
    }

    public long getEnvironmentUsername() {
        return environmentUsername;
    }

    public void setEnvironmentUsername(long environmentUsername) {
        this.environmentUsername = environmentUsername;
    }

    public String getEnvironmentPassword() {
        return environmentPassword;
    }

    public void setEnvironmentPassword(String environmentPassword) {
        this.environmentPassword = environmentPassword;
    }
}
