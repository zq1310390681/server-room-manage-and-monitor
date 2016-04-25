package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmApplications;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@Repository
public interface ApplicationManagementRepository extends PagingAndSortingRepository<predictMmApplications, Long> {
    public List<predictMmApplications> findAll();
    //根据主机ID模糊查找应用信息
    @Query("select app from predictMmApplications app where app.applicationHost like:hostId")
    public List<predictMmApplications>getApplicationsByHostId(@Param("hostId") String hostId);


}
