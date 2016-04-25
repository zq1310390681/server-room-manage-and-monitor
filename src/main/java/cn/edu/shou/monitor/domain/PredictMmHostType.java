package cn.edu.shou.monitor.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by sqhe18 on 2016/4/17.
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id" )
public class PredictMmHostType extends BaseEntity {
    @Getter @Setter
    private String hostType;//主机类型
    @Getter @Setter
    private String hostTypeName;//类型名称

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
