package com.online_examing.service;

import com.domain.ExamPaper;
import com.online_examing.domain.PaperRequestDto;
import com.online_examing.repository.ExamRepository;
import com.utils.DefaultKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: walton
 * @Description:
 * @Createtime: 2018/1/30
 */
@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private DefaultKeyGenerator defaultKeyGenerator;

    public ExamPaper saveExam(ExamPaper examPaper){
        if(examPaper.getId()==0)
            examPaper.setId((Long) defaultKeyGenerator.generateKey());
        examPaper.setStatus(0);
        return examRepository.save(examPaper);
    }
}
