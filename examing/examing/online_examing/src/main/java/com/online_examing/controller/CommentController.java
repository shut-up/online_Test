package com.online_examing.controller;

import com.online_examing.Routes;
import com.online_examing.domain.CommentRequestDto;
import com.online_examing.service.CommentService;
import com.utils.RestCode;
import com.utils.RestDoing;
import com.utils.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Routes.COMMENT_BASE_ROUTE)
public class CommentController {

    private final static Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    public CommentService commentService;

    /**
     * 添加评论
     */
    @PostMapping(Routes.COMMENT_ADD)
    public RestResult addComment(@RequestBody CommentRequestDto requestDto){
        RestDoing restDoing = restResult ->{
            commentService.addOneComment(requestDto);
            restResult.data = RestCode.CD1;
        };
        return restDoing.go(null, logger);
    }

    /**
     * 检索
     * @param requestDto
     * @return
     */
    @PostMapping(Routes.COMMENT_SEARCH)
    public RestResult search(@RequestBody CommentRequestDto requestDto){
        RestDoing restDoing = restResult ->{
            restResult.data = commentService.searchList(requestDto);
        };
        return restDoing.go(null, logger);
    }


    /**
     * 查询评论全部内容
     **/
    @GetMapping(Routes.COMMENT_GET_ALL)
    public RestResult getAllComment(CommentRequestDto commentRequestDto){
        RestDoing restDoing = restResult -> {
            restResult.data= commentService.getAllComment(commentRequestDto);
        };
        return  restDoing.go(null,logger);
    }

    /**
     * 查询评论回复内容
     **/
    @GetMapping(Routes.COMMENT_GET_BY_ID)
    public RestResult getByComment(Long id) {
        RestDoing restDoing = restResult -> {
            restResult.data = commentService.getByComment(id);
        };
        return restDoing.go(null, logger);
    }

    /**
     * 更新选择评论的状态
     **/
    @GetMapping(Routes.COMMENT_UPDATE_SELECT_COMMENT)
    public RestResult updateSelectComment(CommentRequestDto requestDto){
        RestDoing restDoing = restResult -> {
            commentService.updateAllStatus(requestDto);
        };
        return  restDoing.go(null,logger);
    }

}
