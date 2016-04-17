package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmMiddleware;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/1/15 0015.
 */
@Repository
public interface PredictMmMiddlewareRepository extends PagingAndSortingRepository<predictMmMiddleware, Long> {
    //获取中间件类型数据
    @Query("select Middleware from predictMmMiddleware Middleware where Middleware.parentId=:typeId")
    public List<predictMmMiddleware>getMiddlewareTypes(@Param("typeId") long typeId);//获取中间件类型
    //获取中间件名称数据
    @Query("select Middleware from predictMmMiddleware Middleware where Middleware.parentId<>0")
    public List<predictMmMiddleware>getMiddlewareNames();//获取中间件名称

}
