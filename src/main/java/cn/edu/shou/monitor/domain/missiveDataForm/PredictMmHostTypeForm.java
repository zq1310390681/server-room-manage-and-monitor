package cn.edu.shou.monitor.domain.missiveDataForm;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sqhe18 on 2016/4/17.
 */

public class PredictMmHostTypeForm {
    @Getter @Setter
    private long id;
    @Getter @Setter
    private String hostType;//主机类型
    @Getter @Setter
    private String hostTypeName;//类型名称

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHostType() {
        return hostType;
    }

    public void setHostType(String hostType) {
        this.hostType = hostType;
    }

    public String getHostTypeName() {
        return hostTypeName;
    }

    public void setHostTypeName(String hostTypeName) {
        this.hostTypeName = hostTypeName;
    }
}
