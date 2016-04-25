package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmAppGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sqhe18 on 2016/4/4.
 */
@Repository
public interface AppGroupManagementRepository extends PagingAndSortingRepository<predictMmAppGroup,Long> {
    public List<predictMmAppGroup>findAll();
    //根据分组排序 查找是否已有
    @Query("select appGroup from predictMmAppGroup appGroup where appGroup.groupOrder=:groupOrder")
    public predictMmAppGroup getAppGroupByOrder(@Param("groupOrder") long groupOrder);

    //根据分组名 查找是否已有
    @Query("select appGroup from predictMmAppGroup appGroup where appGroup.appGroupName=:appGroupName")
    public predictMmAppGroup getAppGroupByName(@Param("appGroupName") String appGroupName);

}
