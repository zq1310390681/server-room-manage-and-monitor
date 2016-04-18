package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.Group;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by XQQ on 2014/7/25.
 */
//@RepositoryRestResource(collectionResourceRel = "group", path = "group")
@Repository
public interface GroupRepository extends PagingAndSortingRepository<Group,Long> {

    //public Group findByGroupName(String group);
    public List<Group> findAll();

    public Group findByGroupName(String group);
    @Modifying
    @Transactional
    @Query("update User u set u.group.id=0 where u.group.id=?1")
    public void updateUserGroupByID(Long groupId);

    @Query("select g from Group g where g.id <>1 and g.id<>0")
    public List<Group>findAllGroups();


    @Query(value = "select g from Group g where g.group.groupName = ?1")
    public List<Group> getGroupListByGroup(String groupName);

    @Query(value = "select g from Group g where g.groupList IS EMPTY")
    public List<Group> getAllLeafGroup();

    @Query(value = "select g from Group g where g.group.id = ?1")
    public Group getGroupListByGroupID(Long id);
}
