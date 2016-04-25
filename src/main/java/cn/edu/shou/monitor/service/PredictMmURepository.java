package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmU;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/1/15 0015.
 */
@Repository
public interface predictMmURepository extends PagingAndSortingRepository<predictMmU, Long> {
    public List<predictMmU> findAll();//获取所有U

}
