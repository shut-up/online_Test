package com.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @Author: walton
 * @Description: 教师管理的班级表
 * @Createtime: 2017/11/28
 */

@Document(collection="ManagerClass")
public class ManagerClass  {
    private String grade;
    private String school;
    private List<String> major;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public List<String> getMajor() {
        return major;
    }

    public void setMajor(List<String> major) {
        this.major = major;
    }
}
