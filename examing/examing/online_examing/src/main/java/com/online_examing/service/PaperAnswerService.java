package com.online_examing.service;

import com.domain.ExamPaper;
import com.domain.PaperAnswer;
import com.domain.PaperQuestion;
import com.online_examing.repository.ExamRepository;
import com.online_examing.repository.PaperAnswerRepository;
import com.utils.DefaultKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: walton
 * @Description: 登录、注册的Service
 * @Createtime: 2017/11/28
 */

@Service
public class PaperAnswerService {

    @Autowired
    private PaperAnswerRepository paperAnswerRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private DefaultKeyGenerator defaultKeyGenerator ;

    /**
     *@Description: 插入题目
     *@Date: 2017/11/28
     */
    public PaperAnswer addPaperAnswer(PaperAnswer paperAnswer){
        if(paperAnswer.getId()==0)
            paperAnswer.setId((Long) defaultKeyGenerator.generateKey());
        if(paperAnswer.getCreateTime()==0) {
            long now = System.currentTimeMillis();
            paperAnswer.setCreateTime(now);
        }
        int score = 0;
        int totalScore = 0;
        List<String> summitAnswer = new ArrayList<>();
        if(paperAnswer.getSelectAnswers().size()!=0){
            for(String s: paperAnswer.getSelectAnswers()){
                if(s!=null)
                    summitAnswer.add(s);
            }
        }

        if(paperAnswer.getTfAnswers().size()!=0){
            for(String s: paperAnswer.getTfAnswers()){
                if(s!=null)
                    summitAnswer.add(s);
            }
        }
        if(paperAnswer.getFullAnswer().size()!=0){
            for(String s: paperAnswer.getFullAnswer()){
                if(s!=null)
                    summitAnswer.add(s);
            }
        }
        ExamPaper examPaper = examRepository.findById(paperAnswer.getPaperId());
        List<PaperQuestion> questions = examPaper.getExamQuestion();
        for(int i =0; i < questions.size(); i++){
            totalScore += questions.get(i).getScore();
            if(questions.get(i).getType()==0){
                if(summitAnswer.get(i).equals(questions.get(i).getChoiceAnswer().get(0)))
                    score += questions.get(i).getScore();
            }else if(questions.get(i).getType()==1){
                if(summitAnswer.get(i).equals(questions.get(i).getTfAnswer()))
                    score += questions.get(i).getScore();
            }else if(questions.get(i).getType()==2){
                if(summitAnswer.get(i).equals(questions.get(i).getBlankAnswer()))
                    score += questions.get(i).getScore();
            }
        }

        paperAnswer.setScore((int)score*100/totalScore);
        return paperAnswerRepository.save(paperAnswer);
    }

}
