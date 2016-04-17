package cn.edu.shou.monitor.domain.hostgroup;
import java.util.*;

import cn.edu.shou.monitor.domain.base.RequestBase;
public class HostGroupExistsRequest extends RequestBase{
	public HostGroupExistsRequest() {
		super("hostgroup.exists");
	}
	private HostGroupExistsParams params = new HostGroupExistsParams();
	public void setParams(HostGroupExistsParams params) {
		this.params = params;
	}
	public HostGroupExistsParams getParams() {
		return params;
	}
	public static class HostGroupExistsParams{
		private List<String> groupid;
		private List<String> name;
		private String node;
		private List<String> nodeids;
		public void setGroupid(List<String> groupid) {
			this.groupid = groupid;
		}
		public List<String> getGroupid() {
			 if(groupid==null){
				groupid   = new ArrayList<String>();
				return groupid;
			}
			 return groupid;
		}
		public void setName(List<String> name) {
			this.name = name;
		}
		public List<String> getName() {
			 if(name==null){
				name   = new ArrayList<String>();
				return name;
			}
			 return name;
		}
		public void setNode(String node) {
			this.node = node;
		}
		public String getNode() {
			return node;
		}
		public void setNodeids(List<String> nodeids) {
			this.nodeids = nodeids;
		}
		public List<String> getNodeids() {
			 if(nodeids==null){
				nodeids   = new ArrayList<String>();
				return nodeids;
			}
			 return nodeids;
		}
	}
}
