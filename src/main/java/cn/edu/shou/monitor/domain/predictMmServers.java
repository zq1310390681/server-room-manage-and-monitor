package cn.edu.shou.monitor.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class predictMmServers extends BaseEntity {
    @Getter @Setter
    private String hostId;
    @Getter @Setter
    private String serverSN;//服务器S/N号
    @Getter @Setter
    private String serverPurchasingDate;//购买时间
    @Getter @Setter
    private String serverMaintenanceDueTime;//维保到期时间
    @Getter @Setter
    private String serverSerialNumber;//服务器编号
    @Getter @Setter
    private String serverBrand;//服务器品牌
    @Getter @Setter
    private String serverType;//服务器型号
    @Getter @Setter
    private String serverIP;//服务器IP
    @Getter @Setter
    private String serverIPMI;//服务器IPMI
    @Getter @Setter
    private String serverPort;//服务器端口
    @Getter @Setter
    private String serverStorageDevice;//存储设备
    @Getter @Setter
    private String serverEquipmentCabinet;//所在机柜
    @Getter @Setter
    private String serverU;//所在U
    @Getter @Setter
    private String serverRemark;//备注

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getServerSN() {
        return serverSN;
    }

    public void setServerSN(String serverSN) {
        this.serverSN = serverSN;
    }

    public String getServerPurchasingDate() {
        return serverPurchasingDate;
    }

    public void setServerPurchasingDate(String serverPurchasingDate) {
        this.serverPurchasingDate = serverPurchasingDate;
    }

    public String getServerMaintenanceDueTime() {
        return serverMaintenanceDueTime;
    }

    public void setServerMaintenanceDueTime(String serverMaintenanceDueTime) {
        this.serverMaintenanceDueTime = serverMaintenanceDueTime;
    }

    public String getServerSerialNumber() {
        return serverSerialNumber;
    }

    public void setServerSerialNumber(String serverSerialNumber) {
        this.serverSerialNumber = serverSerialNumber;
    }

    public String getServerBrand() {
        return serverBrand;
    }

    public void setServerBrand(String serverBrand) {
        this.serverBrand = serverBrand;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getServerIPMI() {
        return serverIPMI;
    }

    public void setServerIPMI(String serverIPMI) {
        this.serverIPMI = serverIPMI;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerStorageDevice() {
        return serverStorageDevice;
    }

    public void setServerStorageDevice(String serverStorageDevice) {
        this.serverStorageDevice = serverStorageDevice;
    }

    public String getServerEquipmentCabinet() {
        return serverEquipmentCabinet;
    }

    public void setServerEquipmentCabinet(String serverEquipmentCabinet) {
        this.serverEquipmentCabinet = serverEquipmentCabinet;
    }

    public String getServerU() {
        return serverU;
    }

    public void setServerU(String serverU) {
        this.serverU = serverU;
    }

    public String getServerRemark() {
        return serverRemark;
    }

    public void setServerRemark(String serverRemark) {
        this.serverRemark = serverRemark;
    }

}
