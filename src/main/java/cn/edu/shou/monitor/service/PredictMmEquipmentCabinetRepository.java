package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmEquipmentCabinet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/1/15 0015.
 */
@Repository
public interface PredictMmEquipmentCabinetRepository extends PagingAndSortingRepository<predictMmEquipmentCabinet, Long> {
    public List<predictMmEquipmentCabinet> findAll();//获取所有机柜
    //查询机柜信息
    @Query("select cabinet from predictMmEquipmentCabinet cabinet where cabinet.equipmentCabinetName=:equCabinet")
    public predictMmEquipmentCabinet getCabByName(@Param("equCabinet") String equCabinet);
}
