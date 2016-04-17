package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmEquipType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by Administrator on 2016/1/17.
 */
@Repository
public interface EquipTypeRepository extends PagingAndSortingRepository<predictMmEquipType,Long> {
    //获取设备类型数据
    @Query("select equip from predictMmEquipType equip where equip.parentId=:typeId")
    public List<predictMmEquipType>getEquipTypes(@Param("typeId") long typeId);//获取设备类型

    //获取设备要素数据
    @Query("select equip from predictMmEquipType equip where equip.parentId<>0")
    public List<predictMmEquipType>getEquipElements();//获取设备要素
}
