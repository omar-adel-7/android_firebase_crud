package com.example.firebase_crud.ViewHolders;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.firebase_crud.R;
import com.example.firebase_crud.model.Post;


/**
 * Created by Net22 on 11/26/2017.
 */

public class PostsVH extends RecyclerView.ViewHolder {
    @BindView(R.id.tvPost)
    TextView tvPost;
    @BindView(R.id.btDel)
      Button btDel;
    View itemView;
      Context context;

    public PostsVH(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public void bindData(final Object item, final int position) {

        final Post itemInfo = (Post) item;
        tvPost.setText(itemInfo.getDesc());

        btDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                Query query = ref.child("posts").orderByChild("key").equalTo(itemInfo.getKey());

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapShot: dataSnapshot.getChildren()) {
                            childSnapShot.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(getClass().getName(), "onCancelled", databaseError.toException());
                    }
                });
            }
        });



    }

    public static View getView(Context context, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(R.layout.row_post, viewGroup, false);
    }

}

