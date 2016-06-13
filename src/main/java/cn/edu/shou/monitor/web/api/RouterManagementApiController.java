package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.missiveDataForm.predictMmRoutersForm;
import cn.edu.shou.monitor.domain.predictMmRouters;
import cn.edu.shou.monitor.service.CsvUtilRepository;
import cn.edu.shou.monitor.service.PredictMmBrandRepository;
import cn.edu.shou.monitor.service.PredictMmEquipmentCabinetRepository;
import cn.edu.shou.monitor.service.RouterManagementRepository;
import cn.edu.shou.monitor.service.impl.ZbxHostServiceImpl;
import cn.edu.shou.monitor.web.FileOperate;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value ="/predictCenter/api/router" )
public class RouterManagementApiController {
    @Value("${spring.main.homedir}")
    String homedir;
    @Autowired
    RouterManagementRepository routerManagementRepository;
    @Autowired
    CsvUtilRepository csvUtilRepository;
    @Autowired
    PredictMmBrandRepository brandRepository;
    @Autowired
    PredictMmEquipmentCabinetRepository pmecDAO;
    private final static Logger logger = LoggerFactory.getLogger(RouterManagementApiController.class);
    //获取所有路由器数据信息
    @RequestMapping(value = "/getAllRouters")
    public List<predictMmRouters> getAllRouters(){
        return routerManagementRepository.findAll();
    }
    //导出所有路由器数据信息
    @RequestMapping(value = "/getAllRoutersExport")
    public List<predictMmRouters> getAllRoutersExport(){
        List<predictMmRouters> routerses = routerManagementRepository.findAll();
        if (routerses != null){
            for (predictMmRouters routers : routerses){
                //处理品牌
                String brandId = routers.getRouterBrand();
                if (brandId != null){
                    brandId = brandRepository.findOne(Long.parseLong(brandId))==null?"":
                            brandRepository.findOne(Long.parseLong(brandId)).getBrandName();
                    brandId = brandId == ""?"暂无数据":brandId;
                    routers.setRouterBrand(brandId);
                }
                //处理机柜
                String cabinetId = routers.getRouterEquipmentCabinet();
                if (cabinetId != null){
                    cabinetId =pmecDAO.findOne(Long.parseLong(cabinetId))==null?"":
                            pmecDAO.findOne(Long.parseLong(cabinetId)).getEquipmentCabinetName();
                    cabinetId = cabinetId == ""?"暂无数据":cabinetId;
                }
            }
        }
        return routerses;
    }
    //创建路由器
    @RequestMapping(value = "/createAndUpdateRouter",method = RequestMethod.GET)
    public List<predictMmRouters> createRouter(predictMmRoutersForm routersForm) {

        long recordId=routersForm.getId();//获取记录ID
        predictMmRouters predictRouter=null;
        if(recordId==0){
            predictRouter=new predictMmRouters();
        }else {
            predictRouter = routerManagementRepository.findOne(recordId);
        }
        predictRouter.setRouterSN(routersForm.getRouterSN());
        predictRouter.setRouterPurchasingDate(routersForm.getRouterPurchasingDate());
        predictRouter.setRouterMaintenanceDueTime(routersForm.getRouterMaintenanceDueTime());
        predictRouter.setRouterSerialNumber(routersForm.getRouterSerialNumber());
        predictRouter.setRouterBrand(routersForm.getRouterBrand());
        predictRouter.setRouterType(routersForm.getRouterType());
        predictRouter.setRouterIP(routersForm.getRouterIP());
        predictRouter.setRouterSNMP(routersForm.getRouterSNMP());
        predictRouter.setRouterPort(routersForm.getRouterPort());
        predictRouter.setRouterEquipmentCabinet(routersForm.getRouterEquipmentCabinet());
        predictRouter.setRouterU(routersForm.getRouterU());
        predictRouter.setRouterRemark(routersForm.getRouterRemark());

        String createResult=null;
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        List<predictMmRouters> list=new ArrayList<predictMmRouters>();
        if(recordId==0) {
            createResult=zbxHostService.createHostRouter(routersForm.getRouterSerialNumber(), routersForm.getRouterIP());//添加服务
            String hostId = createResult.replaceAll("[^0-9]","");
            predictRouter.setHostId(hostId);
            if(createResult.contains("success")){
                routerManagementRepository.save(predictRouter);
                list.add(predictRouter);
                return list;
            }else {
                routerManagementRepository.save(predictRouter);
                list.add(predictRouter);
                return list;
            }
        }else {
            routerManagementRepository.save(predictRouter);
            list.add(predictRouter);
            return list;
        }
//        routerManagementRepository.save(predictRouter);
//        List<predictMmRouters> list=new ArrayList<predictMmRouters>();
//        list.add(predictRouter);
//        return list;
    }
    //删除路由器
    @RequestMapping(value = "/deleteRouter/{id}")
    public List<predictMmRouters> deleteRouter(@PathVariable long id){
        predictMmRouters predictRouter=routerManagementRepository.findOne(id);

        String hostId; //关联ZBX的hostid
        ZbxHostServiceImpl zbxHostService= new ZbxHostServiceImpl();
        hostId=predictRouter.getHostId();
        zbxHostService.ZbxDeleteServer(hostId);

        routerManagementRepository.delete(predictRouter);
        List<predictMmRouters> list=new ArrayList<predictMmRouters>();
        list.add(predictRouter);
        return list;
    }
    //数据导出
    @RequestMapping(value = "/export")
    public ResponseEntity<byte[]> exportData(){
        String filePath=homedir+"/download/";
        String fileName="路由器.csv";
        List exportDa = new ArrayList<>();
        List<predictMmRouters> routerses = getAllRoutersExport();//获取到需要导出的数据
        List<String>results=new ArrayList<String>();
        String header="路由器编号,路由器S/N号,购买时间,维保到期时间,路由器品牌,路由器型号,路由器IP,SNMP,端口号,所在机柜,所在U,备注";
        for (predictMmRouters routers:routerses){
            String values="";
            if (routers.getRouterSerialNumber()!=null){
                values+=routers.getRouterSerialNumber().replace(",","、")+",";
            }else {
                values+=routers.getRouterSerialNumber()+",";
            }
            if (routers.getRouterSN()!=null){
                values+=routers.getRouterSN().replace(",","、")+",";
            }else {
                values+=routers.getRouterSN()+",";
            }
            if (routers.getRouterPurchasingDate()!=null){
                values+=routers.getRouterPurchasingDate().replace(",","、")+",";
            }else {
                values+=routers.getRouterPurchasingDate()+",";
            }
            if (routers.getRouterMaintenanceDueTime()!=null){
                values+=routers.getRouterMaintenanceDueTime().replace(",","、")+",";
            }else {
                values+=routers.getRouterMaintenanceDueTime()+",";
            }
            if (routers.getRouterBrand()!=null){
                values+=routers.getRouterBrand().replace(",","、")+",";
            }else {
                values+=routers.getRouterBrand()+",";
            }
            if (routers.getRouterType()!=null){
                values+=routers.getRouterType().replace(",","、")+",";
            }else {
                values+=routers.getRouterType();
            }
            if (routers.getRouterIP()!=null){
                values+=routers.getRouterIP().replace(",","、")+",";
            }else {
                values+=routers.getRouterIP();
            }
            if (routers.getRouterSNMP()!=null){
                values+=routers.getRouterSNMP().replace(",","、")+",";
            }else {
                values+=routers.getRouterSNMP();
            }
            if (routers.getRouterPort()!=null){
                values+=routers.getRouterPort().replace(",","、")+",";
            }else {
                values+=routers.getRouterPort();
            }
            if (routers.getRouterEquipmentCabinet()!=null){
                values+=routers.getRouterEquipmentCabinet().replace(",","、")+",";
            }else {
                values+=routers.getRouterEquipmentCabinet();
            }
            if (routers.getRouterU()!=null){
                values+=" "+routers.getRouterU().replace(",","、")+",";
            }else {
                values+=" "+routers.getRouterU();
            }
            if (routers.getRouterRemark()!=null){
                values+=routers.getRouterRemark().replace(",","、")+",";
            }else {
                values+=routers.getRouterRemark();
            }
            results.add(values);
        }
        csvUtilRepository.exportCsv(new File(filePath+fileName),results,header);
        ResponseEntity<byte[]>  result = downFile("路由器.csv");
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
