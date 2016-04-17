package cn.edu.shou.monitor.service.zbxapi;
import java.util.List;

import cn.edu.shou.monitor.domain.base.User;
import cn.edu.shou.monitor.domain.user.UserAddmediaRequest;
import cn.edu.shou.monitor.domain.user.UserAuthenticateRequest;
import cn.edu.shou.monitor.domain.user.UserCreateRequest;
import cn.edu.shou.monitor.domain.user.UserDeleteMediaRequest;
import cn.edu.shou.monitor.domain.user.UserDeleteRequest;
import cn.edu.shou.monitor.domain.user.UserGetRequest;
import cn.edu.shou.monitor.domain.user.UserIsreadableRequest;
import cn.edu.shou.monitor.domain.user.UserIswritableRequest;
import cn.edu.shou.monitor.domain.user.UserLoginRequest;
import cn.edu.shou.monitor.domain.user.UserLogoutRequest;
import cn.edu.shou.monitor.domain.user.UserUpdateProfileRequest;
import cn.edu.shou.monitor.domain.user.UserUpdateRequest;
import cn.edu.shou.monitor.domain.user.UserUpdatemediaRequest;
/**
 * @ClassName: IUserService
 * @Description: 用户接口
 * @author 李庆雷
 * @date 2013-9-23 下午12:02:10
 */
public interface IUserService {
	/**
	 * @Title: get
	 * @Description: 获得用户信息（json）
	 * @param get
	 * @return Object
	 * @throws
	 */
	public Object get(UserGetRequest get);
	
	/**
	 * @Title: login
	 * @Description: 用户登录
	 * @param login
	 * @return Object
	 * @throws
	 */
	public Object login(UserLoginRequest login);
	
	/**
	 * @Title: logout
	 * @Description: 用户退出
	 * @param logout
	 * @return Object
	 * @throws
	 */
	public Object logout(UserLogoutRequest logout);

	/**
	 * @Title: getUserBean
	 * @Description: 获得用户信息（bean）
	 * @param get void
	 * @throws
	 */
	public List<User> getUserBean(UserGetRequest get);
	
	public Object addmedia(UserAddmediaRequest addmedia);
	public Object authenticate(UserAuthenticateRequest authenticate);
	public Object create(UserCreateRequest create);
	public Object deleteMedia(UserDeleteMediaRequest deleteMedia);
	public Object delete(UserDeleteRequest delete);
	public Object isreadable(UserIsreadableRequest isreadable);
	public Object iswritable(UserIswritableRequest iswritable);
	public Object updatemedia(UserUpdatemediaRequest updatemedia);
	public Object updateProfile(UserUpdateProfileRequest updateProfile);
	public Object update(UserUpdateRequest update);
	public boolean addUser(User user);
	public boolean updateUser(User user);
	public boolean deleteUser(User user);
	public List<User> getUser(User user);
}
