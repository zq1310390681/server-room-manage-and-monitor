package cn.edu.shou.monitor.service.zbxapi;

import java.util.List;

import cn.edu.shou.monitor.domain.application.ApplicationGetRequest;
import cn.edu.shou.monitor.domain.base.Application;

public interface IApplicationService {

    public Object get(ApplicationGetRequest get);

    List<Application> getApplicationToBean(ApplicationGetRequest get);
}
