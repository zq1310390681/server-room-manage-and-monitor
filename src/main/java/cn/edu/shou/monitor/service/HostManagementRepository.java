package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmHost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/1/19.
 */
@Repository
public interface HostManagementRepository extends PagingAndSortingRepository<predictMmHost,Long> {
    //获取主机列表
    List<predictMmHost> findAll();//获取主机
    //获取VMWare
    @Query("select host from predictMmHost host where host.hostType=:hostType")
    public List<predictMmHost>getHostsByHostType(@Param("hostType") String hostType);

    //根据服务器Id查找主机信息
    @Query("select hosts from predictMmHost hosts where hosts.hostServer =:serverId")
    public List<predictMmHost> getHostBySeverId(@Param("serverId") String serverId);
}
