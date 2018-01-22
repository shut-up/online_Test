package com.online_examing.service;

import com.domain.ManagerClass;
import com.domain.User;
import com.online_examing.domain.PaperRequestDto;
import com.online_examing.repository.UserRepository;
import com.utils.DefaultKeyGenerator;
import com.utils.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

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
    public User updateInfo(PaperRequestDto paperRequestDto){
        User existUser = userRepository.findByAccountNumber(paperRequestDto.getAccountNumber());
        if(paperRequestDto.getPassword()!=null&&paperRequestDto.getPassword()!="") {
            existUser.setPassword(paperRequestDto.getPassword());
        }
        if(paperRequestDto.getGrade()!=null&&paperRequestDto.getSchool()!=null) {
           List<ManagerClass> managerClassList = existUser.getManagerClasses();
           //如果之前没有mangerClass的话直接添加
           if(managerClassList.size()==0) {
               managerClassList = new ArrayList<>();
               ManagerClass updateClasses = new ManagerClass();
               List<String> school_list = new ArrayList<>();
               for (String s : paperRequestDto.getSchool()) {
                   school_list.add(s);
               }
               updateClasses.setGrade(paperRequestDto.getGrade());
               updateClasses.setSchool(school_list);
               managerClassList.add(updateClasses);
               existUser.setManagerClasses(managerClassList);
           }
           //如果有的话
           else{
               List<String> school = new ArrayList<>();
               for(Iterator<ManagerClass> iter = managerClassList.iterator(); iter.hasNext();) {
                   ManagerClass m = iter.next();
                   if( m.getGrade().equals(paperRequestDto.getGrade())){
                        school.addAll(m.getSchool());
                        iter.remove();
                   }
               }
               ManagerClass updateClasses = new ManagerClass();
               List<String> school_list = new ArrayList<>();
               for (String s : paperRequestDto.getSchool()) {
                   school_list.add(s);
               }
               school_list.addAll(school);
               List newList = new ArrayList(new HashSet(school_list));
               updateClasses.setGrade(paperRequestDto.getGrade());
               updateClasses.setSchool(newList);
               managerClassList.add(updateClasses);
               existUser.setManagerClasses(managerClassList);
           }
         }
        User user =  userRepository.save(existUser);
        return user;
    }

    public User getInfo(User user){
        return userRepository.findByAccountNumber(user.getAccountNumber());
    }
}
