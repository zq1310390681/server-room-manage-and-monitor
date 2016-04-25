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
public class predictMmStores extends BaseEntity {
    @Getter @Setter
    private String hostId;
    @Getter @Setter
    private String storeSN;//存储设备S/N号
    @Getter @Setter
    private String storePurchasingDate;//购买时间
    @Getter @Setter
    private String storeMaintenanceDueTime;//维保到期时间
    @Getter @Setter
    private String storeSerialNumber;//存储设备编号
    @Getter @Setter
    private String storeBrand;//存储设备品牌
    @Getter @Setter
    private String storeType;//存储设备型号
    @Getter @Setter
    private String storeIP;//存储设备IP
    @Getter @Setter
    private String storeEquipmentCabinet;//所在机柜
    @Getter @Setter
    private String storeU;//所在U
    @Getter @Setter
    private String storeRemark;//备注

    public String getStoreSN() {
        return storeSN;
    }

    public void setStoreSN(String storeSN) {
        this.storeSN = storeSN;
    }

    public String getStorePurchasingDate() {
        return storePurchasingDate;
    }

    public void setStorePurchasingDate(String storePurchasingDate) {
        this.storePurchasingDate = storePurchasingDate;
    }

    public String getStoreMaintenanceDueTime() {
        return storeMaintenanceDueTime;
    }

    public void setStoreMaintenanceDueTime(String storeMaintenanceDueTime) {
        this.storeMaintenanceDueTime = storeMaintenanceDueTime;
    }

    public String getStoreSerialNumber() {
        return storeSerialNumber;
    }

    public void setStoreSerialNumber(String storeSerialNumber) {
        this.storeSerialNumber = storeSerialNumber;
    }

    public String getStoreBrand() {
        return storeBrand;
    }

    public void setStoreBrand(String storeBrand) {
        this.storeBrand = storeBrand;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getStoreIP() {
        return storeIP;
    }

    public void setStoreIP(String storeIP) {
        this.storeIP = storeIP;
    }

    public String getStoreEquipmentCabinet() {
        return storeEquipmentCabinet;
    }

    public void setStoreEquipmentCabinet(String storeEquipmentCabinet) {
        this.storeEquipmentCabinet = storeEquipmentCabinet;
    }

    public String getStoreU() {
        return storeU;
    }

    public void setStoreU(String storeU) {
        this.storeU = storeU;
    }

    public String getStoreRemark() {
        return storeRemark;
    }

    public void setStoreRemark(String storeRemark) {
        this.storeRemark = storeRemark;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }
}
