package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmBrand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/1/15 0015.
 */
@Repository
public interface PredictMmBrandRepository extends PagingAndSortingRepository<predictMmBrand, Long> {
    public List<predictMmBrand> findAll();//获取所有品牌
    //根据类别获取品牌数据
    @Query("select brand from predictMmBrand brand where brand.brandType=:type")
    public List<predictMmBrand>findBrandByType(@Param("type") int type);
}
