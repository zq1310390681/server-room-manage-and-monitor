package cn.edu.shou.monitor.domain.item;
import java.util.*;

import cn.edu.shou.monitor.domain.base.RequestBase;
public class ItemIsreadableRequest extends RequestBase{
	public ItemIsreadableRequest() {
		super("item.isreadable");
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
