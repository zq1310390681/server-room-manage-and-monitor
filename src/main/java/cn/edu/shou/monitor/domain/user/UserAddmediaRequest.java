package cn.edu.shou.monitor.domain.user;
import java.util.*;

import cn.edu.shou.monitor.domain.base.Media;
import cn.edu.shou.monitor.domain.base.RequestBase;
import cn.edu.shou.monitor.domain.base.User;
public class UserAddmediaRequest extends RequestBase{
	public UserAddmediaRequest() {
		super("user.addmedia");
	}
	private UserAddmediaParams params = new UserAddmediaParams();
	public void setParams(UserAddmediaParams params) {
		this.params = params;
	}
	public UserAddmediaParams getParams() {
		return params;
	}
	public static class UserAddmediaParams extends User{
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
