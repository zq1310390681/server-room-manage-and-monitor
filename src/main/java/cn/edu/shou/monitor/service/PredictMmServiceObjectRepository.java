package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmServiceObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/1/15 0015.
 */
@Repository
public interface PredictMmServiceObjectRepository extends PagingAndSortingRepository<predictMmServiceObject, Long> {
    public List<predictMmServiceObject> findAll();//获取所有服务对象

}
