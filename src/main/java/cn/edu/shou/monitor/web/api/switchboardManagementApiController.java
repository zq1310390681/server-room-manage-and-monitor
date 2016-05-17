package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmSwitchboardsForm;
import cn.edu.shou.monitor.domain.predictMmSwitchboards;
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
@RequestMapping(value ="/predictCenter/api/switchboard" )
public class switchboardManagementApiController {
    @Value("${spring.main.homedir}")
    String homedir;
    @Autowired
    cn.edu.shou.monitor.service.SwitchboardManagementRepository SwitchboardManagementRepository;
    @Autowired
    CsvUtilRepository csvUtilRepository;
    @Autowired
    PredictMmBrandRepository brandRepository;
    @Autowired
    PredictMmEquipmentCabinetRepository pmecDAO;
    //获取所有交换机数据信息
    @RequestMapping(value = "/getAllSwitchboards")
    public List<predictMmSwitchboards> getAllSwitchboards(){
        List<predictMmSwitchboards> switchboardses = SwitchboardManagementRepository.findAll();
        if (switchboardses != null){
            for (predictMmSwitchboards switchboards : switchboardses){
                //处理品牌
                String brandId = switchboards.getSwitchboardBrand();
                if (brandId != null){
                    brandId = brandRepository.findOne(Long.parseLong(brandId)) == null ?"":
                            brandRepository.findOne(Long.parseLong(brandId)).getBrandName();
                    brandId = brandId == ""?"暂无数据":brandId;
                    switchboards.setSwitchboardBrand(brandId);
                }
                //处理机柜
                String cabinetId = switchboards.getSwitchboardEquipmentCabinet();
                if (cabinetId != null){
                    cabinetId = pmecDAO.findOne(Long.parseLong(cabinetId)) == null ? "":
                            pmecDAO.findOne(Long.parseLong(cabinetId)).getEquipmentCabinetName();
                    cabinetId = cabinetId == "" ? "暂无数据":cabinetId;
                    switchboards.setSwitchboardEquipmentCabinet(cabinetId);
                    switchboards.setSwitchboardBrand(brandId);
                }
            }
        }
        return switchboardses;
    }
    //创建交换机
    @RequestMapping(value = "/createAndUpdateSwitchboard",method = RequestMethod.GET)
    public List<predictMmSwitchboards> createSwitchboard(predictMmSwitchboardsForm switchboardsForm) {
        long recordId=switchboardsForm.getId();//获取记录ID
        predictMmSwitchboards predictSwitchboard=null;
        if(recordId==0){
            predictSwitchboard=new predictMmSwitchboards();
        }else {
            predictSwitchboard=SwitchboardManagementRepository.findOne(recordId);
        }
        predictSwitchboard.setSwitchboardSN(switchboardsForm.getSwitchboardSN());
        predictSwitchboard.setSwitchboardPurchasingDate(switchboardsForm.getSwitchboardPurchasingDate());
        predictSwitchboard.setSwitchboardMaintenanceDueTime(switchboardsForm.getSwitchboardMaintenanceDueTime());
        predictSwitchboard.setSwitchboardSerialNumber(switchboardsForm.getSwitchboardSerialNumber());
        predictSwitchboard.setSwitchboardBrand(switchboardsForm.getSwitchboardBrand());
        predictSwitchboard.setSwitchboardType(switchboardsForm.getSwitchboardType());
        predictSwitchboard.setSwitchboardIP(switchboardsForm.getSwitchboardIP());
        predictSwitchboard.setSwitchboardSNMP(switchboardsForm.getSwitchboardSNMP());
        predictSwitchboard.setSwitchboardPort(switchboardsForm.getSwitchboardPort());
        predictSwitchboard.setSwitchboardEquipmentCabinet(switchboardsForm.getSwitchboardEquipmentCabinet());
        predictSwitchboard.setSwitchboardU(switchboardsForm.getSwitchboardU());
        predictSwitchboard.setSwitchboardRemark(switchboardsForm.getSwitchboardRemark());

        String createResult;
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        List<predictMmSwitchboards> list=new ArrayList<predictMmSwitchboards>();
        if(recordId==0) {
            createResult=zbxHostService.createHostSwitchboard(switchboardsForm.getSwitchboardSerialNumber(), switchboardsForm.getSwitchboardIP());//添加服务
            String hostId = createResult.replaceAll("[^0-9]","");
            predictSwitchboard.setHostId(hostId);
            if(createResult.contains("success")){
                SwitchboardManagementRepository.save(predictSwitchboard);
                list.add(predictSwitchboard);
                return list;
            }else {
                // the update
                return list;
            }
        }
        return list;
    }
    //删除交换机
    @RequestMapping(value = "/deleteSwitchboard/{id}")
    public List<predictMmSwitchboards> deleteSwitchboard(@PathVariable long id){
        predictMmSwitchboards predictSwitchboard=SwitchboardManagementRepository.findOne(id);

        String hostId; //关联ZBX的hostid
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        hostId=predictSwitchboard.getHostId();
        zbxHostService.ZbxDeleteServer(hostId);

        SwitchboardManagementRepository.delete(predictSwitchboard);
        List<predictMmSwitchboards> list=new ArrayList<predictMmSwitchboards>();
        list.add(predictSwitchboard);
        return list;
    }
    //数据导出
    @RequestMapping(value = "/export")
    public ResponseEntity<byte[]> exportData(){
        String filePath=homedir+"/download/";
        String fileName="交换机.csv";
        List exportDa = new ArrayList<>();
        List<predictMmSwitchboards> switchboardses = getAllSwitchboards();//获取到需要导出的数据
        List<String>results=new ArrayList<String>();
        String header="交换机编号,交换机S/N号,购买时间,维保到期时间,交换机品牌,交换机型号,交换机IP,SNMP,端口号,所在机柜,所在U,备注";
        for (predictMmSwitchboards switchboards:switchboardses){
            String values="";
            if (switchboards.getSwitchboardSerialNumber()!=null){
                values+=switchboards.getSwitchboardSerialNumber().replace(",","、")+",";
            }else {
                values+=switchboards.getSwitchboardSerialNumber()+",";
            }
            if (switchboards.getSwitchboardSN()!=null){
                values+=switchboards.getSwitchboardSN().replace(",","、")+",";
            }else {
                values+=switchboards.getSwitchboardSN()+",";
            }
            if (switchboards.getSwitchboardPurchasingDate()!=null){
                values+=switchboards.getSwitchboardPurchasingDate().replace(",","、")+",";
            }else {
                values+=switchboards.getSwitchboardPurchasingDate()+",";
            }
            if (switchboards.getSwitchboardMaintenanceDueTime()!=null){
                values+=switchboards.getSwitchboardMaintenanceDueTime().replace(",","、")+",";
            }else {
                values+=switchboards.getSwitchboardMaintenanceDueTime()+",";
            }
            if (switchboards.getSwitchboardBrand()!=null){
                values+=switchboards.getSwitchboardBrand().replace(",","、")+",";
            }else {
                values+=switchboards.getSwitchboardBrand()+",";
            }
            if (switchboards.getSwitchboardType()!=null){
                values+=switchboards.getSwitchboardType().replace(",","、")+",";
            }else {
                values+=switchboards.getSwitchboardType();
            }
            if (switchboards.getSwitchboardIP()!=null){
                values+=switchboards.getSwitchboardIP().replace(",","、")+",";
            }else {
                values+=switchboards.getSwitchboardIP();
            }
            if (switchboards.getSwitchboardSNMP()!=null){
                values+=switchboards.getSwitchboardSNMP().replace(",","、")+",";
            }else {
                values+=switchboards.getSwitchboardSNMP();
            }
            if (switchboards.getSwitchboardPort()!=null){
                values+=switchboards.getSwitchboardPort().replace(",","、")+",";
            }else {
                values+=switchboards.getSwitchboardPort();
            }
            if (switchboards.getSwitchboardEquipmentCabinet()!=null){
                values+=switchboards.getSwitchboardEquipmentCabinet().replace(",","、")+",";
            }else {
                values+=switchboards.getSwitchboardEquipmentCabinet();
            }
            if (switchboards.getSwitchboardU()!=null){
                values+=" "+switchboards.getSwitchboardU().replace(",","、")+",";
            }else {
                values+=" "+switchboards.getSwitchboardU();
            }
            if (switchboards.getSwitchboardRemark()!=null){
                values+=switchboards.getSwitchboardRemark().replace(",","、")+",";
            }else {
                values+=switchboards.getSwitchboardRemark();
            }
            results.add(values);
        }
        csvUtilRepository.exportCsv(new File(filePath+fileName),results,header);
        ResponseEntity<byte[]>  result = downFile("交换机.csv");
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
