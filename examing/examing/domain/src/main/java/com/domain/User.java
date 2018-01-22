package com.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @Author: walton
 * @Description: 用户表
 * @Createtime: 2017/11/28
 */

@Document(collection="User")
public class User extends BaseEntity{
    private Long accountNumber;
    private String name;
    private String password;
    private Integer type;   //0学生、1教师
    private List<ManagerClass> managerClasses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
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

    public List<ManagerClass> getManagerClasses() {
        return managerClasses;
    }

    public void setManagerClasses(List<ManagerClass> managerClasses) {
        this.managerClasses = managerClasses;
    }
}
