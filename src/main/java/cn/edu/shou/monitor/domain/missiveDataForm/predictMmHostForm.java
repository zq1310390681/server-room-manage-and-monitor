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
    private String hostServer;  //主机所在服务器
    @Getter  @Setter
    private String hostNote; //备注
    @Getter  @Setter
    private String hostId;
    @Getter @Setter
    private String hostType;//主机类型
    @Getter  @Setter
    private String appName;//应用名称
    @Getter  @Setter
    private String hostUserName;//主机用户名
    @Getter  @Setter
    private String hostPassword;//主机密码
    @Getter  @Setter
    private String operational;//是否业务化
    @Getter  @Setter
    private String vmwareUserName;// 虚拟机用户名
    @Getter  @Setter
    private String  hostState;//主机状态
    @Getter @Setter
    private String  SMSName; //短信提示
    @Getter @Setter
    private String topRelation; //拓扑关系

    public String getTopRelation() {
        return topRelation;
    }

    public void setTopRelation(String topRelation) {
        this.topRelation = topRelation;
    }
    public String getSMSName() {
        return SMSName;
    }

    public void setSMSName(String SMSName) {
        this.SMSName = SMSName;
    }

    public String getHostState() {
        return hostState;
    }

    public void setHostState(String hostState) {
        this.hostState = hostState;
    }
    public String getHostUserName() {
        return hostUserName;
    }

    public void setHostUserName(String hostUserName) {
        this.hostUserName = hostUserName;
    }

    public String getHostPassword() {
        return hostPassword;
    }

    public void setHostPassword(String hostPassword) {
        this.hostPassword = hostPassword;
    }

    public String getOperational() {
        return operational;
    }

    public void setOperational(String operational) {
        this.operational = operational;
    }

    public String getHostServer() {
        return hostServer;
    }

    public void setHostServer(String hostServer) {
        this.hostServer = hostServer;
    }

    public String getHostType() {
        return hostType;
    }

    public void setHostType(String hostType) {
        this.hostType = hostType;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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

    public String getVmwareUserName() {
        return vmwareUserName;
    }

    public void setVmwareUserName(String vmwareUserName) {
        this.vmwareUserName = vmwareUserName;
    }
}
