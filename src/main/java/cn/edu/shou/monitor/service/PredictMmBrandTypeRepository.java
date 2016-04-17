package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmBrandType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/1/15 0015.
 */
@Repository
public interface PredictMmBrandTypeRepository extends PagingAndSortingRepository<predictMmBrandType, Long> {
    public List<predictMmBrandType> findAll();//获取所有品牌
    /*//根据类别获取品牌数据
    @Query("select brand from predictMmBrand brand where brand.brandType=:type")
    public List<predictMmBrandType>findBrandByType(@Param("type") int type);*/
}
