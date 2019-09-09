package com.example.firebase_crud.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by logonrm on 01/08/2017.
 */

@IgnoreExtraProperties
public class Test {
    public Test( ) {

    }

    public Test(  String title) {
        this.title = title;
     }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


 }
