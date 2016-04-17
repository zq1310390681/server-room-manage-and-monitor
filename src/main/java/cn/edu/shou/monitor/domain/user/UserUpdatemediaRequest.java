package cn.edu.shou.monitor.domain.user;
import java.util.*;

import cn.edu.shou.monitor.domain.base.Media;
import cn.edu.shou.monitor.domain.base.RequestBase;
import cn.edu.shou.monitor.domain.base.User;
public class UserUpdatemediaRequest extends RequestBase{
	public UserUpdatemediaRequest() {
		super("user.updatemedia");
	}
	private UserUpdatemediaParams params = new UserUpdatemediaParams();
	public void setParams(UserUpdatemediaParams params) {
		this.params = params;
	}
	public UserUpdatemediaParams getParams() {
		return params;
	}
	public static class UserUpdatemediaParams{
		private List<Media> medias;
		private List<User> users;
		public void setMedias(List<Media> medias) {
			this.medias = medias;
		}
		public List<Media> getMedias() {
			 if(medias==null){
				medias   = new ArrayList<Media>();
				return medias;
			}
			 return medias;
		}
		public void setUsers(List<User> users) {
			this.users = users;
		}
		public List<User> getUsers() {
			 if(users==null){
				users   = new ArrayList<User>();
				return users;
			}
			 return users;
		}
	}
}
