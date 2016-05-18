package cn.edu.shou.monitor.domain.missiveDataForm;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by light on 2016/5/17.
 */
public class PredictMmWiringForm {
    @Getter @Setter
    private long id;
    @Getter @Setter
    private String target;//目标
    @Getter @Setter
    private String source;//源
    @Getter @Setter
    private String targetPort;//目标上的端口
    @Getter @Setter
    private String sourcePort;//源上的端口
    @Getter @Setter
    private String wiringMark;//配线的标识
    @Getter @Setter
    private String wiringNote;//配线备注


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTargetPort() {
        return targetPort;
    }

    public void setTargetPort(String targetPort) {
        this.targetPort = targetPort;
    }

    public String getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
    }

    public String getWiringMark() {
        return wiringMark;
    }

    public void setWiringMark(String wiringMark) {
        this.wiringMark = wiringMark;
    }

    public String getWiringNote() {
        return wiringNote;
    }

    public void setWiringNote(String wiringNote) {
        this.wiringNote = wiringNote;
    }
}
