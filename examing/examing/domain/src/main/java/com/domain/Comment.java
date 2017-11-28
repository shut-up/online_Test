package com.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * 评论表
 */
@Document(collection="comment")
public class Comment extends  BaseEntity{

    private int type;//评论类型:1为新闻,2为直播
    @Field("outer_id")
    private long outerId;//新闻或者直播Id
    @Field("root_id")
    private long rootId;//根评论id[当为主评论时，它为0]
    @Field("parent_id")
    private long parentId;//父评论id[当为主评论时，它为0]
    @Field("comment_list")
    @DBRef
    private List<Comment> commentList;//对话列表(当插入评论的时候，先查询父评论的 comment_list,然后把自己更新到列表里)
    private int check;//1已审核  0未审核
    private int status;//0:显示 1:隐藏  2:删除
    private Editor editor;//审核人
    private String content;//评论内容
    @Field("create_time")
    private long createTime;//创建时间
    @Field("update_time")
    private long updateTime;//修改时间


    public  Comment(){};

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getOuterId() {
        return outerId;
    }

    public void setOuterId(long outerId) {
        this.outerId = outerId;
    }

    public long getRootId() {
        return rootId;
    }

    public void setRootId(long rootId) {
        this.rootId = rootId;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "type=" + type +
                ", outerId=" + outerId +
                ", rootId=" + rootId +
                ", parentId=" + parentId +
                ", commentList=" + commentList +
                ", check=" + check +
                ", status=" + status +
                ", editor=" + editor +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", id=" + id +
                '}';
    }
}
