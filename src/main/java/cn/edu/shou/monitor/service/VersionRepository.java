package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmVersion;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/1/19.
 */
@Repository
public interface VersionRepository extends PagingAndSortingRepository<predictMmVersion,Long> {
    //获取版本信息列表
    List<predictMmVersion> findAll();//获取版本信息
}
