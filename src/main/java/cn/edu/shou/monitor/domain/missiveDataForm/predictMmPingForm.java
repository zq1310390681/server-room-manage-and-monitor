package cn.edu.shou.monitor.domain.missiveDataForm;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/1/21.
 */
public class predictMmPingForm {
    @Setter @Getter
    private long id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
