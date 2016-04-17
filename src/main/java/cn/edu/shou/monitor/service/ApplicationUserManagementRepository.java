package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmApplicationUser;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Administrator on 2016/3/6.
 */
public interface ApplicationUserManagementRepository extends PagingAndSortingRepository<predictMmApplicationUser,Long> {
    public List<predictMmApplicationUser> findAll();
}