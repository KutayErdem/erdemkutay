package com.example.learnin5;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.example.learnin5.adapter.YoutubeVideoAdapter;
import com.example.learnin5.model.YoutubeVideoModel;
import com.example.learnin5.utils.RecyclerViewOnClickListener;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private TextView name_surname_email;
    private Button logout;
    private String _name_surname;
    private String _username;
    private String _password;
    private String _email;
    private SharedPreferences prefavs;
    private SharedPreferences remember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        _name_surname = getIntent().getStringExtra("namesurname_");
        _username = getIntent().getStringExtra("user_name");
        _password = getIntent().getStringExtra("pass_word");
        _email = getIntent().getStringExtra("email_");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        name_surname_email = (TextView)hView.findViewById(R.id.namesurnameemail);
        logout = (Button)hView.findViewById(R.id.logout);

        name_surname_email.setText(_name_surname + "\n" + _email);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remember = getSharedPreferences("RememberMe", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = remember.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(FavoritesActivity.this, MainActivity.class));
            }
        });
        setUpRecyclerView();
        populateRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void populateRecyclerView() {
        final ArrayList<YoutubeVideoModel> youtubeVideoModelArrayList = generateDummyVideoList();
        YoutubeVideoAdapter adapter = new YoutubeVideoAdapter(this, youtubeVideoModelArrayList);
        recyclerView.setAdapter(adapter);

        //set click event
        recyclerView.addOnItemTouchListener(new RecyclerViewOnClickListener(this, new RecyclerViewOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                //start youtube player activity by passing selected video id via intent
                startActivity(new Intent(FavoritesActivity.this, FavoritesPlayerActivity.class)
                        .putExtra("video_id", youtubeVideoModelArrayList.get(position).getVideoId())
                        .putExtra("video_title",youtubeVideoModelArrayList.get(position).getTitle())
                        .putExtra("video_duration",youtubeVideoModelArrayList.get(position).getDuration())
                        .putExtra("username", _username).putExtra("password", _password)
                        .putExtra("namesurname", _name_surname).putExtra("email", _email)
                        .putExtra("activity_name","favorites"));

            }
        }));
    }

    private ArrayList<YoutubeVideoModel> generateDummyVideoList() {
        ArrayList<YoutubeVideoModel> youtubeVideoModelArrayList = new ArrayList<>();

        prefavs = getSharedPreferences("UserFavorites", Context.MODE_PRIVATE);

        String[] videoIDArrayMain = getResources().getStringArray(R.array.main_video_id_array);
        String[] videoTitleArrayMain = getResources().getStringArray(R.array.main_video_title_array);
        String[] videoDurationArrayMain = getResources().getStringArray(R.array.main_video_duration_array);

        /*String[] videoIDArrayMath = getResources().getStringArray(R.array.math_video_id_array);
        String[] videoTitleArrayMath = getResources().getStringArray(R.array.math_video_title_array);
        String[] videoDurationArrayMath = getResources().getStringArray(R.array.main_video_duration_array);

        String[] videoIDArrayEngineering = getResources().getStringArray(R.array.engineering_video_id_array);
        String[] videoTitleArrayEngineering = getResources().getStringArray(R.array.engineering_video_title_array);
        String[] videoDurationArrayEngineering = getResources().getStringArray(R.array.engineering_video_duration_array);

        String[] videoIDArrayScience = getResources().getStringArray(R.array.science_video_id_array);
        String[] videoTitleArrayScience = getResources().getStringArray(R.array.science_video_title_array);
        String[] videoDurationArrayScience = getResources().getStringArray(R.array.science_video_duration_array);*/

        for (int i = 0; i < videoIDArrayMain.length; i++) {
            if ( prefavs.contains( _username + _password + videoIDArrayMain[i]) ) {
                YoutubeVideoModel youtubeVideoModel = new YoutubeVideoModel();
                youtubeVideoModel.setVideoId(videoIDArrayMain[i]);
                youtubeVideoModel.setTitle(videoTitleArrayMain[i]);
                youtubeVideoModel.setDuration(videoDurationArrayMain[i]);

                youtubeVideoModelArrayList.add(youtubeVideoModel);
            }
        }
        /*for (int i = 0; i < videoIDArrayMath.length; i++) {
            if ( prefavs.contains( _username + _password + videoIDArrayMath[i]) ) {
                YoutubeVideoModel youtubeVideoModel = new YoutubeVideoModel();
                youtubeVideoModel.setVideoId(videoIDArrayMath[i]);
                youtubeVideoModel.setTitle(videoTitleArrayMath[i]);
                youtubeVideoModel.setDuration(videoDurationArrayMath[i]);

                youtubeVideoModelArrayList.add(youtubeVideoModel);
            }
        }

        for (int i = 0; i < videoIDArrayEngineering.length; i++) {
            if ( prefavs.contains( _username + _password + videoIDArrayEngineering[i]) ) {
                YoutubeVideoModel youtubeVideoModel = new YoutubeVideoModel();
                youtubeVideoModel.setVideoId(videoIDArrayEngineering[i]);
                youtubeVideoModel.setTitle(videoTitleArrayEngineering[i]);
                youtubeVideoModel.setDuration(videoDurationArrayEngineering[i]);

                youtubeVideoModelArrayList.add(youtubeVideoModel);
            }
        }

        for (int i = 0; i < videoIDArrayScience.length; i++) {
            if ( prefavs.contains( _username + _password + videoIDArrayScience[i]) ) {
                YoutubeVideoModel youtubeVideoModel = new YoutubeVideoModel();
                youtubeVideoModel.setVideoId(videoIDArrayScience[i]);
                youtubeVideoModel.setTitle(videoTitleArrayScience[i]);
                youtubeVideoModel.setDuration(videoDurationArrayScience[i]);

                youtubeVideoModelArrayList.add(youtubeVideoModel);
            }
        }*/

        return youtubeVideoModelArrayList;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.favorites, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.app_bar_switch) {
            startActivity(new Intent(FavoritesActivity.this, LoginMainActivity.class)
            .putExtra("_username", _username).putExtra("_password", _password)
            .putExtra("_name_surname", _name_surname).putExtra("_email", _email));
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mathematics) {
            startActivity(new Intent(FavoritesActivity.this, LoginMathActivity.class)
                    .putExtra("_username", _username).putExtra("_password", _password)
                    .putExtra("_name_surname", _name_surname).putExtra("_email", _email));
        } else if (id == R.id.nav_engineering) {
            startActivity(new Intent(FavoritesActivity.this, LoginEngineeringActivity.class)
                    .putExtra("_username", _username).putExtra("_password", _password)
                    .putExtra("_name_surname", _name_surname).putExtra("_email", _email));
        } else if (id == R.id.nav_science) {
            startActivity(new Intent(FavoritesActivity.this, LoginScienceActivity.class)
                    .putExtra("_username", _username).putExtra("_password", _password)
                    .putExtra("_name_surname", _name_surname).putExtra("_email", _email));
        } else if (id == R.id.nav_favorites) {
            startActivity(new Intent(FavoritesActivity.this, FavoritesActivity.class)
                    .putExtra("user_name", _username).putExtra("pass_word", _password)
                    .putExtra("namesurname_", _name_surname).putExtra("email_", _email));
        } else if (id == R.id.nav_exit) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure do you want to exit?");
            alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    remember = getSharedPreferences("RememberMe", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = remember.edit();
                    editor.putString("username", _username);
                    editor.putString("password", _password);
                    editor.putString("name_surname", _name_surname);
                    editor.putString("email", _email);
                    editor.commit();
                    ActivityCompat.finishAffinity(FavoritesActivity.this);
                }
            });
            alertDialogBuilder.setNegativeButton("NO",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
