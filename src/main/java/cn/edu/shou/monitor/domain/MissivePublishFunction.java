package cn.edu.shou.monitor.domain;


import cn.edu.shou.monitor.domain.missiveDataForm.GroupFrom;
import cn.edu.shou.monitor.domain.missiveDataForm.UserFrom;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MissivePublishFunction {

    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public UserFrom userToUserForm(User user){

        UserFrom userFrom=new UserFrom();

        if(user.getId()!=0){
            userFrom.id=user.getId();
        }
        if(user.getUserName()!=null){
            userFrom.userName=user.getUserName();
        }
        if(user.getName()!=null){
            userFrom.Name=user.getName();
        }
        if(user.getSex()!=null){
            userFrom.sex=user.getSex();
        }
        if(user.getTel()!=null){
            userFrom.tel=user.getTel();
        }
        if(user.getEmail()!=null){
            userFrom.sex=user.getSex();
        }
        if(user.getEmail()!=null){
            userFrom.email=user.getEmail();
        }
        if(user.getPassword()!=null){
            userFrom.password=user.getPassword();
        }
        if(user.getImagePath()!=null){
            userFrom.imagePath=user.getImagePath();
        }
        if(user.getLastLoginTime()!=null){
            try{
                userFrom.lastLoginTime=dateFormat.format(user.getLastLoginTime());
            }
            catch (Exception e){}

        }
        if(user.isEnabled()!=false){
            userFrom.enabled=user.isEnabled();
        }
        if(user.getDescription()!=null){
            userFrom.description=user.getDescription();
        }
        if(user.getGroup()!=null){//user.group
            GroupFrom groupFrom=new GroupFrom();
            groupFrom=this.groupToGroupFrom(user.getGroup());

            userFrom.group=groupFrom;
        }
        if(user.getSignImg()!=null){//郑小罗 2014/12/26 添加用户签名
            userFrom.signImg=user.getSignImg();
        }

        return userFrom;
    }

    private UserFrom userToUserFormWithoutGroupInfo(User user){

        UserFrom userFrom=new UserFrom();

        if(user.getId()!=0){
            userFrom.id=user.getId();
        }
        if(user.getUserName()!=null){
            userFrom.userName=user.getUserName();
        }
        if(user.getName()!=null){
            userFrom.Name=user.getName();
        }
        if(user.getSex()!=null){
            userFrom.sex=user.getSex();
        }
        if(user.getTel()!=null){
            userFrom.tel=user.getTel();
        }
        if(user.getEmail()!=null){
            userFrom.sex=user.getSex();
        }
        if(user.getEmail()!=null){
            userFrom.email=user.getEmail();
        }
        if(user.getPassword()!=null){
            userFrom.password=user.getPassword();
        }
        if(user.getImagePath()!=null){
            userFrom.imagePath=user.getImagePath();
        }
        if(user.getLastLoginTime()!=null){
            try{
                userFrom.lastLoginTime=dateFormat.format(user.getLastLoginTime());
            }
            catch (Exception e){}

        }
        if(user.isEnabled()!=false){
            userFrom.enabled=user.isEnabled();
        }
        if(user.getDescription()!=null){
            userFrom.description=user.getDescription();
        }

        userFrom.group=null;
        return userFrom;
    }

    public GroupFrom groupToGroupFrom(Group group){
        GroupFrom groupFrom=new GroupFrom();
        if(group.getId()!=0){
            groupFrom.id=group.getId();
        }
        if(group.getGroupName()!=null){
            groupFrom.groupName=group.getGroupName();
        }
        if(group.getDescription()!=null){
            groupFrom.description=group.getDescription();
        }
        if(group.getGroupName()!=null){
            groupFrom.groupName=group.getGroupName();
        }
        if(group.getUsers()!=null){
            List<UserFrom> userFroms=new ArrayList<UserFrom>();
            for(User user:group.getUsers()){
                UserFrom userFrom=new UserFrom();
                userFrom=this.userToUserFormWithoutGroupInfo(user);
                userFroms.add(userFrom);
            }
            groupFrom.users=userFroms;
        }
        if(group.getCreatedDate()!=null){
            try {
                groupFrom.createdDate=dateFormat.format(group.getCreatedDate());
            }
            catch (Exception e){}
        }
        if(group.getLastModifiedDate()!=null){
            try{
                groupFrom.lastModifiedDate=dateFormat.format(group.getLastModifiedDate());
            }
            catch (Exception e){}
        }
        if(group.getIsDel()!=false){
            groupFrom.isDel=group.getIsDel();
        }
        if(group.getGroupList()!=null)
        {
            List<GroupFrom> groupFromList=new ArrayList<GroupFrom>();
            for(Group group1:group.getGroupList()){
                GroupFrom groupFrom1=new GroupFrom();
                if(group1.getId()!=0){
                    groupFrom1.id=group1.getId();
                }
                if(group1.getGroupName()!=null){
                    groupFrom1.groupName=group1.getGroupName();
                }
                if(group1.getDescription()!=null){
                    groupFrom1.description=group1.getDescription();
                }
                if(group1.getCreatedDate()!=null){
                    try {
                        groupFrom1.createdDate=dateFormat.format(group1.getCreatedDate());
                    }
                    catch (Exception e){}
                }
                if(group1.getLastModifiedDate()!=null){
                    try {
                        groupFrom1.lastModifiedDate=dateFormat.format(group1.getLastModifiedDate());
                    }
                    catch (Exception e){}
                }
                if(group1.getIsDel()!=false){
                    groupFrom1.isDel=group1.getIsDel();
                }
                groupFrom1.users=null;
                groupFrom1.groupList=null;
                groupFrom1.group=null;
                groupFromList.add(groupFrom1);
            }
            groupFrom.groupList=groupFromList;
        }
        if(group.getGroup()!=null){//-----group.group
            GroupFrom groupFrom1=new GroupFrom();
            if(group.getGroup().getId()!=0){
                groupFrom1.id=group.getGroup().getId();
            }
            if(group.getGroup().getGroupName()!=null){
                groupFrom1.groupName=group.getGroup().getGroupName();
            }
            if(group.getGroup().getDescription()!=null){
                groupFrom1.description=group.getGroup().getDescription();
            }
            if(group.getGroup().getCreatedDate()!=null){
                try{
                    groupFrom1.createdDate=dateFormat.format(group.getGroup().getCreatedDate());
                }
                catch (Exception e){}

            }
            if(group.getGroup().getLastModifiedDate()!=null){
                try {
                    groupFrom1.lastModifiedDate=dateFormat.format(group.getGroup().getLastModifiedDate());
                }
                catch (Exception e){}

            }
            if(group.getGroup().getIsDel()!=false){
                groupFrom1.isDel=group.getGroup().getIsDel();
            }
            if(group.getGroup().getGroup()!=null){


                GroupFrom groupFrom1_1=new GroupFrom();
                if(group.getGroup().getGroup().getId()!=0){
                    groupFrom1_1.id=group.getGroup().getGroup().getId();
                }
                if(group.getGroup().getGroup().getGroupName()!=null){
                    groupFrom1_1.groupName=group.getGroup().getGroup().getGroupName();
                }
                if(group.getGroup().getGroup().getDescription()!=null){
                    groupFrom1_1.description=group.getGroup().getGroup().getDescription();
                }
                if(group.getGroup().getGroup().getCreatedDate()!=null){
                    try{
                        groupFrom1_1.createdDate=dateFormat.format(group.getGroup().getGroup().getCreatedDate());
                    }
                    catch (Exception e){}

                }
                if(group.getGroup().getGroup().getLastModifiedDate()!=null){
                    try {
                        groupFrom1_1.lastModifiedDate=dateFormat.format(group.getGroup().getGroup().getLastModifiedDate());
                    }
                    catch (Exception e){}

                }
                groupFrom1_1.users=null;
                groupFrom1_1.groupList=null;
                groupFrom1_1.group=null;

                groupFrom1.group=groupFrom1_1;

            }
            groupFrom1.users=null;
            groupFrom1.groupList=null;


            groupFrom.group=groupFrom1;
        }
        return groupFrom;
    }

}
