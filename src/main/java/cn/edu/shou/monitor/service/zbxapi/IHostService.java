package cn.edu.shou.monitor.service.zbxapi;

import cn.edu.shou.monitor.domain.base.Host;
import cn.edu.shou.monitor.domain.base.HostGroup;
import cn.edu.shou.monitor.domain.base.HostInterface;
import cn.edu.shou.monitor.domain.base.Template;
import cn.edu.shou.monitor.domain.host.HostCreateRequest;
import cn.edu.shou.monitor.domain.host.HostDeleteRequest;
import cn.edu.shou.monitor.domain.host.HostExistsRequest;
import cn.edu.shou.monitor.domain.host.HostGetRequest;
import cn.edu.shou.monitor.domain.host.HostGetobjectsRequest;
import cn.edu.shou.monitor.domain.host.HostIsreadableRequest;
import cn.edu.shou.monitor.domain.host.HostIswritableRequest;
import cn.edu.shou.monitor.domain.host.HostMassaddRequest;
import cn.edu.shou.monitor.domain.host.HostMassremoveRequest;
import cn.edu.shou.monitor.domain.host.HostMassupdateRequest;
import cn.edu.shou.monitor.domain.host.HostUpdateRequest;
import org.json.JSONObject;

/**
 * @ClassName: IHostService
 * @Description: 设备接口
 * @author 李庆雷
 * @date 2013-9-23 上午11:59:42
 */
public interface IHostService {
	public Object create(HostCreateRequest create);
	/**
	 * @Title: get
	 * @Description: 获取设备信息（json）
	 * @param get
	 * @return Object
	 * @throws
	 */
	public JSONObject get(HostGetRequest get);


//	public List<Host> getHostToBean(HostGetRequest get);
	public String createHost(Host host, HostInterface hostInterface,
			HostGroup hostgroup, Template template);

	
	public String delete(HostDeleteRequest delete);
	public Object exists(HostExistsRequest exists);
	public Object getobjects(HostGetobjectsRequest getobjects);
	public Object isreadable(HostIsreadableRequest isreadable);
	public Object iswritable(HostIswritableRequest iswritable);
	public Object massadd(HostMassaddRequest massadd);
	public Object massremove(HostMassremoveRequest massremove);
	public Object massupdate(HostMassupdateRequest massupdate);
	public Object update(HostUpdateRequest update);

}
