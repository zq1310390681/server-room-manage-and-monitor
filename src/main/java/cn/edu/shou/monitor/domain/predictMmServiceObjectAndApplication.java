package cn.edu.shou.monitor.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by sqhe18 on 2016/3/27.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class predictMmServiceObjectAndApplication extends BaseEntity {
    @Getter @Setter
    private String serviceObjectId;//服务对象的ID
    @Getter @Setter
    private long applicationId;//应用的ID

    public String getServiceObjectId() {
        return serviceObjectId;
    }

    public void setServiceObjectId(String serviceObjectId) {
        this.serviceObjectId = serviceObjectId;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }
}
