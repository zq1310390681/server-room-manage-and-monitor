package cn.edu.shou.monitor.domain.host;

import java.util.*;

import cn.edu.shou.monitor.domain.base.RequestBase;

public class HostDeleteRequest extends RequestBase{
	public HostDeleteRequest() {
		super("host.delete");
	}
	private List<String> params;
	public void setParams(List<String> params) {
		this.params = params;
	}
	public List<String> getParams() {
		if(params==null){
			params   = new ArrayList<String>();
			return params;
		}
		return params;
	}
}