package com.online_examing.service;

import com.domain.Comment;
import com.domain.MyEntity;
import com.online_examing.domain.CommentRequestDto;
import com.online_examing.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommentService extends  BaseService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoOperationService mongoOperationService;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    MongoRepository getRepository() {
        return this.commentRepository;
    }

    @Override
    public MongoOperationService getMongoOperationService() {
        return mongoOperationService;
    }

    @PostConstruct
    public  void setDataSource(){
        mongoOperationService.setDataSource(Comment.class);
    }


    /**
     * 添加评论
     * @param requestDto
     * @return
     */
    public MyEntity addOneComment(CommentRequestDto requestDto){
        MyEntity data =null;
        //插入时间
        long now = System.currentTimeMillis();
        requestDto.getComment().setCreateTime(now);
        requestDto.getComment().setUpdateTime(now);
        //当父的id为0的时候则添加评论
        if(requestDto.getComment()!=null && requestDto.getComment().getParentId()==0){
            //添加第一条评论
            Comment comment =requestDto.getComment();
            Comment getComment = (Comment) addOne(comment);
            data =commentRepository.findOne(getComment.getId());
        }else if(requestDto.getComment().getParentId()!=0){//第二次时候添加
            Long id =  requestDto.getComment().getParentId();
            Comment comment = commentRepository.findOne(id);
            if (comment == null) {
                return null;
            }
            List<Comment> commentList = comment.getCommentList();
            if(commentList==null){//添加第一个
                commentList  = new ArrayList<Comment>();
                commentList.add(comment);
            }else{
                Comment tempComment = new Comment();
                tempComment.setId(comment.getId());
                commentList.add(tempComment);
            }
            Comment newComment = new Comment();
            newComment.setParentId(requestDto.getComment().getParentId());
            if(comment.getRootId()==0){
                newComment.setRootId(comment.getId());
            }else{
                newComment.setRootId(comment.getRootId());
            }
            newComment.setType(requestDto.getComment().getType());
            newComment.setOuterId(requestDto.getComment().getOuterId());
            newComment.setContent(requestDto.getComment().getContent());
            newComment.setCreateTime(requestDto.getComment().getCreateTime());
            newComment.setUpdateTime(requestDto.getComment().getUpdateTime());
            newComment.setCommentList(commentList);
            return addOne(newComment);
        }
        return data;
    }

    /**
     * 根据评论id查询评论列表
     * @param id
     * @return
     */

    public Comment  getByComment(Long id){
        if(id ==null){
            return null;
        }
       Comment comment = commentRepository.findOne(id);
        if(comment ==null){
            return  null;
        }
        return  comment;
    }


    /**
     * 检索
     * @param requestDto
     * @return
     */
    public Map searchList(CommentRequestDto requestDto){
        Query query = new Query();
        if(requestDto.getComplaintStatus().size()!=0){//0被投诉 1已处理
            //query.addCriteria(Criteria.where("complaintStatus").in(requestDto.getStatus()));
        }
        if(requestDto.getStatus().size()!=0) {//评论状态 0显示 1隐藏  2删除
            query.addCriteria(Criteria.where("status").in(requestDto.getStatus()));
        }
       if(requestDto.getCheck().size()!=0){//0未审核 1已审核
           query.addCriteria(Criteria.where("check").in(requestDto.getCheck()));
       }
        if(requestDto.getPageSize()==0){
            requestDto.setPageSize(10);
        }
        if(requestDto.getPage()==0){
            requestDto.setPage(1);
        }
       Integer skip = (requestDto.getPage()-1) * requestDto.getPageSize();
        query.with(new Sort(new Sort.Order(Sort.Direction.ASC,"check")));
        query.skip(skip).limit(requestDto.getPageSize());
        return getMongoOperationService().searchList(query);
    }

    /**
     * 查询全部评论
     * @return
     */
    public List<Comment> getAllComment(CommentRequestDto pageRequestDto){
        int sikp = pageRequestDto.getPageSize() * (pageRequestDto.getPage()-1);
        return getMongoOperationService().findList(sikp,pageRequestDto.getPageSize());
    }

    /**
     * 修改评论状态
     * @param requestDto
     */
    public void updateAllStatus(CommentRequestDto requestDto){
        //评论状态 0显示 1隐藏  2删除
       for(Long id : requestDto.getIds()){
           Query query = new Query();
           Update update = new Update();
           query.addCriteria(Criteria.where("_id").is(id));
           update.set("status",requestDto.getStatus());
           mongoTemplate.updateFirst(query,update,Comment.class);
       }

    }


}
