package com.online_examing.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope("prototype")
public class MongoOperationService {
    @Autowired
    private MongoTemplate mongoTemplate;

    private Class dataSource;

    public  MongoOperationService(){};

    public MongoOperationService(Class dataSource)
    {
        this.dataSource = dataSource;
    }

    public Class getDataSource() {
        return dataSource;
    }

    public void setDataSource(Class dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 刪除多個id
     * @param ids
     */
    public void deleteAll(List<Long> ids){
        Query query = new Query();
        if(ids ==null){
            return ;
        }
        query.addCriteria(Criteria.where("_id").in(ids));
        mongoTemplate.remove(query,this.dataSource);
    }

    /**
     * 分頁
     * @param skip
     * @param limit
     * @return
     */
    public List findList(int skip,int limit){
        Query query = new Query();
        query.with(new Sort(new Sort.Order(Sort.Direction.ASC,"check")));
        query.skip(skip).limit(limit);
        return  mongoTemplate.find(query,this.dataSource);
    }

    /**
     * 检索查询
     * @param query
     * @return
     */
    public Map searchList(Query query){
        Map resultMap = new HashMap();
        long total = mongoTemplate.count(query,this.dataSource);
        resultMap.put("total",total);
        resultMap.put("list",mongoTemplate.find(query, dataSource));
        return resultMap;
    }
}
