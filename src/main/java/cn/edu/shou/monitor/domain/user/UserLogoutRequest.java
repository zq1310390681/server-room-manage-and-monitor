package cn.edu.shou.monitor.domain.user;
import java.util.*;

import cn.edu.shou.monitor.domain.base.RequestBase;
import cn.edu.shou.monitor.domain.base.User;
public class UserLogoutRequest extends RequestBase{
	public UserLogoutRequest() {
		super("user.logout");
	}
	private List<User> params;
	public void setParams(List<User> params) {
		this.params = params;
	}
	public List<User> getParams() {
		 if(params==null){
			params   = new ArrayList<User>();
			return params;
		}
		 return params;
	}
}
