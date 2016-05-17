package cn.edu.shou.monitor.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by sqhe18 on 2016/5/9.
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id" )
public class predictMmKvm extends BaseEntity {
    @Getter @Setter
    private String kvmNum;

    public String getKvmNum() {
        return kvmNum;
    }

    public void setKvmNum(String kvmNum) {
        this.kvmNum = kvmNum;
    }
}
