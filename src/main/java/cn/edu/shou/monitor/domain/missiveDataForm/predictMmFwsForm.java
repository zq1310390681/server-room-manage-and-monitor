package cn.edu.shou.monitor.domain.missiveDataForm;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/1/14 0014.
 */
public class predictMmFwsForm {

    @Getter @Setter
    private long id;
    @Getter @Setter
    private String fwSN;//防火墙S/N号
    @Getter @Setter
    private String fwPurchasingDate;//购买时间
    @Getter @Setter
    private String fwMaintenanceDueTime;//维保到期时间
    @Getter @Setter
    private String fwSerialNumber;//防火墙编号
    @Getter @Setter
    private String fwBrand;//防火墙品牌
    @Getter @Setter
    private String fwType;//防火墙型号
    @Getter @Setter
    private String fwIP;//防火墙IP
    @Getter @Setter
    private String fwGroup;
    @Getter @Setter
    private String fwSNMP;//防火墙SNMP
    @Getter @Setter
    private String fwPort;//防火墙端口
    @Getter @Setter
    private String fwEquipmentCabinet;//所在机柜
    @Getter @Setter
    private String fwRemark;//备注
    @Getter @Setter
    private String hostId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFwSN() {
        return fwSN;
    }

    public void setFwSN(String fwSN) {
        this.fwSN = fwSN;
    }

    public String getFwPurchasingDate() {
        return fwPurchasingDate;
    }

    public void setFwPurchasingDate(String fwPurchasingDate) {
        this.fwPurchasingDate = fwPurchasingDate;
    }

    public String getFwMaintenanceDueTime() {
        return fwMaintenanceDueTime;
    }

    public void setFwMaintenanceDueTime(String fwMaintenanceDueTime) {
        this.fwMaintenanceDueTime = fwMaintenanceDueTime;
    }

    public String getFwSerialNumber() {
        return fwSerialNumber;
    }

    public void setFwSerialNumber(String fwSerialNumber) {
        this.fwSerialNumber = fwSerialNumber;
    }

    public String getFwBrand() {
        return fwBrand;
    }

    public void setFwBrand(String fwBrand) {
        this.fwBrand = fwBrand;
    }

    public String getFwType() {
        return fwType;
    }

    public void setFwType(String fwType) {
        this.fwType = fwType;
    }

    public String getFwIP() {
        return fwIP;
    }

    public void setFwIP(String fwIP) {
        this.fwIP = fwIP;
    }

    public String getFwGroup() {
        return fwGroup;
    }

    public void setFwGroup(String fwGroup) {
        this.fwGroup = fwGroup;
    }

    public String getFwSNMP() {
        return fwSNMP;
    }

    public void setFwSNMP(String fwSNMP) {
        this.fwSNMP = fwSNMP;
    }

    public String getFwPort() {
        return fwPort;
    }

    public void setFwPort(String fwPort) {
        this.fwPort = fwPort;
    }

    public String getFwEquipmentCabinet() {
        return fwEquipmentCabinet;
    }

    public void setFwEquipmentCabinet(String fwEquipmentCabinet) {
        this.fwEquipmentCabinet = fwEquipmentCabinet;
    }

    public String getFwRemark() {
        return fwRemark;
    }

    public void setFwRemark(String fwRemark) {
        this.fwRemark = fwRemark;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }
}
