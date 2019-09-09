package com.example.firebase_crud.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by logonrm on 01/08/2017.
 */

@IgnoreExtraProperties
public class Post {
    public Post( ) {

    }

    public Post(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String descricao) {
        this.desc = descricao;
    }
}
