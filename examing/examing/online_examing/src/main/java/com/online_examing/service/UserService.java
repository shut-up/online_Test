package com.online_examing.service;

import com.domain.User;
import com.online_examing.repository.UserRepository;
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

    /**
    *@Description: 登录成功返回用户名，失败返回空
    *@Date: 2017/11/28
    */
    public User login(User user){
        User existUser = userRepository.findByIdAndPassword(user.getId(), user.getPassword());
        if(existUser == null){
            return null;
        }
        return existUser;
    }

    /**
    *@Description: 用户注册
    *@Date: 2017/11/28
    */
    public String register(User user){
        User existUser = userRepository.findById(user.getId());
        if(existUser != null){
            return null;
        }
        userRepository.save(user);
        return user.getName();
    }

}
