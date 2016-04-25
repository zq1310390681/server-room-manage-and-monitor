package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmPingForm;
import cn.edu.shou.monitor.domain.predictMmPing;
import cn.edu.shou.monitor.service.impl.ZbxHostServiceImpl;
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
 * Created by Administrator on 2016/1/12.
 */
@RestController
@RequestMapping(value ="/predictCenter/api/ping")
public class PingManagementApiController {
    @Autowired
    cn.edu.shou.monitor.service.PingManagementRepository PingManagementRepository;
    //获取所有Tomcat数据信息
    @RequestMapping(value = "/getAllPing")
    public List<predictMmPing> getAllPing(){
        return PingManagementRepository.findAll();
    }
    //创建Tomcat
    @RequestMapping(value = "/createAndUpdatePing",method = RequestMethod.GET)
    public List<predictMmPing> createAndUpdatePing(predictMmPingForm pingForm) {
        long recordId=pingForm.getId();//获取记录ID
        predictMmPing predictPing=null;
        if (recordId==0){
            predictPing=new predictMmPing();
        }else {
            predictPing=PingManagementRepository.findOne(recordId);
        }
        predictPing.setPingName(pingForm.getPingName());
        predictPing.setOneIP(pingForm.getOneIP());
        predictPing.setTwoIP(pingForm.getTwoIP());
        predictPing.setPingNote(pingForm.getPingNote());

        String createResult=null;
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        List<predictMmPing> list=new ArrayList<predictMmPing>();
        if(recordId==0) {
            createResult=zbxHostService.createHostPing(pingForm.getPingName(), pingForm.getOneIP());//添加服务
            String hostId = createResult.replaceAll("[^0-9]","");
            predictPing.setHostId(hostId);
            if(createResult.contains("success")){
                PingManagementRepository.save(predictPing);
                list.add(predictPing);
                return list;
            }else {
                return list;
            }
        }
        return list;
    }
    //删除
    @RequestMapping(value = "/deletePing/{id}")
    public List<predictMmPing> deleteSurveillancePro(@PathVariable long id){
        predictMmPing predictPing=PingManagementRepository.findOne(id);

        String hostId; //关联ZBX的hostid
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        hostId=predictPing.getHostId();
        zbxHostService.ZbxDeleteServer(hostId);

        PingManagementRepository.delete(predictPing);
        List<predictMmPing> list=new ArrayList<predictMmPing>();
        list.add(predictPing);
        return list;
    }
    @RequestMapping(value = "/cabinetExit/{name}")
    public Map<String,Boolean> cabinetExit(@PathVariable String name){
        predictMmPing ping = new predictMmPing();
        Map<String,Boolean> results = new HashMap<String ,Boolean>();
        boolean nameExist = false;// 网络名称是否存在
        ping = PingManagementRepository.getPingByName(name);
        if(ping == null){
            nameExist=true;
            results.put("name",nameExist);
        }else{
            results.put("name",false);
        }
        return results;
    }
}
