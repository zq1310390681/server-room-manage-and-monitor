package cn.edu.shou.monitor.domain.missiveDataForm;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/1/14 0014.
 */
public class predictMmSwitchboardsForm {

    @Getter @Setter
    private String hostId;
    @Getter @Setter
    private long id;
    @Getter @Setter
    private String switchboardSN;//交换机S/N号
    @Getter @Setter
    private String switchboardPurchasingDate;//购买时间
    @Getter @Setter
    private String switchboardMaintenanceDueTime;//维保到期时间
    @Getter @Setter
    private String switchboardSerialNumber;//交换机编号
    @Getter @Setter
    private String switchboardBrand;//交换机品牌
    @Getter @Setter
    private String switchboardType;//交换机型号
    @Getter @Setter
    private String switchboardIP;//交换机IP
    @Getter @Setter
    private String switchboardGroup;
    @Getter @Setter
    private String switchboardSNMP;//交换机SNMP
    @Getter @Setter
    private String switchboardPort;//交换机端口
    @Getter @Setter
    private String switchboardEquipmentCabinet;//所在机柜
    @Getter @Setter
    private String switchboardU;//所在U
    @Getter @Setter
    private String switchboardRemark;//备注


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSwitchboardSN() {
        return switchboardSN;
    }

    public void setSwitchboardSN(String switchboardSN) {
        this.switchboardSN = switchboardSN;
    }

    public String getSwitchboardPurchasingDate() {
        return switchboardPurchasingDate;
    }

    public void setSwitchboardPurchasingDate(String switchboardPurchasingDate) {
        this.switchboardPurchasingDate = switchboardPurchasingDate;
    }

    public String getSwitchboardMaintenanceDueTime() {
        return switchboardMaintenanceDueTime;
    }

    public void setSwitchboardMaintenanceDueTime(String switchboardMaintenanceDueTime) {
        this.switchboardMaintenanceDueTime = switchboardMaintenanceDueTime;
    }

    public String getSwitchboardSerialNumber() {
        return switchboardSerialNumber;
    }

    public void setSwitchboardSerialNumber(String switchboardSerialNumber) {
        this.switchboardSerialNumber = switchboardSerialNumber;
    }

    public String getSwitchboardBrand() {
        return switchboardBrand;
    }

    public void setSwitchboardBrand(String switchboardBrand) {
        this.switchboardBrand = switchboardBrand;
    }

    public String getSwitchboardType() {
        return switchboardType;
    }

    public void setSwitchboardType(String switchboardType) {
        this.switchboardType = switchboardType;
    }

    public String getSwitchboardIP() {
        return switchboardIP;
    }

    public void setSwitchboardIP(String switchboardIP) {
        this.switchboardIP = switchboardIP;
    }

    public String getSwitchboardGroup() {
        return switchboardGroup;
    }

    public void setSwitchboardGroup(String switchboardGroup) {
        this.switchboardGroup = switchboardGroup;
    }

    public String getSwitchboardSNMP() {
        return switchboardSNMP;
    }

    public void setSwitchboardSNMP(String switchboardSNMP) {
        this.switchboardSNMP = switchboardSNMP;
    }

    public String getSwitchboardPort() {
        return switchboardPort;
    }

    public void setSwitchboardPort(String switchboardPort) {
        this.switchboardPort = switchboardPort;
    }

    public String getSwitchboardEquipmentCabinet() {
        return switchboardEquipmentCabinet;
    }

    public void setSwitchboardEquipmentCabinet(String switchboardEquipmentCabinet) {
        this.switchboardEquipmentCabinet = switchboardEquipmentCabinet;
    }

    public String getSwitchboardU() {
        return switchboardU;
    }

    public void setSwitchboardU(String switchboardU) {
        this.switchboardU = switchboardU;
    }

    public String getSwitchboardRemark() {
        return switchboardRemark;
    }

    public void setSwitchboardRemark(String switchboardRemark) {
        this.switchboardRemark = switchboardRemark;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }
}
