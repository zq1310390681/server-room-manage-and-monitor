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
public class predictMmRouters extends BaseEntity {
    @Getter @Setter
    private String routerSN;//路由器S/N号
    @Getter @Setter
    private String routerPurchasingDate;//购买时间
    @Getter @Setter
    private String routerMaintenanceDueTime;//维保到期时间
    @Getter @Setter
    private String routerSerialNumber;//路由器编号
    @Getter @Setter
    private String routerBrand;//路由器品牌
    @Getter @Setter
    private String routerType;//路由器型号
    @Getter @Setter
    private String routerIP;//路由器IP
    @Getter @Setter
    private String routerSNMP;//路由器SNMP
    @Getter @Setter
    private String routerPort;//路由器端口
    @Getter @Setter
    private String routerStorageDevice;//存储设备
    @Getter @Setter
    private String routerEquipmentCabinet;//所在机柜
    @Getter @Setter
    private String routerU;//所在U
    @Getter @Setter
    private String routerRemark;//备注
    @Getter @Setter
    private String hostId;

    public String getRouterSN() {
        return routerSN;
    }

    public void setRouterSN(String routerSN) {
        this.routerSN = routerSN;
    }

    public String getRouterPurchasingDate() {
        return routerPurchasingDate;
    }

    public void setRouterPurchasingDate(String routerPurchasingDate) {
        this.routerPurchasingDate = routerPurchasingDate;
    }

    public String getRouterMaintenanceDueTime() {
        return routerMaintenanceDueTime;
    }

    public void setRouterMaintenanceDueTime(String routerMaintenanceDueTime) {
        this.routerMaintenanceDueTime = routerMaintenanceDueTime;
    }

    public String getRouterSerialNumber() {
        return routerSerialNumber;
    }

    public void setRouterSerialNumber(String routerSerialNumber) {
        this.routerSerialNumber = routerSerialNumber;
    }

    public String getRouterBrand() {
        return routerBrand;
    }

    public void setRouterBrand(String routerBrand) {
        this.routerBrand = routerBrand;
    }

    public String getRouterType() {
        return routerType;
    }

    public void setRouterType(String routerType) {
        this.routerType = routerType;
    }

    public String getRouterIP() {
        return routerIP;
    }

    public void setRouterIP(String routerIP) {
        this.routerIP = routerIP;
    }

    public String getRouterSNMP() {
        return routerSNMP;
    }

    public void setRouterSNMP(String routerSNMP) {
        this.routerSNMP = routerSNMP;
    }

    public String getRouterPort() {
        return routerPort;
    }

    public void setRouterPort(String routerPort) {
        this.routerPort = routerPort;
    }

    public String getRouterStorageDevice() {
        return routerStorageDevice;
    }

    public void setRouterStorageDevice(String routerStorageDevice) {
        this.routerStorageDevice = routerStorageDevice;
    }

    public String getRouterEquipmentCabinet() {
        return routerEquipmentCabinet;
    }

    public void setRouterEquipmentCabinet(String routerEquipmentCabinet) {
        this.routerEquipmentCabinet = routerEquipmentCabinet;
    }

    public String getRouterU() {
        return routerU;
    }

    public void setRouterU(String routerU) {
        this.routerU = routerU;
    }

    public String getRouterRemark() {
        return routerRemark;
    }

    public void setRouterRemark(String routerRemark) {
        this.routerRemark = routerRemark;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }
}
