package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.PredictMmWiring;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by light on 2016/5/17.
 */
@Repository
public interface PredictMmWiringRepository extends PagingAndSortingRepository<PredictMmWiring,Long> {
    public List<PredictMmWiring> findAll();
}
