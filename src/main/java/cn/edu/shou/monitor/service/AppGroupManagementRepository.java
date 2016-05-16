package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.PredictMmAppGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sqhe18 on 2016/4/4.
 */
@Repository
public interface AppGroupManagementRepository extends PagingAndSortingRepository<PredictMmAppGroup,Long> {
    public List<PredictMmAppGroup>findAll();
    //根据分组排序 查找是否已有
    @Query("select appGroup from PredictMmAppGroup appGroup where appGroup.groupOrder=:groupOrder")
    public PredictMmAppGroup getAppGroupByOrder(@Param("groupOrder") long groupOrder);

    //根据分组名 查找是否已有
    @Query("select appGroup from PredictMmAppGroup appGroup where appGroup.appGroupName=:appGroupName")
    public PredictMmAppGroup getAppGroupByName(@Param("appGroupName") String appGroupName);

}
