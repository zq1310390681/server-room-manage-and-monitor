package cn.edu.shou.monitor.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by light on 2016/5/17.
 */
public class PredictMmWiring {
    @Getter @Setter
    private int id;
    @Getter @Setter
    private String target;
    @Getter @Setter
    private String source;
    @Getter @Setter
    private String targetPort;
    @Getter @Setter
    private String sourcePort;
    @Getter @Setter
    private String wireMark;


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getWireMark() {
        return wireMark;
    }

    public void setWireMark(String wireMark) {
        this.wireMark = wireMark;
    }
}
