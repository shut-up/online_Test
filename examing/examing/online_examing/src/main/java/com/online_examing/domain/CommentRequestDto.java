package com.online_examing.domain;


import com.domain.Comment;

import java.io.Serializable;
import java.util.List;

public class CommentRequestDto implements Serializable {
    private int page;
    private int pageSize;
    private List<Integer> status;//评论状态 0显示 1隐藏  2删除
    private List<Integer>  check;//0未审核 1已审核
    private List<Integer>  complaintStatus;//0被投诉 1已处理
    private List<Long> ids;
    private Long id;
    private Comment comment;

    public String getIdStr(){
        return this.id+"";
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }

    public List<Integer> getCheck() {
        return check;
    }

    public void setCheck(List<Integer> check) {
        this.check = check;
    }

    public List<Integer> getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(List<Integer> complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
