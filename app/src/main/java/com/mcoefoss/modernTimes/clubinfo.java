package com.mcoefoss.modernTimes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class clubinfo extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RviewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cards_club);

        init();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }
    private void init(){
        this.recyclerView = findViewById(R.id.recyclerView);
        this.layoutManager = new LinearLayoutManager(getBaseContext());
        this.adapter = new RviewAdapter(getBaseContext(),ParseHelp.getPosts(ParseHelp.currentClub));
    }

}
