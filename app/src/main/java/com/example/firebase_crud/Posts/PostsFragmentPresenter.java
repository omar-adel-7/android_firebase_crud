package com.example.firebase_crud.Posts;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import com.example.firebase_crud.model.DataManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import modules.general.firebase.RealTime.listeners.IFireBaseRtCallBack;

import com.example.firebase_crud.model.Post;


/**
 * Created by Net22 on 11/13/2017.
 */

public class PostsFragmentPresenter
        implements IPostsFragmentContract.IPostsFragmentContractPresenter , IFireBaseRtCallBack {
    private final Context mContext;
    IPostsFragmentContract.IPostsFragmentContractView mView;

    private DatabaseReference mPostsReference;
    private DatabaseReference mTestsReference;



    public DatabaseReference getPostsReference() {
        return mPostsReference;
    }

    public DatabaseReference getTestsReference() {
        return mTestsReference;
    }


    public PostsFragmentPresenter(Context context, IPostsFragmentContract.IPostsFragmentContractView view) {
        mView = view;
        mContext = context;


        mPostsReference = FirebaseDatabase.getInstance().getReference("posts");
        mTestsReference = FirebaseDatabase.getInstance().getReference("tests");

    }
    @Override
    public void onStart() {


        if (((PostsFragment) mView).getFirebaseRecyclerAdapter() != null) {
            ((PostsFragment) mView).getFirebaseRecyclerAdapter().startListening();
        } else {

            DataManager.getInstance(mContext).attachDatabaseReadListener(mTestsReference);
            DataManager.getInstance(mContext).attachDatabaseReadListener(mPostsReference);

            DataManager.getInstance(mContext).setSyncListener(this);

         }


    }

    @Override
    public void onStop() {

        if (((PostsFragment) mView).getFirebaseRecyclerAdapter() != null) {
            ((PostsFragment) mView).getFirebaseRecyclerAdapter().stopListening();
        } else {
            DataManager.getInstance(mContext).detachDatabaseReadListener(mTestsReference);
            DataManager.getInstance(mContext).detachDatabaseReadListener(mPostsReference);
        }
    }

    @Override
    public void getAll(DataSnapshot dataSnapshot) {
        ArrayList<Post> posts = new ArrayList<>();
        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            if (singleSnapshot.getRef().getParent().equals(mPostsReference)) {
                Post post = singleSnapshot.getValue(Post.class);
                posts.add(post);
            }
        }
        ((PostsFragment) mView).getGenericRecyclerViewAdapter().setAll(posts);
    }

    @Override
    public void postAdded(DataSnapshot singleSnapshot) {
        if (singleSnapshot.getRef().getParent().equals(mPostsReference)) {
            Post post = singleSnapshot.getValue(Post.class);
            ((PostsFragment) mView).getGenericRecyclerViewAdapter().addItem(post);
        }

    }

    @Override
    public void postChanged(DataSnapshot singleSnapshot) {
        if (singleSnapshot.getRef().getParent().equals(mPostsReference)) {
            String key ="";
            Post post = singleSnapshot.getValue(Post.class);
            //key = singleSnapshot.getKey();
            key = post.getKey();
            for (int i = 0; i < ((PostsFragment) mView).getGenericRecyclerViewAdapter().getItemCount(); i++) {
                if (((Post) ((PostsFragment) mView).getGenericRecyclerViewAdapter().getItem(i)).getKey().equals(key)) {
                    ((PostsFragment) mView).getGenericRecyclerViewAdapter().updateItem(i, singleSnapshot.getValue(Post.class));
                    break;
                }
            }
        }
    }

    @Override
    public void postDeleted(DataSnapshot singleSnapshot) {
        if (singleSnapshot.getRef().getParent().equals(mPostsReference)) {
             String key ="";
            Post post = singleSnapshot.getValue(Post.class);
            //key = singleSnapshot.getKey();
            key = post.getKey();
            for (int i = 0; i < ((PostsFragment) mView).getGenericRecyclerViewAdapter().getItemCount(); i++) {
                if (((Post) ((PostsFragment) mView).getGenericRecyclerViewAdapter().getItem(i)).getKey().equals(key)) {
                    ((PostsFragment) mView).getGenericRecyclerViewAdapter().removeItem(i);
                    break;
                }

            }
        }
    }



    @Override
    public void onChildAdded(@NonNull DataSnapshot singleSnapshot, @Nullable String s) {
        postAdded(singleSnapshot);
        if (singleSnapshot.getRef().getParent().equals(mPostsReference)) {
            // A post has added
            Post post = singleSnapshot.getValue(Post.class);
//             Toast.makeText(mContext, "onChildAdded: " + post.getDesc(), Toast.LENGTH_SHORT).show();
        } else if (singleSnapshot.getRef().getParent().equals(mTestsReference))

        {
            Log.e(getClass().getName(), "onChildAdded:" + " not post but test found ");
        }
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot singleSnapshot, @Nullable String s) {
        postChanged(singleSnapshot);
        if (singleSnapshot.getRef().getParent().equals(mPostsReference)) {
            // A post has changed
            Post post = singleSnapshot.getValue(Post.class);
//                Toast.makeText(mContext, "onChildChanged: " + post.getDesc(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot singleSnapshot) {
        postDeleted(singleSnapshot);
        if (singleSnapshot.getRef().getParent().equals(mPostsReference)) {
            // A post has been removed
            Post post = singleSnapshot.getValue(Post.class);
          // Toast.makeText(mContext, "onChildRemoved: " + post.getDesc(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot singleSnapshot, @Nullable String s) {
        if (singleSnapshot.getRef().getParent().equals(mPostsReference)) {
            // A post has changed position
            Post post = singleSnapshot.getValue(Post.class);
//              Toast.makeText(mContext, "onChildMoved: " + post.getDesc(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.e(getClass().getName(), "DatabaseError:onCancelled", databaseError.toException());
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        getAll(dataSnapshot);
    }


}