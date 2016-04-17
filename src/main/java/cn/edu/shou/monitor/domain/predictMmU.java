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
public class predictMmU extends BaseEntity {
    @Getter @Setter
    private  String uName;//u名称
    @Getter @Setter
    private  String uCode;//用来计算U的高度

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }



    public String getuCode() {
        return uCode;
    }

    public void setuCode(String uCode1) {
        this.uCode = uCode;
    }
}
