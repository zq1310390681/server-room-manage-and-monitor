package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmEquipmentCabinet;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/1/15 0015.
 */
@Repository
public interface PredictMmEquipmentCabinetRepository extends PagingAndSortingRepository<predictMmEquipmentCabinet, Long> {
    public List<predictMmEquipmentCabinet> findAll();//获取所有机柜
}
