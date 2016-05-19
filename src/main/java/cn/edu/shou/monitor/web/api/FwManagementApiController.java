package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.predictMmFws;
import cn.edu.shou.monitor.domain.missiveDataForm.predictMmFwsForm;
import cn.edu.shou.monitor.service.CsvUtilRepository;
import cn.edu.shou.monitor.service.PredictMmEquipmentCabinetRepository;
import cn.edu.shou.monitor.service.impl.ZbxHostServiceImpl;
import cn.edu.shou.monitor.service.PredictMmBrandRepository;
import cn.edu.shou.monitor.web.FileOperate;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/30 0030.
 */
@RestController
@RequestMapping(value ="/predictCenter/api/fw" )
public class FwManagementApiController {
    @Value("${spring.main.homedir}")
    String homedir;
    @Autowired
    cn.edu.shou.monitor.service.FwManagementRepository FwManagementRepository;
    @Autowired
    CsvUtilRepository csvUtilRepository;
    @Autowired
    PredictMmBrandRepository brandRepository;
    @Autowired
    PredictMmEquipmentCabinetRepository pmecDAO;
    //获取所有防火墙数据信息
    @RequestMapping(value = "/getAllFws")
    public List<predictMmFws> getAllFws(){
        return FwManagementRepository.findAll();
    }
    public List<predictMmFws> export(){
        List<predictMmFws> fwses = FwManagementRepository.findAll();
        if (fwses != null){
           for (predictMmFws fws : fwses){
               //处理品牌名称
               String brandId = fws.getFwBrand();
               if (brandId != null){
                   brandId = brandRepository.findOne(Long.parseLong(brandId))==null?"":
                           brandRepository.findOne(Long.parseLong(brandId)).getBrandName();
                   brandId = brandId ==null?"暂无数据":brandId;
               }
           }
        }
        return fwses;
    }
    //创建防火墙
    @RequestMapping(value = "/createAndUpdateFw",method = RequestMethod.GET)
    public List<predictMmFws> createFw(predictMmFwsForm fwsForm) {
        long recordId=fwsForm.getId();//获取记录ID
        predictMmFws predictFw=null;
        if(recordId==0){
            predictFw=new predictMmFws();
        }else {
            predictFw=FwManagementRepository.findOne(recordId);
        }
        predictFw.setFwSerialNumber(fwsForm.getFwSerialNumber());
        predictFw.setFwSN(fwsForm.getFwSN());
        predictFw.setFwPurchasingDate(fwsForm.getFwPurchasingDate());
        predictFw.setFwMaintenanceDueTime(fwsForm.getFwMaintenanceDueTime());
        predictFw.setFwBrand(fwsForm.getFwBrand());
        predictFw.setFwType(fwsForm.getFwType());
        predictFw.setFwIP(fwsForm.getFwIP());
        predictFw.setFwSNMP(fwsForm.getFwSNMP());
        predictFw.setFwPort(fwsForm.getFwPort());
        predictFw.setFwRemark(fwsForm.getFwRemark());

        String createResult=null;
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        List<predictMmFws> list=new ArrayList<predictMmFws>();
        if(recordId==0) {
            createResult=zbxHostService.createHostFw(fwsForm.getFwSerialNumber(),fwsForm.getFwIP());//添加服务
            String hostId = createResult.replaceAll("[^0-9]","");
            predictFw.setHostId(hostId);
            if(createResult.contains("success")){
                FwManagementRepository.save(predictFw);
                list.add(predictFw);
                return list;
            }else {
                FwManagementRepository.save(predictFw);
                list.add(predictFw);
                return list;
            }
        }
        return list;
//        FwManagementRepository.save(predictFw);
//        List<predictMmFws> list=new ArrayList<predictMmFws>();
//        list.add(predictFw);
//        return list;
    }
    //删除防火墙
    @RequestMapping(value = "/deleteFw/{id}")
    public List<predictMmFws> deleteFw(@PathVariable long id){
        predictMmFws predictFw=FwManagementRepository.findOne(id);
        String hostId; //关联ZBX的hostid
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        hostId=predictFw.getHostId();
        zbxHostService.ZbxDeleteServer(hostId);
        FwManagementRepository.delete(predictFw);
        List<predictMmFws> list=new ArrayList<predictMmFws>();
        list.add(predictFw);
        return list;
    }
    //数据导出
    @RequestMapping(value = "/export")
    public ResponseEntity<byte[]> exportData(){
        String filePath=homedir+"/download/";
        String fileName="防火墙.csv";
        List exportDa = new ArrayList<>();
        List<predictMmFws> fwses = getAllFws();//获取到需要导出的数据
        List<String>results=new ArrayList<String>();
        String header="防火墙编号,防火墙S/N号,购买时间,维保到期时间,防火墙品牌,防火墙型号,防火墙IP,SNMP,端口号,备注";
        for (predictMmFws fws:fwses){
            String values="";
            if (fws.getFwSerialNumber()!=null){
                values+=fws.getFwSerialNumber().replace(",","、")+",";
            }else {
                values+=fws.getFwSerialNumber()+",";
            }
            if (fws.getFwSN()!=null){
                values+=fws.getFwSN().replace(",","、")+",";
            }else {
                values+=fws.getFwSN()+",";
            }
            if (fws.getFwPurchasingDate()!=null){
                values+=fws.getFwPurchasingDate().replace(",","、")+",";
            }else {
                values+=fws.getFwPurchasingDate()+",";
            }
            if (fws.getFwMaintenanceDueTime()!=null){
                values+=fws.getFwMaintenanceDueTime().replace(",","、")+",";
            }else {
                values+=fws.getFwMaintenanceDueTime()+",";
            }
            if (fws.getFwBrand()!=null){
                values+=fws.getFwBrand().replace(",","、")+",";
            }else {
                values+=fws.getFwBrand()+",";
            }
            if (fws.getFwType()!=null){
                values+=fws.getFwType().replace(",","、")+",";
            }else {
                values+=fws.getFwType();
            }
            if (fws.getFwIP()!=null){
                values+=fws.getFwIP().replace(",","、")+",";
            }else {
                values+=fws.getFwIP();
            }
            if (fws.getFwSNMP()!=null){
                values+=fws.getFwSNMP().replace(",","、")+",";
            }else {
                values+=fws.getFwSNMP();
            }
            if (fws.getFwPort()!=null){
                values+=fws.getFwPort().replace(",","、")+",";
            }else {
                values+=fws.getFwPort();
            }
            if (fws.getFwRemark()!=null){
                values+=fws.getFwRemark().replace(",","、")+",";
            }else {
                values+=fws.getFwRemark();
            }
            results.add(values);
        }
        csvUtilRepository.exportCsv(new File(filePath+fileName),results,header);
        ResponseEntity<byte[]>  result = downFile("防火墙.csv");
        return result;
    }
    public ResponseEntity<byte[]> downFile(String fileName)  {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //String path = req.getSession().getServletContext().getRealPath("/");
        String filePath=homedir+"/download/"+fileName;//文件路径
        try {
            //filePath = URLEncoder.encode(filePath, "UTF-8");//转码解决IE下文件名乱码问题
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Http响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", filePath);
        if (!FileOperate.exitFile(filePath)){
            return null;
        }
        try {
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(filePath)),
                    headers,
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            //日志
        }
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "error.txt");
        return new ResponseEntity<byte[]>("文件不存在.".getBytes(), headers, HttpStatus.OK);
    }
}
