package cn.edu.shou.monitor.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by sqhe18 on 2016/4/18.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class PredictMmAlarmGrade extends BaseEntity {
    @Getter @Setter
    private String alarmGradeName;//告警等级名称

    public String getAlarmGradeName() {
        return alarmGradeName;
    }

    public void setAlarmGradeName(String alarmGradeName) {
        this.alarmGradeName = alarmGradeName;
    }
}
