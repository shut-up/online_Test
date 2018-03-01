package com.online_examing.controller;

import com.domain.ExamPaper;
import com.domain.PaperDetail;
import com.online_examing.Routes;
import com.online_examing.domain.PaperRequestDto;
import com.online_examing.repository.ExamRepository;
import com.online_examing.service.ExamService;
import com.utils.RestDoing;
import com.utils.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: walton
 * @Description:
 * @Createtime: 2018/1/30
 */
@RestController
@RequestMapping(Routes.EXAM_BASE_ROUTE)
public class ExamController {
    @Autowired
    private ExamService examService;
    @Autowired
    private ExamRepository examRepository;
    /**
     * 生成试卷
     */
    @PostMapping(Routes.EXAM_CREATE_UPDATE)
    public ExamPaper addExam(@RequestBody ExamPaper examPaper){
        return examService.saveExam(examPaper);
    }

    @GetMapping(Routes.EXAM_GET)
    public List<ExamPaper> getExam(){
        return examRepository.findAll();
    }

    @GetMapping(Routes.EXAM_GET_PAPEER_BY_ID)
    public ExamPaper getExamPaperById(@RequestParam(value = "examId")String examId){
        return examRepository.findById(Long.parseLong(examId));
    }
}
