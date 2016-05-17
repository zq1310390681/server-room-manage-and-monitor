package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.predictMmKvm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by sqhe18 on 2016/5/9.
 */
public interface KvmRepository extends PagingAndSortingRepository<predictMmKvm,Long> {
    List<predictMmKvm> findAll();
    //根据KVM编号 查找是否已有
    @Query("select kvm from predictMmKvm kvm where kvm.kvmNum=:kvmNum")
    public predictMmKvm getKvmByName(@Param("kvmNum") String kvmNum);
}
