package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.PredictMmAlarmGrade;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sqhe18 on 2016/4/18.
 */
@Repository
public interface AlarmGradeRepository extends PagingAndSortingRepository<PredictMmAlarmGrade,Long> {
    public List<PredictMmAlarmGrade> findAll();
}
