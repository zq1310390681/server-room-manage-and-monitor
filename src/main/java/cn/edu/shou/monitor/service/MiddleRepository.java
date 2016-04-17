package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmMiddle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/3/15 0015.
 */
@Repository
public interface MiddleRepository extends PagingAndSortingRepository<predictMmMiddle,Long> {
    List<predictMmMiddle> findAll();
    @Query("select middle from predictMmMiddle middle where middle.middleType=:middleType")
    public List<predictMmMiddle>getMiddleByMiddleType(@Param("middleType") String middleType);
}
