package com.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @Author: walton
 * @Description: 教师管理班级表
 * @Createtime: 2017/11/28
 */

@Document(collection="ManagerClass")
public class ManagerClass  {
    private String grade;
    private List<String> school;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<String> getSchool() {
        return school;
    }

    public void setSchool(List<String> school) {
        this.school = school;
    }
}
