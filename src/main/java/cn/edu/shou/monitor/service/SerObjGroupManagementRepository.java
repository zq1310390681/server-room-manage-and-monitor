package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmServiceObjGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sqhe18 on 2016/4/4.
 */
@Repository
public interface SerObjGroupManagementRepository extends PagingAndSortingRepository<predictMmServiceObjGroup,Long> {
        public List<predictMmServiceObjGroup> findAll();
    @Query("select serObjGroup from predictMmServiceObjGroup serObjGroup where serObjGroup.groupOrder=:groupOrder")
    public predictMmServiceObjGroup getSerObjByOrder(@Param("groupOrder") long groupOrder);

    @Query("select serObjGroup from predictMmServiceObjGroup serObjGroup where serObjGroup.serObjGroupName=:serObjGroupName")
    public predictMmServiceObjGroup getSerObjByName(@Param("serObjGroupName") String serObjGroupName);

}
