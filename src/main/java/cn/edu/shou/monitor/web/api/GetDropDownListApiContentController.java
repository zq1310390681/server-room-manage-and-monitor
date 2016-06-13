package cn.edu.shou.monitor.web.api;

import cn.edu.shou.monitor.domain.*;
import cn.edu.shou.monitor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2016/1/15 0015.
 * 专门处理下拉列表相关的控制器
 */
@RestController
@RequestMapping(value ="/predictCenter/api/dropDownList" )
public class GetDropDownListApiContentController {
    @Autowired
    PredictMmBrandRepository brandRepository;
    @Autowired
    PredictMmEquipmentCabinetRepository EquipmentCabinetRepository;
    @Autowired
    PredictMmURepository URepository;
    @Autowired
    StoreManagementRepository StoresRepository;
    @Autowired
    PredictMmServiceObjectRepository ServiceObjectRepository;
    @Autowired
    PredictMmMainframeRepository MainframeRepository;
    @Autowired
    ApplicationManagementRepository ApplicationRepository;

    //张倩
    @Autowired
    EquipTypeRepository equipTypeRepository;
    @Autowired
    OperatingSystemRepository operatingSystemRepository;
    @Autowired
    ServerManagementRepository serverManagementRepository;
    @Autowired
    HostManagementRepository hostManagementRepository;
    @Autowired
    VersionRepository versionRepository;
    @Autowired
    SerObjGroupManagementRepository serObjGroupManagementRepository;
    @Autowired
    AppGroupManagementRepository appGroupManagementRepository;
    @Autowired
    AlarmGradeRepository alarmGradeRepository;
    @Autowired
    KvmRepository kvmRepository;
    //获取所有品牌数据
    @RequestMapping(value = "/getAllBrands")
    public List<predictMmBrand>getAllBrands(){
        return brandRepository.findAll();
    }
    //根据品牌Id获取品牌数据
    @RequestMapping(value = "/getBrandNameById/{id}")
    public predictMmBrand getBrandNameById(@PathVariable long id){
        return brandRepository.findOne(id);
    }
   //根据品牌名称获取品牌对象
    @RequestMapping(value = "/getBrandByBrandName/{name}")
    public predictMmBrand getBrandByBrandName(@PathVariable String name){
        return brandRepository.getBrandByBrandName(name);
    }
    //获取所有U数据
    @RequestMapping(value = "/getAllUs")
    public List<predictMmU>getAllUs(){
        return URepository.findAll();
    }

    //根据Id获取U数据
    @RequestMapping(value = "/getUNumberById/{id}")
    public predictMmU getUNumberById(@PathVariable long id){
        return URepository.findOne(id);
    }
    //获取所有机柜数据
    @RequestMapping(value = "/getAllEquipmentCabinets")
    public List<PredictMmEquipmentCabinet>getAllEquipmentCabinets(){
        return EquipmentCabinetRepository.findAll();
    }
    //根据Id获取机柜数据
    @RequestMapping(value = "/getEquipmentCabinetNameById/{id}")
    public PredictMmEquipmentCabinet getEquipmentCabinetNameById(@PathVariable long id){
        return EquipmentCabinetRepository.findOne(id);
    }
    //根据名称获取机柜数据
    @RequestMapping(value = "/getCabinetByName/{name}")
    public PredictMmEquipmentCabinet getCabinetByName(@PathVariable String name){
        return EquipmentCabinetRepository.getCabByName(name);
    }
    //获取所有服务器的存储设备数据
    @RequestMapping(value = "/getAllStorageDevices")
    public List<predictMmStores>getAllStorageDevices(){
        return StoresRepository.findAll();
    }
    //根据Id获取服务器的存储设备数据
    @RequestMapping(value = "/getStorageDeviceNameById/{id}")
    public predictMmStores getStorageDeviceNameById(@PathVariable long id){
        return StoresRepository.findOne(id);
    }
    //根据设备名称获取设备信息
    @RequestMapping(value = "/getStoreByName/{name}")
    public predictMmStores getStoreByName(@PathVariable String name){
        return StoresRepository.getStoreByName(name);
    }
    //获取所有服务对象数据
    @RequestMapping(value = "/getAllServiceObjects")
    public List<predictMmServiceObject>getAllServiceObjects(){
        return ServiceObjectRepository.findAll();
    }

    //根据Id获取服务对象数据
    @RequestMapping(value = "/getServiceObjectNameById/{id}")
    public predictMmServiceObject getServiceObjectNameById(@PathVariable long id){
        return ServiceObjectRepository.findOne(id);
    }

    //获取所有应用名称数据
    @RequestMapping(value = "/getAllApplicationNames")
    public List<predictMmApplications>getAllApplicationNames(){
        return ApplicationRepository.findAll();
    }
    //根据Id获取应用名称数据
    @RequestMapping(value = "/getApplicationNameById/{id}")
    public predictMmApplications getApplicationNameById(@PathVariable long id){
        return ApplicationRepository.findOne(id);
    }
    //张倩 20160120 合并代码
    /*告警规则*/
    //获取设备类型一级名称
    @RequestMapping("/getEquipTypes/{typeId}")
    public List<predictMmEquipType>getEquipTypes(@PathVariable long typeId){
        return equipTypeRepository.getEquipTypes(typeId);
    }

    //获取设备类型二级名称
    @RequestMapping("/getEquipElements")
    public List<predictMmEquipType>getEquipElements(){
        return equipTypeRepository.getEquipElements();
    }
    @RequestMapping("/getEquipTypeNameById/{id}")
    public predictMmEquipType getEquipTypeNameById(@PathVariable long id){
        return equipTypeRepository.findOne(id);
    }
    /*VMWare虚拟机*/
    //获取操作系统列表
    @RequestMapping("/getOperatingSystem")
    public List<predictMmOperatingSystem>getOperatingSystem(){
        return operatingSystemRepository.findAll();
    }
    //根据操作系统Id获取操作系统名称
    @RequestMapping(value = "/getOperatingSystemById/{id}")
    public predictMmOperatingSystem getOperatingSystemById(@PathVariable long id){
        return operatingSystemRepository.findOne(id);
    }
    //获取服务对象分组列表
    @RequestMapping("/getServiceObjectGroup")
    public List<PredictMmServiceObjGroup>getServiceObjectGroup(){
        return serObjGroupManagementRepository.findAll();
    }
    //根据服务对象分组Id获取服务对象分组名称
    @RequestMapping(value = "/getServiceObjectGroupById/{id}")
    public PredictMmServiceObjGroup getServiceObjectGroupById(@PathVariable long id){
        return serObjGroupManagementRepository.findOne(id);
    }
    //根据名称获取服务对象分组数据
    @RequestMapping(value = "/getSoByName/{name}")
    public PredictMmServiceObjGroup getSoByName(@PathVariable String name){
        return serObjGroupManagementRepository.getSerObjByName(name);
    }
    //获取应用分组列表
    @RequestMapping("/getAppGroup")
    public List<PredictMmAppGroup>getAppGroup(){
        return appGroupManagementRepository.findAll();
    }
    //根据应用分组Id获取应用分组名称
    @RequestMapping(value = "/getAppGroupById/{id}")
    public PredictMmAppGroup getAppGroupById(@PathVariable long id){
        return appGroupManagementRepository.findOne(id);
    }

    //获取等级列表
    @RequestMapping("/getAlarmGrade")
    public List<PredictMmAlarmGrade> getAlarmGrade(){
        return alarmGradeRepository.findAll();
    }
    //根据等级Id获取等级名称
    @RequestMapping(value="/getAlarmGradeById/{id}")
    public PredictMmAlarmGrade getAlarmGradeById(@PathVariable long id){
        return alarmGradeRepository.findOne(id);
    }

    //获取服务器列表
    @RequestMapping("/getServerSerialNumber")
    public List<predictMmServers>getServerSerialNumber(){
        return serverManagementRepository.findAll();
    }
    //根据服务器Id获取服务器编号
    @RequestMapping("/getServerSerialNumberById/{id}")
    public predictMmServers getServerSerialNumberById(@PathVariable long id){
        return serverManagementRepository.findOne(id);
    }
    //获取主机列表
    @RequestMapping("/getAllHosts")
    public List<predictMmHost>getAllHosts(){
        return hostManagementRepository.findAll();
    }
    @RequestMapping("/getHostById/{id}")
    public predictMmHost getHostById(@PathVariable long id){
        return hostManagementRepository.findOne(id);
    }
   //获取版本信息
    @RequestMapping("/getAllVersion")
    public List<predictMmVersion>getAllVersion(){
        return versionRepository.findAll();
    }
    @RequestMapping("/getVersionById/{id}")
    public predictMmVersion getVersionById(@PathVariable long id){
        return versionRepository.findOne(id);
    }
    //获取KVM编号
    @RequestMapping("/getAllKvm")
    public List<predictMmKvm>getAllKvm(){
        return kvmRepository.findAll();
    }
    //根据KVM ID获取数据信息
    @RequestMapping("/getKvmById/{id}")
    public predictMmKvm getKvmById(@PathVariable long id){
        return kvmRepository.findOne(id);
    }
    //根据KVM名称获取数据信息
    @RequestMapping("/getKvmByName/{name}")
    public predictMmKvm getKvmByName (@PathVariable String name){
        return kvmRepository.getKvmByName(name);
    }
}
