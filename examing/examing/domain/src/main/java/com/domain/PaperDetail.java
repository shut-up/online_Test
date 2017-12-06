package com.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


/**
 * @Author: walton
 * @Description: 试卷表
 * @Createtime: 2017/11/28
 */

@Document(collection="ExamDetail")
public class PaperDetail extends BaseEntity{
    private String title;
    private List<String> answer;
    private String selectionA;
    private String selectionB;
    private String selectionC;
    private String selectionD;
    private Integer type;//0选择题，1判断题，2填空题
    private long createTime;//创建时间

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
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
}
