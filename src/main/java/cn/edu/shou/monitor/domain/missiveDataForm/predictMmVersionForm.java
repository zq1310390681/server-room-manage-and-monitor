package cn.edu.shou.monitor.domain.missiveDataForm;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sqhe18 on 2016/4/5.
 */
public class predictMmVersionForm {
    @Getter @Setter
    private long id;
    @Getter @Setter
    private String versions;  //版本信息

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }
}
