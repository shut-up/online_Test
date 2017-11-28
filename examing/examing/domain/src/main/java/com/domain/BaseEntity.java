package com.domain;

import org.springframework.data.annotation.Id;

public class BaseEntity implements MyEntity{

    @Id
    protected long id;

    public String getIdStr(){
        return this.id+"";
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
