package cn.edu.shou.monitor.domain.missiveDataForm;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sqhe18 on 2016/4/5.
 */
public class predictMmEquipmentCabinetForm {
    @Getter @Setter
    private long id;
    @Getter @Setter
    private String equipmentCabinetName; //机柜名称
    @Getter @Setter
    private String equipmentCabinetUsedU;//机柜已被使用的U

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
