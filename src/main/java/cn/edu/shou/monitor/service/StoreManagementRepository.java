package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmStores;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@Repository
public interface StoreManagementRepository extends PagingAndSortingRepository<predictMmStores, Long> {
    public List<predictMmStores> findAll();
    @Query("select store from predictMmStores store where store.storeSerialNumber =:storeSerialNumber")
    public predictMmStores getStoreByName(@Param("storeSerialNumber") String storeSerialNumber);
}
