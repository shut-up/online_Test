package com.online_examing.controller;

import com.domain.User;
import com.online_examing.Routes;
import com.online_examing.domain.PaperRequestDto;
import com.online_examing.service.UserService;
import com.utils.RestDoing;
import com.utils.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.USER_BASE_ROUTE)
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     */
    @PostMapping(Routes.USER_LOGIN)
    public RestResult login(@RequestBody User user){
        RestDoing restDoing = restResult ->{
            restResult.data = userService.login(user);
        };
        return restDoing.go(null, logger);
    }

    /**
     * 用户注册
     */
    @PostMapping(Routes.USER_REGISTER)
    public RestResult register(@RequestBody User user){
        RestDoing restDoing = restResult ->{
            restResult.data = userService.register(user);
        };
        return restDoing.go(null, logger);
    }

    /**
     * 用户更新信息
     */
    @PostMapping(Routes.USER_UPDATEINFO)
    public RestResult updateInfo(@RequestBody PaperRequestDto paperRequestDto){
        RestDoing restDoing = restResult ->{
            restResult.data = userService.updateInfo(paperRequestDto);
        };
        return restDoing.go(null, logger);
    }

    /**
     * 获取用户信息
     */
    @GetMapping(Routes.USER_GETINFO)
    public RestResult getInfo(User user){
        RestDoing restDoing = restResult ->{
            restResult.data = userService.getInfo(user);
        };
        return restDoing.go(null, logger);
    }

    /**
     * 获取用户的试卷
     */
    @GetMapping(Routes.USER_GET_PAPER)
    public RestResult getPaper(@RequestParam(value = "examClass") String examClass,@RequestParam(value = "stuId") String stuId){
        RestDoing restDoing = restResult ->{
            restResult.data = userService.getPaper(examClass,stuId);
        };
        return restDoing.go(null, logger);
    }

}
