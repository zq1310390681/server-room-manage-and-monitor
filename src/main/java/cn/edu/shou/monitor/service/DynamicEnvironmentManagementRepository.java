package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmDynamicEnvironment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/3/6.
 */
@Repository
public interface DynamicEnvironmentManagementRepository extends PagingAndSortingRepository<predictMmDynamicEnvironment,Long> {
    public List<predictMmDynamicEnvironment> findAll();
}
