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
public class predictMmMainframe extends BaseEntity {
    @Getter @Setter
    private  String mainframeName;//主机

    public String getMainframeName() {
        return mainframeName;
    }

    public void setMainframeName(String mainframeName) {
        this.mainframeName = mainframeName;
    }
}
