package com.mcoefoss.modernTimes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class home extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RviewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void init(){
        this.recyclerView = findViewById(R.id.recyclerView);
        this.layoutManager = new LinearLayoutManager(getBaseContext());
        this.adapter = new RviewAdapter(getBaseContext(),ParseHelp.getPosts());
    }
}
