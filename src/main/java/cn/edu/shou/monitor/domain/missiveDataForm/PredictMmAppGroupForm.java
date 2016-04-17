package cn.edu.shou.monitor.domain.missiveDataForm;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sqhe18 on 2016/4/4.
 */
public class PredictMmAppGroupForm {
    @Getter @Setter
    private long id;
    @Getter @Setter
    private long groupOrder;//分组的排序
    @Getter @Setter
    private String appGroupName;// 分组名称

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
