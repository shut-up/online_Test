package com.online_examing.domain;


import com.domain.Comment;

import java.io.Serializable;
import java.util.List;

public class PaperRequestDto implements Serializable {
    private int page;
    private int pageSize;
    private String keywords;
    private int type;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

}