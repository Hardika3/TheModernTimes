package com.mcoefoss.modernTimes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.net.ConnectivityManager;
import android.content.Context;

import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    GridView gridView;
    String[] numberWord;
    Bitmap[] numberImages;
    List<ParseObject> Clubs;
    WebView CollegeView;
    RecyclerView home;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        CollegeView = (WebView)findViewById(R.id.webViewMain);
        CollegeView.loadUrl("http://moderncoe.edu.in/");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        short option_no=0;
        try {
            if (gridView.getVisibility() == View.VISIBLE) {
                option_no = 1;
                gridView.setVisibility(View.INVISIBLE);
                CollegeView.setVisibility(View.VISIBLE);
            } else if (home.getVisibility() == View.VISIBLE) {
                option_no = 2;
                home.setVisibility(View.INVISIBLE);
                CollegeView.setVisibility(View.VISIBLE);
            } else if (drawer.isDrawerOpen(GravityCompat.START)) {
                option_no = 3;
                drawer.closeDrawer(GravityCompat.START);
            }
        }catch(NullPointerException e){
            Log.e("NullPointer",e.toString());
        }
        if(option_no == 0){
            Toast.makeText(getApplicationContext(),"Remove the application from recent to close",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        TextView displayName = findViewById(R.id.DISPLAY_name);
        String text = "User: " + ParseUser.getCurrentUser().getString("Display_name");
        displayName.setText(text);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent about_intent = new Intent(Main2Activity.this,aboutUs.class);
            startActivity(about_intent);
        }
        else if(id == R.id.logout){
            ParseUser.logOutInBackground();
            Toast.makeText(Main2Activity.this,"Logged out!",Toast.LENGTH_SHORT).show();
            Intent login_intent = new Intent(Main2Activity.this,MainActivity.class);
            finish();
            startActivity(login_intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        home = findViewById(R.id.homePage);
        gridView = findViewById(R.id.gridview);

        int id = item.getItemId();
        LinearLayout l = findViewById(R.id.home_linear);
        l.setVisibility(View.INVISIBLE);

        if( id == R.id.nav_home) {
           if(gridView.getVisibility() == View.VISIBLE){
               gridView.setVisibility(View.INVISIBLE);
           }
            if(CollegeView.getVisibility() == View.VISIBLE){
                CollegeView.setVisibility(View.INVISIBLE);
            }
            home.setVisibility(View.VISIBLE);
            RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getBaseContext());
            RviewAdapter adapter = new RviewAdapter(getBaseContext(),ParseHelp.getPosts());

            home.setLayoutManager(layoutManager);
            home.setAdapter(adapter);

        } else if (id == R.id.nav_clubs) {
            final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            if(home.getVisibility()==View.VISIBLE){
                home.setVisibility(View.INVISIBLE);
            }
            if(CollegeView.getVisibility() == View.VISIBLE){
                CollegeView.setVisibility(View.INVISIBLE);
            }
            gridView.setVisibility(View.VISIBLE);
            getClubs();
            com.mcoefoss.modernTimes.MainAdapter adapter=new com.mcoefoss.modernTimes.MainAdapter(Main2Activity.this,numberWord,numberImages);
            gridView.setAdapter(adapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                    Toast.makeText(getApplicationContext(),"You Clicked "+numberWord[+i],Toast.LENGTH_SHORT).show();
                    Intent club_intent = null;
                    ParseHelp.currentClub = Clubs.get(i);
                    club_intent=new Intent(Main2Activity.this,clubinfo.class);
                    startActivity(club_intent);
                }
            });

        } else if(id==R.id.post_upload) {
            Intent post_upload_intent = new Intent(Main2Activity.this,postUpload.class);
            startActivity(post_upload_intent);

        } else if (id == R.id.deletePosts) {
            Intent deleteIntent = new Intent(Main2Activity.this,deletePost.class);
            startActivity(deleteIntent);
        } else if (id == R.id.nav_about) {
            Intent about_intent = new Intent(Main2Activity.this,aboutUs.class);
            startActivity(about_intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void getClubs() {
        ParseQuery<ParseObject> clubQuery = new ParseQuery<ParseObject>("Clubs");
        try {
            Clubs = clubQuery.find();
            Log.d("Club", "Retrieved " + Clubs.size() + " clubs");
            numberWord = new String[Clubs.size()];
            numberImages = new Bitmap[Clubs.size()];
            int i = 0;
            for(ParseObject club : Clubs){
                numberWord[i] = club.getString("Title");
                numberImages[i] = ParseHelp.toImg(club,"Display_picture");
                i++;
            }
        }catch(ParseException e){
            Log.e("ParseError:",e.toString());
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
