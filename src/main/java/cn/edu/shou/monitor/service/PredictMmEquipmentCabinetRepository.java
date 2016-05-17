package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.PredictMmEquipmentCabinet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/1/15 0015.
 */
@Repository
public interface PredictMmEquipmentCabinetRepository extends PagingAndSortingRepository<PredictMmEquipmentCabinet, Long> {
    public List<PredictMmEquipmentCabinet> findAll();//获取所有机柜
    //查询机柜信息
    @Query("select cabinet from PredictMmEquipmentCabinet cabinet where cabinet.equipmentCabinetName=:equCabinet")
    public PredictMmEquipmentCabinet getCabByName(@Param("equCabinet") String equCabinet);
}
