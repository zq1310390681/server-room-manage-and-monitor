package cn.edu.shou.monitor.service.zbxapi;
import cn.edu.shou.monitor.domain.usergroup.UserGroupCreateRequest;
import cn.edu.shou.monitor.domain.usergroup.UserGroupDeleteRequest;
import cn.edu.shou.monitor.domain.usergroup.UserGroupExistsRequest;
import cn.edu.shou.monitor.domain.usergroup.UserGroupGetRequest;
import cn.edu.shou.monitor.domain.usergroup.UserGroupGetobjectsRequest;
import cn.edu.shou.monitor.domain.usergroup.UserGroupIsreadableRequest;
import cn.edu.shou.monitor.domain.usergroup.UserGroupIswritableRequest;
import cn.edu.shou.monitor.domain.usergroup.UserGroupMassaddRequest;
import cn.edu.shou.monitor.domain.usergroup.UserGroupMassupdateRequest;
import cn.edu.shou.monitor.domain.usergroup.UserGroupUpdateRequest;
public interface IUsergroupService {
	public Object userGroupCreate(UserGroupCreateRequest userGroupCreate);
	public Object userGroupDelete(UserGroupDeleteRequest userGroupDelete);
	public Object userGroupExists(UserGroupExistsRequest userGroupExists);
	public Object userGroupGetobjects(UserGroupGetobjectsRequest userGroupGetobjects);
	public Object userGroupGet(UserGroupGetRequest userGroupGet);
	public Object userGroupIsreadable(UserGroupIsreadableRequest userGroupIsreadable);
	public Object userGroupIswritable(UserGroupIswritableRequest userGroupIswritable);
	public Object userGroupMassadd(UserGroupMassaddRequest userGroupMassadd);
	public Object userGroupMassupdate(UserGroupMassupdateRequest userGroupMassupdate);
	public Object userGroupUpdate(UserGroupUpdateRequest userGroupUpdate);
}
