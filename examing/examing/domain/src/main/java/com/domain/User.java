package com.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author: walton
 * @Description: 用户表
 * @Createtime: 2017/11/28
 */

@Document(collection="User")
public class User {
    private Long id;
    private String name;
    private String password;
    private Integer type;   //0学生、1教师

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
