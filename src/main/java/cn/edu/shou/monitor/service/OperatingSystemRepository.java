package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmOperatingSystem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/1/18.
 */
@Repository
public interface OperatingSystemRepository extends PagingAndSortingRepository<predictMmOperatingSystem,Long> {
    //获取操作系统列表
    List<predictMmOperatingSystem> findAll();//获取操作系统
    @Query("select os from predictMmOperatingSystem os where os.operatingSystem=:opeSys")
    public predictMmOperatingSystem getOsByOpeSys(@Param("opeSys") String opeSys);
}
