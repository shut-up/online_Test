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
    public PaperDetail addQuestion(PaperDetail paperDetail){
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
    public PaperDetail deleteQuestion(Long id){
        PaperDetail paperDetail = paperRepository.findOne(id);
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
    public List<PaperDetail> getPaperList(PaperRequestDto paperRequestDto){
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "createTime");
        Sort sort = new Sort(order);
        Pageable pageable=new PageRequest(paperRequestDto.getPage()-1, paperRequestDto.getPageSize(), sort);//对数据进行分页
        List<PaperDetail> list =  paperRepository.findAll(pageable).getContent();
        List<PaperDetail> result_list =  new ArrayList<>();
        for(PaperDetail paperDetail : list){
            if(paperDetail.getType()==paperRequestDto.getType())
                result_list.add(paperDetail);
        }
        return result_list;
    }

    /**
    *@Description:  获取指定类型题目的总数目
    *@Date: 2017/12/4
    */
    public  List<PaperDetail> getPaperSize(int type){
        return paperRepository.findByType(type);
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
