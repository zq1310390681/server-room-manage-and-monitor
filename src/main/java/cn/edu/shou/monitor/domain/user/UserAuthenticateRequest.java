package cn.edu.shou.monitor.domain.user;
import cn.edu.shou.monitor.domain.base.RequestBase;
import cn.edu.shou.monitor.domain.base.User;
public class UserAuthenticateRequest extends RequestBase{
	public UserAuthenticateRequest() {
		super("user.authenticate");
	}
	private UserAuthenticateParams params = new UserAuthenticateParams();
	public void setParams(UserAuthenticateParams params) {
		this.params = params;
	}
	public UserAuthenticateParams getParams() {
		return params;
	}
	public static class UserAuthenticateParams extends User{
		private String password;
		private String ser;
		private Boolean userData;
		public void setPassword(String password) {
			this.password = password;
		}
		public String getPassword() {
			return password;
		}
		public void setSer(String ser) {
			this.ser = ser;
		}
		public String getSer() {
			return ser;
		}
		public void setUserData(Boolean userData) {
			this.userData = userData;
		}
		public Boolean getUserData() {
			return userData;
		}
	}
}
