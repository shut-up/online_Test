package com.online_examing.repository;

import com.domain.PaperQuestion;
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
public interface PaperRepository extends MongoRepository<PaperQuestion,Long> {

    public List<PaperQuestion> findByTitleContainingAndType(String title, int type,Pageable pageable);

    public List<PaperQuestion> findByTitleContainingAndType(String title, int type);

    public List<PaperQuestion> findByType(int type);

    public List<PaperQuestion> findByType(int type, Pageable pageable);

    public List<PaperQuestion> findByType(int type, Sort sort);

}
