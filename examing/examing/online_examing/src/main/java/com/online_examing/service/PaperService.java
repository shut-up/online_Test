package com.online_examing.service;

import com.domain.PaperDetail;
import com.online_examing.domain.PaperRequestDto;
import com.online_examing.repository.PaperRepository;
import com.utils.DefaultKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public PaperDetail addQuestion(PaperDetail paperDetail){
        paperDetail.setId((Long) defaultKeyGenerator.generateKey());
        long now = System.currentTimeMillis();
        paperDetail.setCreateTime(now);
        return paperRepository.save(paperDetail);
    }

    /**
    *@Description: 获取当前页数的题目
    *@Date: 2017/12/4
    */
    public List<PaperDetail> getPaperList(PaperRequestDto paperRequestDto){
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "createTime");
        Sort sort = new Sort(order);
        Pageable pageable=new PageRequest(paperRequestDto.getPage()-1, paperRequestDto.getPageSize(), sort);//对数据进行分页
        List<PaperDetail> list =  paperRepository.findAll(pageable).getContent();
        return list;
    }

    /**
    *@Description:  获取题目的总数目
    *@Date: 2017/12/4
    */
    public  List<PaperDetail> getPaperSize(){
        return paperRepository.findAll();
    }

    /**
    *@Description: 查找题目
    *@Date: 2017/12/6
    */
    public Map searchQuestion(PaperRequestDto paperRequestDto){
        //总页数
        int total = 0;
        //按时间降序排序
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "createTime");
        Sort sort = new Sort(order);
        //获取所有试卷
        List<PaperDetail> paperDetailList_temp = paperRepository.findAll(sort);
        //获取模糊关键词
        String keywords = paperRequestDto.getKeywords();
        //匹配模糊的关键词的试卷
        List<PaperDetail> paperDetailList = new ArrayList<>();
        for (PaperDetail paper : paperDetailList_temp) {
            if (paper.getTitle().indexOf(keywords) != -1) {//数据库与前台数据一一匹配，没有的话等于-1
                paperDetailList.add(paper);
            }
        }
        total = paperDetailList.size();
        //当前页数的试卷
        List<PaperDetail> result = new ArrayList<>();
        int start = (paperRequestDto.getPage()-1) * paperRequestDto.getPageSize();
        int end = start + paperRequestDto.getPageSize() - 1;
        for(int i = start; i <= end; i++){
            if(i >= total)
                break;
            result.add(paperDetailList.get(i));
        }
        Map resultMap = new HashMap();
        resultMap.put("total",total);
        resultMap.put("list",result);
        return resultMap;
    }

}
