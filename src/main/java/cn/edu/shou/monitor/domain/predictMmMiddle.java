package cn.edu.shou.monitor.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by Administrator on 2016/3/15 0015.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class predictMmMiddle extends BaseEntity {
    @Getter @Setter
    private String middleName;//中间件数据库名称
    @Getter @Setter
    private String middleIP;//中间件数据库IP
    @Setter @Getter
    private String middleHost;//中间件数据库所在主机
    @Getter @Setter
    private String middleUserName;//中间件数据库用户名
    @Getter @Setter
    private String middlePassword;//中间件数据库密码
    @Getter @Setter
    private String middleVersion;//中间件版本信息
    @Getter @Setter
    private String middleOS;//中间件操作系统
    @Getter @Setter
    private String middleNote;//备注
    @Getter @Setter
    private String hostId;
    @Getter @Setter
    private String middleType;//中间件类型   1代表iis 2代表tomact 3代表sql 4代表oracle 5代表java



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMiddleIP() {
        return middleIP;
    }

    public void setMiddleIP(String middleIP) {
        this.middleIP = middleIP;
    }

    public String getMiddleHost() {
        return middleHost;
    }

    public void setMiddleHost(String middleHost) {
        this.middleHost = middleHost;
    }

    public String getMiddleUserName() {
        return middleUserName;
    }

    public void setMiddleUserName(String middleUserName) {
        this.middleUserName = middleUserName;
    }

    public String getMiddlePassword() {
        return middlePassword;
    }

    public void setMiddlePassword(String middlePassword) {
        this.middlePassword = middlePassword;
    }

    public String getMiddleVersion() {
        return middleVersion;
    }

    public void setMiddleVersion(String middleVersion) {
        this.middleVersion = middleVersion;
    }

    public String getMiddleOS() {
        return middleOS;
    }

    public void setMiddleOS(String middleOS) {
        this.middleOS = middleOS;
    }

    public String getMiddleNote() {
        return middleNote;
    }

    public void setMiddleNote(String middleNote) {
        this.middleNote = middleNote;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getMiddleType() {
        return middleType;
    }

    public void setMiddleType(String middleType) {
        this.middleType = middleType;
    }
}
