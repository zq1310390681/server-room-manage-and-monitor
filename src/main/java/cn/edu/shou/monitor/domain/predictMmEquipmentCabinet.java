package cn.edu.shou.monitor.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by Administrator on 2016/1/15 0015.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class PredictMmEquipmentCabinet extends BaseEntity {
    @Getter @Setter
    private  String equipmentCabinetName;//机柜名称
    @Getter @Setter
    private  String equipmentCabinetUsedU;//机柜已被使用的U

    public String getEquipmentCabinetName() {
        return equipmentCabinetName;
    }

    public void setEquipmentCabinetName(String equipmentCabinetName) {
        this.equipmentCabinetName = equipmentCabinetName;
    }

    public String getEquipmentCabinetUsedU() {
        return equipmentCabinetUsedU;
    }

    public void setEquipmentCabinetUsedU(String equipmentCabinetUsedU) {
        this.equipmentCabinetUsedU = equipmentCabinetUsedU;
    }
}
