package com.online_examing.repository;

import com.domain.ExamPaper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: walton
 * @Description:
 * @Createtime: 2018/1/30
 */

@Repository
public interface ExamRepository extends MongoRepository<ExamPaper,Long>{
    public ExamPaper findById(long id);

    public List<ExamPaper> findByExamClassExists(String examClass);

    public List<ExamPaper> findByExamClassContains(String ca);
}
