package cn.edu.shou.monitor.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by sqhe18 on 2016/4/4.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class PredictMmAppGroup extends BaseEntity {
    @Getter  @Setter
    private long groupOrder;//分组的排序
    @Getter @Setter
    private String appGroupName;// 分组名称

    public long getGroupOrder() {
        return groupOrder;
    }

    public void setGroupOrder(long groupOrder) {
        this.groupOrder = groupOrder;
    }

    public String getAppGroupName() {
        return appGroupName;
    }

    public void setAppGroupName(String appGroupName) {
        this.appGroupName = appGroupName;
    }
}
