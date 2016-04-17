package cn.edu.shou.monitor.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by Administrator on 2016/1/12.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class predictMmPing extends BaseEntity {
    @Getter @Setter
    private String pingName;//网络名称
    @Getter @Setter
    private String oneIP;//设备一 IP
    @Getter @Setter
    private String twoIP;//设备二 IP
    @Getter @Setter
    private String pingNote;//备注
    @Getter @Setter
    private String hostId;

    public String getPingName() {
        return pingName;
    }

    public void setPingName(String pingName) {
        this.pingName = pingName;
    }

    public String getOneIP() {
        return oneIP;
    }

    public void setOneIP(String oneIP) {
        this.oneIP = oneIP;
    }

    public String getTwoIP() {
        return twoIP;
    }

    public void setTwoIP(String twoIP) {
        this.twoIP = twoIP;
    }

    public String getPingNote() {
        return pingNote;
    }

    public void setPingNote(String pingNote) {
        this.pingNote = pingNote;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }
}
