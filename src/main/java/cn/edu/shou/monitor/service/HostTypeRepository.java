package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.PredictMmHostType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/1/19.
 */
@Repository
public interface HostTypeRepository extends PagingAndSortingRepository<PredictMmHostType,Long> {
    //获取主机类型列表
    List<PredictMmHostType> findAll();//获取主机类型
}
