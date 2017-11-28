package com.online_examing.service;

import com.domain.MyEntity;
import com.utils.DefaultKeyGenerator;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 增删改查操作
 */
public abstract class BaseService {

    abstract MongoRepository getRepository();
    abstract MongoOperationService getMongoOperationService();


    /**
     * 添加
     * @param data
     * @return
     */
    public MyEntity addOne(MyEntity data){
        data.setId((long)(new DefaultKeyGenerator()).generateKey());
        getRepository().insert(data);
        return data;
    }

}
