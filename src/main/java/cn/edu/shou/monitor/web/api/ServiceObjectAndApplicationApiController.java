package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.predictMmApplications;
import cn.edu.shou.monitor.domain.predictMmServiceObject;
import cn.edu.shou.monitor.domain.predictMmServiceObjectAndApplication;
import cn.edu.shou.monitor.domain.predictVoApplicationService;
import cn.edu.shou.monitor.service.ApplicationManagementRepository;
import cn.edu.shou.monitor.service.ServiceObjectAndApplicationRepository;
import cn.edu.shou.monitor.service.ServiceObjectManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sqhe18 on 2016/3/27.
 */
@RestController
@RequestMapping(value ="/predictCenter/api/serviceObjectAndApplication")
public class ServiceObjectAndApplicationApiController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ApplicationManagementRepository applicationManagementRepository;
    @Autowired
    ServiceObjectAndApplicationRepository serviceObjectAndApplicationRepository;
    @Autowired
    ServiceObjectManagementRepository SODao;

    @RequestMapping(value = "/getPredictMmServiceObject/{id}")
   public  List<Map<String,Object>> getPredictMmServiceObject(@PathVariable("id") long id) {
        String sql ="select * from predict_mm_service_object so  where so.id in " +"( select oa.service_object_id FROM predict_mm_service_object_and_application oa where oa.application_id = "+id+")";
        List<Map<String,Object>> list;
        list = jdbcTemplate.queryForList(sql);

        List<predictMmServiceObject> serviceObjects = new ArrayList<predictMmServiceObject>();

       /* for (Map<String,Object> map:list){
            predictMmServiceObject ps = new predictMmServiceObject();
            ps.setId((Long) map.get("id"));
        }*/
        return list;
    }

    @RequestMapping(value = "/getPredictVoApplicationService")
    public Map<String,Object> getPredictVoApplicationService() {
        List<predictVoApplicationService> voApplicationServices = new ArrayList<predictVoApplicationService>();
        List<predictMmApplications> appList = applicationManagementRepository.findAll();
        List<predictMmServiceObject> soList = SODao.findAll();
        List<predictMmServiceObjectAndApplication> relist = serviceObjectAndApplicationRepository.findAll();

        List<String> appNameAndSONameList = new ArrayList<String>();
        List<List<Integer>> reAppAndSOList = new ArrayList<List<Integer>>();
        Map<String,Integer> appAndSoKey = new HashMap<String, Integer>();
        Map<String,Object> resultMap = new HashMap<String, Object>();

        for(predictMmServiceObjectAndApplication soapp : relist){
            String key = "app"+soapp.getApplicationId()+"so"+soapp.getServiceObjectId();
            appAndSoKey.put(key,0);
        }

        //得到app
        for(predictMmApplications app : appList){
            appNameAndSONameList.add(app.getApplicationName());
        }
        //得到服务对象
        for(predictMmServiceObject so:soList){
            appNameAndSONameList.add(so.getServiceObjectName());
        }

        resultMap.put("appsoname",appNameAndSONameList);

        for (int i=0;i<appNameAndSONameList.size();i++)
        {
            List<Integer> result = new ArrayList<Integer>();
            for (int j=0;j<appNameAndSONameList.size();j++)
            {
                if(i<appList.size()&&j<appList.size())
                {
                    result.add(0);
                }
                if(i>=appList.size()&&j>=appList.size())
                {
                    result.add(0);
                }
                if(i<appList.size()&&j>=appList.size())
                {
                    String reSOandAppKey = "app" + appList.get(i).getId() + "so" + soList.get(j-appList.size()).getId();
                    if (appAndSoKey.containsKey(reSOandAppKey))
                    {
                        result.add(1);
                    }else{
                        result.add(0);
                    }
                }
                if(i>=appList.size()&&j<appList.size()){

                    String reSOandAppKey = "app" + appList.get(j).getId() + "so" + soList.get(i-appList.size()).getId();
                    if (appAndSoKey.containsKey(reSOandAppKey))
                    {
                        result.add(1);
                    }else{
                        result.add(0);
                    }
                }
            }
            reAppAndSOList.add(result);
        }


        resultMap.put("mt",reAppAndSOList);

        return resultMap;
    }
}
