package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmVersionForm;
import cn.edu.shou.monitor.domain.predictMmVersion;
import cn.edu.shou.monitor.service.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sqhe18 on 2016/4/5.
 */
@RestController
@RequestMapping(value = "/predictCenter/api/version")
public class VersionManagementApiController {
    @Autowired
    VersionRepository versionRepository;
    //获取所有的版本信息
    @RequestMapping(value = "/getAllVersion")
    public List<predictMmVersion> getAllVersion(){
        return versionRepository.findAll();
    }
    @RequestMapping(value = "/creatAndUpdateVersion",method = RequestMethod.GET)
    public List<predictMmVersion> creatAndUpdateVersion(predictMmVersionForm versionForm){
        long recordId=versionForm.getId();
        predictMmVersion predictVersion=null;
        if(recordId==0){
            predictVersion=new predictMmVersion();
        }else {
            predictVersion=versionRepository.findOne(recordId);
        }
        predictVersion.setVersions(versionForm.getVersions());
        List<predictMmVersion> list=new ArrayList<predictMmVersion>();
        versionRepository.save(predictVersion);
        list.add(predictVersion);
        return list;
    }
    @RequestMapping(value = "/deleteVersion/{id}")
    public List<predictMmVersion>deleteVersion(@PathVariable long id){
        predictMmVersion predictVersion = versionRepository.findOne(id);
        versionRepository.delete(predictVersion);
        List<predictMmVersion> list = new ArrayList<predictMmVersion>();
        list.add(predictVersion);
        return list;
    }
    @RequestMapping(value = "/versionExit/{version}")
    public Map<String ,Boolean> versionExit(@PathVariable String version){
        predictMmVersion versions = new predictMmVersion();
        Map<String ,Boolean> results = new HashMap<String ,Boolean>();
        boolean versionExit = false;//版本是否已经存在
        versions = versionRepository.getVersionByVer(version);
        if (versions == null){
            versionExit = true;
            results.put("version",versionExit);
        }else {
            results.put("version",false);
        }
        return results;
    }
}
