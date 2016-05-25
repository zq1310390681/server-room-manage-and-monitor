package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.predictMmStores;
import cn.edu.shou.monitor.domain.missiveDataForm.predictMmStoresForm;
import cn.edu.shou.monitor.service.CsvUtilRepository;
import cn.edu.shou.monitor.service.impl.ZbxHostServiceImpl;
import cn.edu.shou.monitor.service.PredictMmBrandRepository;
import cn.edu.shou.monitor.service.PredictMmEquipmentCabinetRepository;
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
@RequestMapping(value ="/predictCenter/api/store" )
public class StoreManagementApiController {
    @Value("${spring.main.homedir}")
    String homedir;
    @Autowired
    cn.edu.shou.monitor.service.StoreManagementRepository storeManagementRepository;
    @Autowired
    PredictMmBrandRepository brandRepository;
    @Autowired
    PredictMmEquipmentCabinetRepository pmecDAO;
    @Autowired
    CsvUtilRepository csvUtilRepository;
    //获取所有存储设备数据信息
    @RequestMapping(value = "/getAllStores")
    public List<predictMmStores> getAllStores(){
        return storeManagementRepository.findAll();
    }
    //导出所有存储设备数据信息
    @RequestMapping(value = "/getAllStoresExport")
    public List<predictMmStores> getAllStoresExport(){
        List<predictMmStores> storeses = storeManagementRepository.findAll();
        if (storeses != null){
            for (predictMmStores stores : storeses){
                //处理品牌名称
                String brandId = stores.getStoreBrand();
                if (brandId != null){
                    brandId = brandRepository.findOne(Long.parseLong(brandId))==null?"":
                            brandRepository.findOne(Long.parseLong(brandId)).getBrandName();
                    brandId = brandId == ""?"暂无数据":brandId;
                    stores.setStoreBrand(brandId);
                }
                //处理机柜名称
                String cabinetId = stores.getStoreEquipmentCabinet();
                if (cabinetId != null){
                    cabinetId = pmecDAO.findOne(Long.parseLong(cabinetId)) == null ? "":
                            pmecDAO.findOne(Long.parseLong(cabinetId)).getEquipmentCabinetName();
                    cabinetId = cabinetId == ""?"暂无数据":cabinetId;
                    stores.setStoreEquipmentCabinet(cabinetId);
                }
            }
        }
        return storeses;
    }
    //创建存储设备
    @RequestMapping(value = "/createAndUpdateStore",method = RequestMethod.GET)
    public List<predictMmStores> createStore(predictMmStoresForm storesForm) {
        long recordId=storesForm.getId();//获取记录ID
        predictMmStores predictStore=null;
        if(recordId==0){
            predictStore=new predictMmStores();
        }else {
            predictStore= storeManagementRepository.findOne(recordId);
        }
        predictStore.setStoreSN(storesForm.getStoreSN());
        predictStore.setStorePurchasingDate(storesForm.getStorePurchasingDate());
        predictStore.setStoreMaintenanceDueTime(storesForm.getStoreMaintenanceDueTime());
        predictStore.setStoreSerialNumber(storesForm.getStoreSerialNumber());
        predictStore.setStoreBrand(storesForm.getStoreBrand());
        predictStore.setStoreType(storesForm.getStoreType());
        predictStore.setStoreIP(storesForm.getStoreIP());
        predictStore.setStoreEquipmentCabinet(storesForm.getStoreEquipmentCabinet());
        predictStore.setStoreU(storesForm.getStoreU());
        predictStore.setStoreRemark(storesForm.getStoreRemark());
        predictStore.setSMSName("存储设备");
//        storeManagementRepository.save(predictStore);
//        List<predictMmStores> list=new ArrayList<predictMmStores>();
//        list.add(predictStore);
        String createResult=null;
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        List<predictMmStores> list=new ArrayList<predictMmStores>();
        if(recordId==0) {
            createResult=zbxHostService.createHostStore(storesForm.getStoreSerialNumber(), storesForm.getStoreIP());//添加服务
            String hostId = createResult.replaceAll("[^0-9]","");
            predictStore.setHostId(hostId);
            if(createResult.contains("success")){
                storeManagementRepository.save(predictStore);
                list.add(predictStore);
                return list;
            }else {
                storeManagementRepository.save(predictStore);
                list.add(predictStore);
                return list;
            }
        }
        return list;
    }
    //删除存储设备
    @RequestMapping(value = "/deleteStore/{id}")
    public List<predictMmStores> deleteStore(@PathVariable long id){
        predictMmStores predictStore= storeManagementRepository.findOne(id);

        String hostId; //关联ZBX的hostid
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        hostId=predictStore.getHostId();
        zbxHostService.ZbxDeleteServer(hostId);

        storeManagementRepository.delete(predictStore);
        List<predictMmStores> list=new ArrayList<predictMmStores>();
        list.add(predictStore);
        return list;

    }
    //数据导出
    @RequestMapping(value = "/export")
    public ResponseEntity<byte[]> exportData(){
        String filePath=homedir+"/download/";
        String fileName="存储设备.csv";
        List exportDa = new ArrayList<>();
        List<predictMmStores> storeses = getAllStores();//获取到需要导出的数据
        List<String>results=new ArrayList<String>();
        String header="存储设备编号,存储设备S/N号,购买时间,维保到期时间,存储设备品牌,存储设备型号,存储设备IP,所在机柜,所在U,备注";
        for (predictMmStores stores:storeses){
            String values="";
            if (stores.getStoreSerialNumber()!=null){
                values+=stores.getStoreSerialNumber().replace(",","、")+",";
            }else {
                values+=stores.getStoreSerialNumber()+",";
            }
            if (stores.getStoreSN()!=null){
                values+=stores.getStoreSN().replace(",","、")+",";
            }else {
                values+=stores.getStoreSN()+",";
            }
            if (stores.getStorePurchasingDate()!=null){
                values+=stores.getStorePurchasingDate().replace(",","、")+",";
            }else {
                values+=stores.getStorePurchasingDate()+",";
            }
            if (stores.getStoreMaintenanceDueTime()!=null){
                values+=stores.getStoreMaintenanceDueTime().replace(",","、")+",";
            }else {
                values+=stores.getStoreMaintenanceDueTime()+",";
            }
            if (stores.getStoreBrand()!=null){
                values+=stores.getStoreBrand().replace(",","、")+",";
            }else {
                values+=stores.getStoreBrand()+",";
            }
            if (stores.getStoreType()!= null){
                values+=stores.getStoreType().replace(",","、")+",";
            }else {
                values+=stores.getStoreType()+",";
            }
            if (stores.getStoreIP()!=null){
                values+=stores.getStoreIP().replace(",","、")+",";
            }else {
                values+=stores.getStoreIP();
            }
            if (stores.getStoreEquipmentCabinet()!=null){
                values+=stores.getStoreEquipmentCabinet().replace(",","、")+",";
            }else {
                values+=stores.getStoreEquipmentCabinet();
            }
            if (stores.getStoreU()!=null){
                values+=" "+stores.getStoreU().replace(",","、")+",";
            }else {
                values+=" "+stores.getStoreU();
            }
            if (stores.getStoreRemark()!=null){
                values+=stores.getStoreRemark().replace(",","、")+",";
            }else {
                values+=stores.getStoreRemark();
            }

            results.add(values);
        }
        csvUtilRepository.exportCsv(new File(filePath+fileName),results,header);
        ResponseEntity<byte[]>  result = downFile("存储设备.csv");
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
