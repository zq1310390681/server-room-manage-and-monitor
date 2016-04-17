package cn.edu.shou.monitor.domain.user;
import java.util.*;

import cn.edu.shou.monitor.domain.base.RequestBase;
public class UserDeleteMediaRequest extends RequestBase{
	public UserDeleteMediaRequest() {
		super("user.deletemedia");
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
