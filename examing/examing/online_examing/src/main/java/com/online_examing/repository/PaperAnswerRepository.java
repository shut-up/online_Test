package com.online_examing.repository;

import com.domain.PaperAnswer;
import com.domain.PaperDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: walton
 * @Description: 登录、注册的Repository
 * @Createtime: 2017/11/28
 */

@Repository
public interface PaperAnswerRepository extends MongoRepository<PaperAnswer,Long> {

    public PaperAnswer findByStuIdAndPaperId(Long stuId, Long paperId);

}
