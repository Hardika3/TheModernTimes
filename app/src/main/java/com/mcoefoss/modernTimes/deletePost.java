package com.mcoefoss.modernTimes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.parse.ParseUser;

public class deletePost extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DeleteAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_post);
        init();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void init() {
        this.recyclerView = findViewById(R.id.recyclerViewDelete);
        this.layoutManager = new LinearLayoutManager(getBaseContext());
        this.adapter = new DeleteAdapter(getBaseContext(), ParseHelp.getPostsBy(ParseUser.getCurrentUser()));
    }
}
