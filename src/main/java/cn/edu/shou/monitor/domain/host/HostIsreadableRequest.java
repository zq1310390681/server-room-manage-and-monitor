package cn.edu.shou.monitor.domain.host;
import java.util.*;

import cn.edu.shou.monitor.domain.base.RequestBase;
public class HostIsreadableRequest extends RequestBase{
	public HostIsreadableRequest() {
		super("host.isreadable");
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
