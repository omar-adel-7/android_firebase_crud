package com.example.firebase_crud.Posts;

import com.google.firebase.database.DataSnapshot;

import modules.basemvp.Base;

/**
 * Created by Net22 on 11/13/2017.
 */

public interface IPostsFragmentContract {

    public interface IPostsFragmentContractView {
    }

    public interface IPostsFragmentContractPresenter extends Base.IPresenter {
        void onStart( ) ;
        void onStop( ) ;
        void getAll(DataSnapshot dataSnapshot) ;
        void postAdded(DataSnapshot singleSnapshot) ;
        void postChanged(DataSnapshot singleSnapshot) ;
        void postDeleted(DataSnapshot singleSnapshot) ;


     }
}
