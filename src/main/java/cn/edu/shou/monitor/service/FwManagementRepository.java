package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmFws;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@Repository
public interface FwManagementRepository extends PagingAndSortingRepository<predictMmFws, Long> {
    public List<predictMmFws> findAll();

    @Query("select Fws from predictMmFws Fws")
    public List<predictMmFws>getAllFws();
}
