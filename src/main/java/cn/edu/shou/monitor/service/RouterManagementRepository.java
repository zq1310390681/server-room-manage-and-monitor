package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmRouters;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/1/5 0005.
 */
@Repository
public interface RouterManagementRepository extends PagingAndSortingRepository<predictMmRouters, Long> {
    public List<predictMmRouters> findAll();
}
