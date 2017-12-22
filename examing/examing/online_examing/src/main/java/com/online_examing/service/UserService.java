package com.online_examing.service;

import com.domain.User;
import com.online_examing.repository.UserRepository;
import com.utils.DefaultKeyGenerator;
import com.utils.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: walton
 * @Description: 登录、注册的Service
 * @Createtime: 2017/11/28
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DefaultKeyGenerator defaultKeyGenerator ;

    /**
    *@Description: 登录成功返回用户名，失败返回空
    *@Date: 2017/11/28
    */
    public User login(User user){
        User existUser = userRepository.findByAccountNumberAndPassword(user.getAccountNumber(), user.getPassword());
        if(existUser == null){
            return null;
        }
        return existUser;
    }

    /**
    *@Description: 用户注册
    *@Date: 2017/11/28
    */
    public Long register(User user){
        User existUser = userRepository.findByAccountNumber(user.getAccountNumber());
        if(existUser != null){
            return null;
        }
        user.setId((Long) defaultKeyGenerator.generateKey());
        userRepository.save(user);
        return user.getId();
    }

    /**
    *@Description: 更新用户信息
    *@Date: 2017/12/1
    */
    public User updateInfo(User user){
        User existUser = userRepository.findByAccountNumber(user.getAccountNumber());
        if(user.getPassword()!=null) {
            existUser.setPassword(user.getPassword());
        }
        if(user.getGrade()!=null) {
            existUser.setGrade(user.getGrade());
        }
        if(user.getSchool()!=null) {
            existUser.setSchool(user.getSchool());
        }
        user =  userRepository.save(existUser);
        return user;
    }
}
