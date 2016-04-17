package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmBrandForm;
import cn.edu.shou.monitor.domain.predictMmBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@RestController
@RequestMapping(value ="/predictCenter/api/systemBrand" )
public class SystemBrandManagementApiController {
    @Autowired
    cn.edu.shou.monitor.service.PredictMmBrandRepository predictMmBrandRepository;
    //获取所有品牌数据信息
    @RequestMapping(value = "/getAllSystemBrands")
    public List<predictMmBrand> getAllSystemBrands(){
        return predictMmBrandRepository.findAll();
    }
    //创建品牌
    @RequestMapping(value = "/createAndUpdateSystemBrand",method = RequestMethod.GET)
    public List<predictMmBrand> createAndUpdateSystemBrand(predictMmBrandForm systemBrandForm) {
        long recordId=systemBrandForm.getId();//获取记录ID
        predictMmBrand predictSystemBrand=null;
        if(recordId==0){
            predictSystemBrand=new predictMmBrand();
        }else {
            predictSystemBrand=predictMmBrandRepository.findOne(recordId);
        }
        predictSystemBrand.setBrandName(systemBrandForm.getBrandName());
        predictSystemBrand.setBrandType(systemBrandForm.getBrandType());
        predictMmBrandRepository.save(predictSystemBrand);
        List<predictMmBrand> list=new ArrayList<predictMmBrand>();
        list.add(predictSystemBrand);
        return list;
    }
    //删除品牌
    @RequestMapping(value = "/deleteSystemBrand/{id}")
    public List<predictMmBrand> deleteSystemBrand(@PathVariable long id){
        predictMmBrand predictSystemBrand=predictMmBrandRepository.findOne(id);
        predictMmBrandRepository.delete(predictSystemBrand);
        List<predictMmBrand> list=new ArrayList<predictMmBrand>();
        list.add(predictSystemBrand);
        return list;

    }
}
