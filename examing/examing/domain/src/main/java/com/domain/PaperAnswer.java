package com.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


/**
 * @Author: walton
 * @Description: 试卷的答题卡
 * @Createtime: 2017/11/28
 */

@Document(collection="PaperAnswer")
public class PaperAnswer extends BaseEntity{
    private Long stuId;
    private Long paperId;
    private List<String> selectAnswers;
    private List<String> tfAnswers;
    private List<String> fullAnswer;
    private Integer score;
    private long createTime;

    public Long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public List<String> getSelectAnswers() {
        return selectAnswers;
    }

    public void setSelectAnswers(List<String> selectAnswers) {
        this.selectAnswers = selectAnswers;
    }

    public List<String> getTfAnswers() {
        return tfAnswers;
    }

    public void setTfAnswers(List<String> tfAnswers) {
        this.tfAnswers = tfAnswers;
    }

    public List<String> getFullAnswer() {
        return fullAnswer;
    }

    public void setFullAnswer(List<String> fullAnswer) {
        this.fullAnswer = fullAnswer;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
