package cn.edu.shou.monitor.service;

import cn.edu.shou.monitor.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sqhe on 14-7-31.
 */
@Repository
public class UserDAO implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user)
    {

        return this.userRepository.save(user);
    }

    public User findById(Long userId)
    {
        return this.userRepository.findOne(userId);
    }

    public User findByUserName(String userName)
    {
        return userRepository.findByUserName(userName);
    }


    public List<User> findUserListByGroupList(List<Long> groupIdList)
    {
        return userRepository.getUserListByGroupList(groupIdList);

    }

    public List<User> findUserListByIdList(List<Long> userIdList)
    {
        return this.userRepository.getUserListByIdList(userIdList);
    }

    public List<User> findAll(){
        return this.userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        return user;
    }


}
