package com.online_examing.service;

import com.domain.PaperQuestion;
import com.online_examing.domain.PaperRequestDto;
import com.online_examing.repository.PaperRepository;
import com.utils.DefaultKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: walton
 * @Description: 登录、注册的Service
 * @Createtime: 2017/11/28
 */

@Service
public class PaperService {

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private DefaultKeyGenerator defaultKeyGenerator ;

    /**
     *@Description: 插入题目
     *@Date: 2017/11/28
     */
    public PaperQuestion addQuestion(PaperQuestion paperDetail){
        if(paperDetail.getId()==0)
            paperDetail.setId((Long) defaultKeyGenerator.generateKey());
        if(paperDetail.getCreateTime()==0) {
            long now = System.currentTimeMillis();
            paperDetail.setCreateTime(now);
        }
        if(paperDetail.getType()==2){
            String[] answer = paperDetail.getBlankAnswer().split("&");
            List<String> blankAnswer_temp = new ArrayList<>();
            for(String a : answer)
                blankAnswer_temp.add(a);
            paperDetail.setBlankAnswers(blankAnswer_temp);
        }
        return paperRepository.save(paperDetail);
    }

    /**
     *@Description: 删除题目
     *@Date: 2017/11/28
     */
    public PaperQuestion deleteQuestion(Long id){
        PaperQuestion paperDetail = paperRepository.findOne(id);
        if(paperDetail!=null){
            paperRepository.delete(id);
            return paperDetail;
        }else {
            return null;
        }
    }


    /**
    *@Description: 获取当前页数的题目
    *@Date: 2017/12/4
    */
    public List<PaperQuestion> getPaperList(PaperRequestDto paperRequestDto){
        if(paperRequestDto.getType()==3){
            Sort.Order order = new Sort.Order(Sort.Direction.ASC,"type");
            Sort sort = new Sort(order);
            return paperRepository.findAll(sort);
        } else{
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, "createTime");
            Sort sort = new Sort(order);
            Pageable pageable=new PageRequest(paperRequestDto.getPage()-1, paperRequestDto.getPageSize(), sort);//对数据进行分页
            List<PaperQuestion> list =  paperRepository.findByType(paperRequestDto.getType(),pageable);
            return list;
        }


    }

    /**
    *@Description:  获取指定类型题目的总数目
    *@Date: 2017/12/4
    */
    public  List<PaperQuestion> getPaperSize(int type){
        return paperRepository.findByType(type);
    }

    /**
    *@Description: 查找题目
    *@Date: 2017/12/6
    */
    public Map searchQuestion(PaperRequestDto paperRequestDto){
        //总页数
        //按时间降序排序
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "createTime");
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(paperRequestDto.getPage()-1,paperRequestDto.getPageSize(),sort);
        List<PaperQuestion> paper = paperRepository.findByTitleContainingAndType(paperRequestDto.getKeywords(),paperRequestDto.getType(),pageable);
        int total = paperRepository.findByTitleContainingAndType(paperRequestDto.getKeywords(),paperRequestDto.getType()).size();
        Map resultMap = new HashMap();
        resultMap.put("total",total);
        resultMap.put("list",paper);
        return resultMap;
    }

}
