package com.online_examing.domain;


import com.domain.Comment;
import com.domain.Paper;

import java.io.Serializable;
import java.util.List;

public class PaperRequestDto implements Serializable {
    private int page;
    private int pageSize;
    private String keywords;
    private int type;
    private int status;
    private String ids;
    private long accountNumber;
    private String grade;
    private String school;
    private List<String> major;
    private String password;
    private List<String> examClass;
    private List<String> examTime;
    private List<Paper> examQuestion;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

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

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getExamClass() {
        return examClass;
    }

    public void setExamClass(List<String> examClass) {
        this.examClass = examClass;
    }

    public List<String> getExamTime() {
        return examTime;
    }

    public void setExamTime(List<String> examTime) {
        this.examTime = examTime;
    }

    public List<Paper> getExamQuestion() {
        return examQuestion;
    }

    public void setExamQuestion(List<Paper> examQuestion) {
        this.examQuestion = examQuestion;
    }
}
