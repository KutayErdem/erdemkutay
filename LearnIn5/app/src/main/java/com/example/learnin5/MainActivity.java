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
import android.widget.EditText;
import android.widget.Toast;

import com.example.learnin5.adapter.YoutubeVideoAdapter;
import com.example.learnin5.model.YoutubeVideoModel;
import com.example.learnin5.utils.RecyclerViewOnClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private EditText username;
    private EditText password;
    private Button login;
    private Button signup;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpRecyclerView();
        populateRecyclerView();

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
        username = (EditText)hView.findViewById(R.id.username);
        password = (EditText)hView.findViewById(R.id.password);
        login = (Button)hView.findViewById(R.id.loginbutton);
        signup = (Button)hView.findViewById(R.id.signbutton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences = getSharedPreferences("Members", Context.MODE_PRIVATE);
                if (sharedpreferences.contains(username.getText().toString()) &&
                        sharedpreferences.contains(password.getText().toString())) {
                    String sharuser = sharedpreferences.getString(username.getText().toString(), null);
                    String sharpass = sharedpreferences.getString(password.getText().toString(), null);
                    if (sharuser.equals(sharpass)) {
                        String name_surname = " ";
                        String email = " ";
                        if (sharedpreferences.contains(sharuser + sharpass)) {
                            name_surname = sharedpreferences.getString(sharuser + sharpass, null);
                        }
                        if (sharedpreferences.contains(sharuser + sharpass + sharpass)) {
                            email = sharedpreferences.getString(sharuser + sharpass + sharpass, null);
                        }
                        Toast.makeText(getApplicationContext(),
                                "Redirecting...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, LoginMainActivity.class)
                                .putExtra("_username", sharuser).putExtra("_password", sharpass)
                                .putExtra("_name_surname", name_surname).putExtra("_email", email));
                    } else {
                        Toast.makeText(getApplicationContext(), "\t\t\t\t\t\t\t\tLogin Failed\nCheck Username/Password",
                                Toast.LENGTH_LONG).show();
                    }
                } else if (sharedpreferences.contains(username.getText().toString())) {
                    if (password.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "\t\t\t\t\tLogin Failed\nEnter Your Password", Toast.LENGTH_LONG).show();
                    } else {
                        password.setSelectAllOnFocus(true);
                        password.selectAll();
                        Toast.makeText(getApplicationContext(), "\t\t\t\t\tLogin Failed\nCheck Your Password", Toast.LENGTH_LONG).show();
                    }
                } else if (sharedpreferences.contains(password.getText().toString())) {
                    if (username.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "\t\t\t\t\tLogin Failed\nEnter Your Username", Toast.LENGTH_LONG).show();
                    } else {
                        username.setSelectAllOnFocus(true);
                        username.selectAll();
                        Toast.makeText(getApplicationContext(), "\t\t\t\t\tLogin Failed\nCheck Your Username", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (username.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "\t\t\t\t\t\t\t\tLogin Failed\nEnter Username&Password", Toast.LENGTH_LONG).show();
                    } else if (username.getText().toString().isEmpty()) {
                        password.setSelectAllOnFocus(true);
                        password.selectAll();
                        Toast.makeText(getApplicationContext(), "\t\t\t\t\tLogin Failed\nEnter Your Username", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Check Your Password", Toast.LENGTH_SHORT).show();
                    } else if (password.getText().toString().isEmpty()) {
                        username.setSelectAllOnFocus(true);
                        username.selectAll();
                        Toast.makeText(getApplicationContext(), "\t\t\t\t\tLogin Failed\nEnter Your Password", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Check Your Username", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "\t\t\t\t\t\t\t\tLogin Failed\nCheck Username&Password", Toast.LENGTH_LONG).show();
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignActivity.class));
            }
        });
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
                startActivity(new Intent(MainActivity.this, YoutubePlayerActivity.class)
                        .putExtra("video_id", youtubeVideoModelArrayList.get(position).getVideoId())
                        .putExtra("video_title",youtubeVideoModelArrayList.get(position).getTitle()));

            }
        }));
    }

    private ArrayList<YoutubeVideoModel> generateDummyVideoList() {
        ArrayList<YoutubeVideoModel> youtubeVideoModelArrayList = new ArrayList<>();

        //get the video id array, title array and duration array from strings.xml
        String[] videoIDArray = getResources().getStringArray(R.array.main_video_id_array);
        String[] videoTitleArray = getResources().getStringArray(R.array.main_video_title_array);
        String[] videoDurationArray = getResources().getStringArray(R.array.main_video_duration_array);

        //loop through all items and add them to arraylist
        for (int i = 0; i < videoIDArray.length; i++) {
                if ( videoDurationArray[i].length() == 4) {
                    int time = Integer.parseInt(videoDurationArray[i].substring(0,1));
                    if (time < 5 || (time == 5 && videoDurationArray[i].substring(2,4).equals("00"))) {
                        YoutubeVideoModel youtubeVideoModel = new YoutubeVideoModel();
                        youtubeVideoModel.setVideoId(videoIDArray[i]);
                        youtubeVideoModel.setTitle(videoTitleArray[i]);
                        youtubeVideoModel.setDuration(videoDurationArray[i]);

                        youtubeVideoModelArrayList.add(youtubeVideoModel);
                    }
                }
        }

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mathematics) {
            startActivity(new Intent(MainActivity.this, MathActivity.class));
        } else if (id == R.id.nav_engineering) {
            startActivity(new Intent(MainActivity.this, EngineeringActivity.class));
        } else if (id == R.id.nav_science) {
            startActivity(new Intent(MainActivity.this, ScienceActivity.class));
        } else if (id == R.id.nav_exit) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure do you want to exit?");
            alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    ActivityCompat.finishAffinity(MainActivity.this);
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
