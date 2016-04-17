package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.Authorities;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by sqhe on 14-8-13.
 */
@Repository
public interface AuthoritiesRepository extends PagingAndSortingRepository<Authorities,Long> {
}
