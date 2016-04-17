package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmServiceObject;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Administrator on 2016/3/6.
 */
public interface ServiceObjectManagementRepository extends PagingAndSortingRepository<predictMmServiceObject,Long> {
    public List<predictMmServiceObject> findAll();
}