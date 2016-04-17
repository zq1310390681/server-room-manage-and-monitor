package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmPing;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/1/12.
 */
@Repository
public interface PingManagementRepository extends PagingAndSortingRepository<predictMmPing,Long> {
    public List<predictMmPing> findAll();
}
