package cn.edu.shou.monitor.domain.trigger;
import java.util.*;

import cn.edu.shou.monitor.domain.base.RequestBase;
public class TriggerIswritableRequest extends RequestBase{
	public TriggerIswritableRequest() {
		super("trigger.iswritable");
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
