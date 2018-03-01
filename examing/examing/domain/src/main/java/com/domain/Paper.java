package com.domain;

import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


/**
 * @Author: walton
 * @Description: 试卷表
 * @Createtime: 2017/11/28
 */

@Document(collection="Paper")
public class Paper extends BaseEntity{
    private String title;
    private List<String> choiceAnswer;
    private String tfAnswer;
    private String blankAnswer;
    private List<String> blankAnswers;
    private String selectionA;
    private String selectionB;
    private String selectionC;
    private String selectionD;
    private Integer type;//0选择题，1判断题，2填空题
    private long createTime;//创建时间
    private Integer status; //0隐藏，1显示，2删除
    private Integer score;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getChoiceAnswer() {
        return choiceAnswer;
    }

    public void setChoiceAnswer(List<String> choiceAnswer) {
        this.choiceAnswer = choiceAnswer;
    }

    public String getSelectionA() {
        return selectionA;
    }

    public void setSelectionA(String selectionA) {
        this.selectionA = selectionA;
    }

    public String getSelectionB() {
        return selectionB;
    }

    public void setSelectionB(String selectionB) {
        this.selectionB = selectionB;
    }

    public String getSelectionC() {
        return selectionC;
    }

    public void setSelectionC(String selectionC) {
        this.selectionC = selectionC;
    }

    public String getSelectionD() {
        return selectionD;
    }

    public void setSelectionD(String selectionD) {
        this.selectionD = selectionD;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getTfAnswer() {
        return tfAnswer;
    }

    public void setTfAnswer(String tfAnswer) {
        this.tfAnswer = tfAnswer;
    }

    public String getBlankAnswer() {
        return blankAnswer;
    }

    public void setBlankAnswer(String blankAnswer) {
        this.blankAnswer = blankAnswer;
    }

    public List<String> getBlankAnswers() {
        return blankAnswers;
    }

    public void setBlankAnswers(List<String> blankAnswers) {
        this.blankAnswers = blankAnswers;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
