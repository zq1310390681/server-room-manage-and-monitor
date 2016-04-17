package cn.edu.shou.monitor.service;


import cn.edu.shou.monitor.domain.predictMmServiceObjectAndApplication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sqhe18 on 2016/3/27.
 */
@Repository
public interface ServiceObjectAndApplicationRepository extends PagingAndSortingRepository<predictMmServiceObjectAndApplication,Long> {
    public List<predictMmServiceObjectAndApplication> findAll();

    @Query("select serObjApp from predictMmServiceObjectAndApplication serObjApp where serObjApp.applicationId=:applicationId")
    public predictMmServiceObjectAndApplication getServerObjAndApp(@Param("applicationId") long applicationId);//根据应用ID获取中间对象
}
