package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.PredictMmAlarm;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
@Repository
public interface AlarmManagementRepository extends PagingAndSortingRepository<PredictMmAlarm,Long> {
    public List<PredictMmAlarm> findAll();
}
