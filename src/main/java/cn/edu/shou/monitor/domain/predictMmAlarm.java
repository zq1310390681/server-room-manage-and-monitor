package cn.edu.shou.monitor.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by Administrator on 2016/1/13.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class PredictMmAlarm extends BaseEntity {
    @Getter @Setter
    private long equipTypeName;//告警设备类别名称编号
    @Getter @Setter
    private long equipTypeElement;//告警设备监控要素
    @Getter @Setter
    private String alarmThreshold;//告警阈值
    @Getter @Setter
    private String alarmGrade;//告警等级
    @Getter @Setter
    private String alarmInform;//填写信息
    @Getter @Setter
    private String alarmNote;//备注


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEquipTypeName() {
        return equipTypeName;
    }

    public void setEquipTypeName(long equipTypeName) {
        this.equipTypeName = equipTypeName;
    }

    public long getEquipTypeElement() {
        return equipTypeElement;
    }

    public void setEquipTypeElement(long equipTypeElement) {
        this.equipTypeElement = equipTypeElement;
    }

    public String getAlarmThreshold() {
        return alarmThreshold;
    }

    public void setAlarmThreshold(String alarmThreshold) {
        this.alarmThreshold = alarmThreshold;
    }

    public String getAlarmNote() {
        return alarmNote;
    }

    public void setAlarmNote(String alarmNote) {
        this.alarmNote = alarmNote;
    }

    public String getAlarmGrade() {
        return alarmGrade;
    }

    public void setAlarmGrade(String alarmGrade) {
        this.alarmGrade = alarmGrade;
    }

    public String getAlarmInform() {
        return alarmInform;
    }

    public void setAlarmInform(String alarmInform) {
        this.alarmInform = alarmInform;
    }
}
