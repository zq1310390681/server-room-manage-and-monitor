package cn.edu.shou.monitor.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by Administrator on 2016/1/16.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class predictMmEquipType extends BaseEntity {
    @Getter @Setter
    private String equipTypeName; //设备类型名称
    @Getter @Setter
    private long parentId; //父编号
    @Getter @Setter
    private String zbxItemName;//该监控项在zbx中的item检索项,从name检索
    @Getter @Setter
    private String zbxItemKey;//该监控项在zbx中的item搜索项，从key检索
    @Getter @Setter
    private String description; //报警描述信息

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEquipTypeName() {
        return equipTypeName;
    }

    public void setEquipTypeName(String equipTypeName) {
        this.equipTypeName = equipTypeName;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getZbxItemName() {
        return zbxItemName;
    }

    public void setZbxItemName(String zbxItemName) {
        this.zbxItemName = zbxItemName;
    }

    public String getZbxItemKey() {
        return zbxItemKey;
    }

    public void setZbxItemKey(String zbxItemKey) {
        this.zbxItemKey = zbxItemKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
