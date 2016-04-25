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
public class predictMmMiddleware extends BaseEntity {
    @Getter @Setter
    private  String middlewareTypeName;//中间件类型名称
    @Getter @Setter
    private long parentId; //父编号
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMiddlewareTypeName() {
        return middlewareTypeName;
    }

    public void setMiddlewareTypeName(String middlewareTypeName) {
        this.middlewareTypeName = middlewareTypeName;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
