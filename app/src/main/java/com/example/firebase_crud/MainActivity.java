package com.example.firebase_crud;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase_crud.Posts.PostsFragment;

public class MainActivity extends AppCompatActivity
       {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeFragment(new PostsFragment());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void changeFragment(Fragment fragment) {
         getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContainer, fragment)
                .disallowAddToBackStack()
                .commit();
    }
}
