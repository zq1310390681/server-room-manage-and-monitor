package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmSwitchboards;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@Repository
public interface SwitchboardManagementRepository extends PagingAndSortingRepository<predictMmSwitchboards, Long> {
    public List<predictMmSwitchboards> findAll();
}
