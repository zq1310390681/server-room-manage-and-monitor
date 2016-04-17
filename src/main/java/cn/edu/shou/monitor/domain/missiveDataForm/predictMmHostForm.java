package cn.edu.shou.monitor.domain.missiveDataForm;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/1/25.
 */
public class predictMmHostForm {
    @Getter @Setter
    private long id;
    @Getter @Setter
    private String hosts;  //主机名称
    @Getter  @Setter
    private String hostIP; //主机IP
    @Getter  @Setter
    private String hostOS; //主机操作系统
    @Getter  @Setter
    private String hostNote; //备注
    @Getter  @Setter
    private String hostId;
    @Getter @Setter
    private String hostType;//主机类型
    @Getter  @Setter
    private String hostServer; //主机所在服务器

    public String getHostType() {
        return hostType;
    }

    public void setHostType(String hostType) {
        this.hostType = hostType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public String getHostIP() {
        return hostIP;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    public String getHostOS() {
        return hostOS;
    }

    public void setHostOS(String hostOS) {
        this.hostOS = hostOS;
    }

    public String getHostNote() {
        return hostNote;
    }

    public void setHostNote(String hostNote) {
        this.hostNote = hostNote;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getHostServer() {
        return hostServer;
    }

    public void setHostServer(String hostServer) {
        this.hostServer = hostServer;
    }
}
