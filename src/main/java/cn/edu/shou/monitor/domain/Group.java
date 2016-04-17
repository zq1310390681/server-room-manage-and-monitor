package cn.edu.shou.monitor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by sqhe on 14-7-12.
 */
@Entity
@Table(name = "Groups")
public class Group extends BaseEntity {

    @Getter @Setter public String groupName;
    @Getter @Setter private String description;


    @Getter
    @Setter
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="group")
    @JsonIgnore
    private List<User> users;


    @Getter @Setter public boolean isDel;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, mappedBy="group")
    @JsonIgnore
    private List<Group> groupList;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="parentId")
    @JsonIgnore
    public Group group;



    public boolean getHasGroups() {
        if(this.groupList!=null&&this.groupList.size()>0)
            return true;
        else
            return false;
    }

    //private boolean hasGroups;


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }



    public boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(boolean isDel) {
        this.isDel = isDel;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}



