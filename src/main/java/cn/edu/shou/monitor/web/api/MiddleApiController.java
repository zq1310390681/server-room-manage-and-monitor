package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmMiddleForm;
import cn.edu.shou.monitor.domain.predictMmMiddle;
import cn.edu.shou.monitor.service.MiddleRepository;
import cn.edu.shou.monitor.service.impl.ZbxHostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/15 0015.
 */
@RestController
@RequestMapping(value ="/predictCenter/api/middle" )
public class MiddleApiController {
    @Autowired
    MiddleRepository middleRepository;

    //获取所有中间件数据库数据信息
    @RequestMapping(value = "/getAllMiddle")
    public List<predictMmMiddle> getAllMiddle(){
        return middleRepository.findAll();
    }
    //创建中间件
    @RequestMapping(value = "/createAndUpdateMiddle",method = RequestMethod.GET)
    public List<predictMmMiddle> createAndUpdateMmMiddle(predictMmMiddleForm mmMiddleForm) {
        long recordId=mmMiddleForm.getId();//获取记录ID
        predictMmMiddle mmMiddle= null;
        if(recordId==0){
            mmMiddle=new predictMmMiddle();
        }else {
            mmMiddle=middleRepository.findOne(recordId);
        }
        mmMiddle.setMiddleName(mmMiddleForm.getMiddleName());
        mmMiddle.setMiddleIP(mmMiddleForm.getMiddleIP());
        mmMiddle.setMiddleHost(mmMiddleForm.getMiddleHost());
        mmMiddle.setMiddleUserName(mmMiddleForm.getMiddleUserName());
        mmMiddle.setMiddlePassword(mmMiddleForm.getMiddlePassword());
        mmMiddle.setMiddleOS(mmMiddleForm.getMiddleOS());
        mmMiddle.setMiddleNote(mmMiddleForm.getMiddleNote());
        mmMiddle.setMiddleType(mmMiddleForm.getMiddleType());
        mmMiddle.setMiddleVersion(mmMiddleForm.getMiddleVersion());
        middleRepository.save(mmMiddle);
        List<predictMmMiddle> list=new ArrayList<predictMmMiddle>();
        list.add(mmMiddle);
        return list;

    }
    //删除中间件数据库
    @RequestMapping(value = "/deleteMiddle/{id}")
    public List<predictMmMiddle> deleteOracle(@PathVariable long id){
        predictMmMiddle mmMiddle=middleRepository.findOne(id);

        String hostId; //关联ZBX的hostid
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        hostId=mmMiddle.getHostId();
        zbxHostService.ZbxDeleteServer(hostId);

        middleRepository.delete(mmMiddle);
        List<predictMmMiddle> list=new ArrayList<predictMmMiddle>();
        list.add(mmMiddle);
        return list;

    }
    //根据id集合获取中间件信息
    @RequestMapping(value = "getMiddleByIds/{ids}")
    public List<predictMmMiddle>getMiddleByIds(@PathVariable String ids){
        List<predictMmMiddle> middles=new ArrayList<predictMmMiddle>();
        if (ids!=null && ids!="" && !ids.contains("null")){
            String [] middleIds=ids.split(",");//拆分中间件ids
            for (String id:middleIds){
                predictMmMiddle middle=new predictMmMiddle();
                middle=middleRepository.findOne(Long.parseLong(id));
                middles.add(middle);
            }
        }
        return middles;
    }
    //根据中间件类型获得中间件  middleType=1代表iis 2代表tomact 3代表sql 4代表oracle 5代表java
    @RequestMapping(value = "/getMiddleType/{middleType}")
    public List<predictMmMiddle>getMiddleType(@PathVariable String middleType){
        List middles=middleRepository.getMiddleByMiddleType(middleType);
        return middles;
    }
    //根据中间件ID字符串获取名称
    @RequestMapping(value = "/getMiddleNamesByIds/{Id}")
    public String getMiddleNamesByIds(@PathVariable String Id){
        String [] ids=Id.split(",");
        String middleName="";
        for (int i=0;i<ids.length;++i){
            middleName+=middleRepository.findOne(Long.parseLong(ids[i])).getMiddleName()+",";
        }
        middleName=middleName.substring(0,middleName.length()-1);
        return middleName;
    }

}

