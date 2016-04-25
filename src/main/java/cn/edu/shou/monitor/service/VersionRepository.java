package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmVersion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/1/19.
 */
@Repository
public interface VersionRepository extends PagingAndSortingRepository<predictMmVersion,Long> {
    //获取版本信息列表
    List<predictMmVersion> findAll();//获取版本信息
    //查询版本信息
    @Query("select  version from predictMmVersion version where version.versions=:versions ")
    public predictMmVersion getVersionByVer(@Param("versions") String versions);
}
