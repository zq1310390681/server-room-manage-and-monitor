package cn.edu.shou.monitor.domain.missiveDataForm;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sqhe18 on 2016/5/9.
 */
public class predictMmKvmForm {
    @Getter @Setter
    private long id;
    @Getter @Setter
    private String kvmNum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKvmNum() {
        return kvmNum;
    }

    public void setKvmNum(String kvmNum) {
        this.kvmNum = kvmNum;
    }
}
