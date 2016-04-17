package cn.edu.shou.monitor.service.zbxapi;
import java.util.List;

import cn.edu.shou.monitor.domain.base.HostGroup;
import cn.edu.shou.monitor.domain.hostgroup.HostGroupCreateRequest;
import cn.edu.shou.monitor.domain.hostgroup.HostGroupDeleteRequest;
import cn.edu.shou.monitor.domain.hostgroup.HostGroupExistsRequest;
import cn.edu.shou.monitor.domain.hostgroup.HostGroupGetRequest;
import cn.edu.shou.monitor.domain.hostgroup.HostGroupGetobjectsRequest;
import cn.edu.shou.monitor.domain.hostgroup.HostGroupIsreadableRequest;
import cn.edu.shou.monitor.domain.hostgroup.HostGroupIswritableRequest;
import cn.edu.shou.monitor.domain.hostgroup.HostGroupMassaddRequest;
import cn.edu.shou.monitor.domain.hostgroup.HostGroupMassremoveRequest;
import cn.edu.shou.monitor.domain.hostgroup.HostGroupMassupdateRequest;
import cn.edu.shou.monitor.domain.hostgroup.HostGroupUpdateRequest;
/**
 * @ClassName: IHostgroupService
 * @Description: 设备组接口
 * @author 李庆雷
 * @date 2013-9-23 上午11:56:11
 */
public interface IHostgroupService {
	/**
	 * 
	 * @Title: hostGroupGet
	 * @Description: 获取设备组信息（json）
	 * @param hostGroupGet
	 * @return Object
	 * @throws
	 */
	public Object hostGroupGet(HostGroupGetRequest hostGroupGet);
	
	/**
	 * 
	 * @Title: getHostGroupBean
	 * @Description: 获取设备组信息（bean）
	 * @param hostGroupGet
	 * @return List<HostGroup>
	 * @throws
	 */
	public List<HostGroup> getHostGroupBean(HostGroupGetRequest hostGroupGet);
	
	
	public Object hostGroupCreate(HostGroupCreateRequest hostGroupCreate);
	public Object hostGroupDelete(HostGroupDeleteRequest hostGroupDelete);
	public Object hostGroupExists(HostGroupExistsRequest hostGroupExists);
	public Object hostGroupGetobjects(HostGroupGetobjectsRequest hostGroupGetobjects);
	public Object hostGroupIsreadable(HostGroupIsreadableRequest hostGroupIsreadable);
	public Object hostGroupIswritable(HostGroupIswritableRequest hostGroupIswritable);
	public Object hostGroupMassadd(HostGroupMassaddRequest hostGroupMassadd);
	public Object hostGroupMassremove(HostGroupMassremoveRequest hostGroupMassremove);
	public Object hostGroupMassupdate(HostGroupMassupdateRequest hostGroupMassupdate);
	public Object hostGroupUpdate(HostGroupUpdateRequest hostGroupUpdate);
}
