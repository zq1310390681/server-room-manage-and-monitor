package cn.edu.shou.monitor.domain.template;
import java.util.*;

import cn.edu.shou.monitor.domain.base.RequestBase;
public class TemplateDeleteRequest extends RequestBase{
	public TemplateDeleteRequest() {
		super("template.delete");
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
