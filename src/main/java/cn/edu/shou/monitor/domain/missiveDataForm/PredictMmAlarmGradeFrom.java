package cn.edu.shou.monitor.domain.missiveDataForm;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sqhe18 on 2016/4/20.
 */
public class PredictMmAlarmGradeFrom {
    @Setter  @Getter
    private long id;
    @Getter @Setter
    private String alarmGradeName;//告警等级名称

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlarmGradeName() {
        return alarmGradeName;
    }

    public void setAlarmGradeName(String alarmGradeName) {
        this.alarmGradeName = alarmGradeName;
    }
}
