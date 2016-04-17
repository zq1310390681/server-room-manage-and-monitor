package cn.edu.shou.monitor.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by Administrator on 2016/3/6.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class predictMmDynamicEnvironment extends BaseEntity {
    @Getter @Setter
    private long environmentDataSource;//数据库地址
    @Getter @Setter
    private long environmentUsername;//用户名
    @Getter @Setter
    private String environmentPassword;//密码

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
