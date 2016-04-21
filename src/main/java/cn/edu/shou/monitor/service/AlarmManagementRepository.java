package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.PredictMmAlarm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
@Repository
public interface AlarmManagementRepository extends PagingAndSortingRepository<PredictMmAlarm,Long> {
    public List<PredictMmAlarm> findAll();

    @Query("select alarm from PredictMmAlarm alarm where alarm.equipTypeElement =:elementId")
    public PredictMmAlarm getEquipName(@Param("elementId") long elementId);//equipTypeName 也是id

}
