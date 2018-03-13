package com.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @Author: walton
 * @Description: 考试的试卷表（包括试题，考试班级等）
 * @Createtime: 2018/1/30
 */
@Document(collection="ExamPaper")
public class ExamPaper extends BaseEntity {
    private String title;
    private List<String> examClass;
    private String beginTime;
    private String endTime;
    private List<PaperQuestion> examQuestion;
    private Integer status; //0代表未考试，1代表已考完

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getExamClass() {
        return examClass;
    }

    public void setExamClass(List<String> examClass) {
        this.examClass = examClass;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<PaperQuestion> getExamQuestion() {
        return examQuestion;
    }

    public void setExamQuestion(List<PaperQuestion> examQuestion) {
        this.examQuestion = examQuestion;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
