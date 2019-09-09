package com.example.firebase_crud.Posts;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.firebase_crud.model.Test;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import butterknife.BindView;
import modules.basemvp.BaseSupportFragment;
import modules.general.ui.adapters.GenericRecyclerViewAdapter;
import modules.general.utils.utils.KeyBoardUtil;
import com.example.firebase_crud.R;
import com.example.firebase_crud.ViewHolders.PostsVH;
import com.example.firebase_crud.model.Post;

public class PostsFragment extends BaseSupportFragment<PostsFragmentPresenter> implements

        IPostsFragmentContract.IPostsFragmentContractView {

    GenericRecyclerViewAdapter genericRecyclerViewAdapter;

    @BindView(R.id.rvPosts)
    RecyclerView rvPosts;
    @BindView(R.id.etPost)
    EditText etPost;
    @BindView(R.id.btAdd)
    Button btAdd;
    @BindView(R.id.llAddPost)
    LinearLayout llAddPost;


    private FirebaseRecyclerAdapter<Post, PostsVH> firebaseRecyclerAdapter;


    public GenericRecyclerViewAdapter getGenericRecyclerViewAdapter() {
        return genericRecyclerViewAdapter;
    }


    public FirebaseRecyclerAdapter<Post, PostsVH> getFirebaseRecyclerAdapter() {
        return firebaseRecyclerAdapter;
    }

    public PostsFragment() {

    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_posts;
    }

    @Override
    public void configureUI() {
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String desc = etPost.getText().toString();

                if (TextUtils.isEmpty(desc)) {
                    Toast.makeText(getContext(), "empty post", Toast.LENGTH_LONG).show();
                    return;
                }
                DatabaseReference databaseReferenceAdd = getPostsFragmentPresenter().getPostsReference().push();
                String key = databaseReferenceAdd.getKey();
                Post postObject = new Post(key, desc);
                databaseReferenceAdd.setValue(postObject);
                etPost.setText("");
                KeyBoardUtil.hideSoftKeyboard(getContainerActivity());

            }
        });



        //push  Test model for testing purpose
//        DatabaseReference databaseReferenceAdd = getPostsFragmentPresenter().getTestsReference().push();
//        Test testObject = new Test("title test 1");
//        databaseReferenceAdd.setValue(testObject);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(false);
        rvPosts.setHasFixedSize(true);
        rvPosts.setLayoutManager(layoutManager);

        setNormalRecycler();

    //  setFireBaseUiRecycler();
    }

    @Override
    public PostsFragmentPresenter injectDependencies() {

        return new PostsFragmentPresenter(getContainerActivity(), this);
    }


    private void setNormalRecycler() {

        genericRecyclerViewAdapter = new GenericRecyclerViewAdapter(getActivity(), new GenericRecyclerViewAdapter.AdapterDrawData() {
            @Override
            public RecyclerView.ViewHolder getView(ViewGroup parent, int viewType) {

                return new PostsVH(getActivity(),
                        PostsVH.getView(getActivity(), parent));
            }

            @Override
            public void bindView(GenericRecyclerViewAdapter genericRecyclerViewAdapter,
                                 RecyclerView.ViewHolder holder, Object item, int position) {
                ((PostsVH) holder).bindData(
                        genericRecyclerViewAdapter.getItem(position), position);
            }
        });

        rvPosts.setAdapter(genericRecyclerViewAdapter);

    }

    private void setFireBaseUiRecycler() {
        Query query = getPostsFragmentPresenter().getPostsReference();

//        FirebaseRecyclerOptions<Post> options =
//                new FirebaseRecyclerOptions.Builder<Post>()
//                        .setQuery(query, new SnapshotParser<Post>() {
//                            @NonNull
//                            @Override
//                            public Post parseSnapshot(@NonNull DataSnapshot snapshot) {
//                                return ...;
//                            }
//                        });

        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(query, Post.class)
                        .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post, PostsVH>(
                options) {
            @NonNull
            @Override
            public PostsVH onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                return new PostsVH(getActivity(),
                        PostsVH.getView(getActivity(), parent));
            }

            @Override
            protected void onBindViewHolder(@NonNull PostsVH holder, int position, @NonNull Post model) {

                ((PostsVH) holder).bindData(
                        model, position);
            }

        };

        rvPosts.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();

        getPostsFragmentPresenter().onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

        getPostsFragmentPresenter().onStop();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public PostsFragmentPresenter getPostsFragmentPresenter() {
        return ((PostsFragmentPresenter) this.getPresenter());
    }
}
