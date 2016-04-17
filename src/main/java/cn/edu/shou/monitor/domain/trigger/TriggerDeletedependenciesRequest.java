package cn.edu.shou.monitor.domain.trigger;
import java.util.*;

import cn.edu.shou.monitor.domain.base.RequestBase;
import cn.edu.shou.monitor.domain.base.Trigger;
public class TriggerDeletedependenciesRequest extends RequestBase{
	public TriggerDeletedependenciesRequest() {
		super("trigger.deletedependencies");
	}
	private List<Trigger> params;
	public void setParams(List<Trigger> params) {
		this.params = params;
	}
	public List<Trigger> getParams() {
		 if(params==null){
			params   = new ArrayList<Trigger>();
			return params;
		}
		 return params;
	}
}
