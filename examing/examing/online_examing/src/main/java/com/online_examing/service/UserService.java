package com.online_examing.service;

import com.domain.ExamPaper;
import com.domain.ManagerClass;
import com.domain.User;
import com.online_examing.domain.PaperRequestDto;
import com.online_examing.repository.ExamRepository;
import com.online_examing.repository.UserRepository;
import com.utils.DefaultKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    MongoTemplate mongoTemplate;

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
               List<String> major_list = new ArrayList<>();
               for (String s : paperRequestDto.getMajor()) {
                   major_list.add(s);
               }
               updateClasses.setGrade(paperRequestDto.getGrade());
               updateClasses.setSchool(paperRequestDto.getSchool());
               updateClasses.setMajor(major_list);
               managerClassList.add(updateClasses);
               existUser.setManagerClasses(managerClassList);
           }
           //如果有的话
           else{
               List<String> major = new ArrayList<>();
               for(Iterator<ManagerClass> iter = managerClassList.iterator(); iter.hasNext();) {
                   ManagerClass m = iter.next();
                   if( m.getGrade().equals(paperRequestDto.getGrade())&&m.getSchool().equals(paperRequestDto.getSchool())){
                        major.addAll(m.getMajor());
                        iter.remove();
                   }
               }
               ManagerClass updateClasses = new ManagerClass();
               List<String> major_list = new ArrayList<>();
               for (String s : paperRequestDto.getMajor()) {
                   major_list.add(s);
               }
               major_list.addAll(major);
               List newList = new ArrayList(new HashSet(major_list));
               updateClasses.setGrade(paperRequestDto.getGrade());
               updateClasses.setSchool(paperRequestDto.getSchool());
               updateClasses.setMajor(newList);
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

    public List<ExamPaper> getPaper(String  examClass){
//        String examClass = user.getManagerClasses().get(0).getGrade()+user.getManagerClasses().get(0).getSchool()+user.getManagerClasses().get(0).getMajor().get(0);
        return examRepository.findByExamClassContains(examClass);
    }
}
