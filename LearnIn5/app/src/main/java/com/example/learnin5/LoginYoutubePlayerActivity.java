package com.example.learnin5;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnin5.model.Questions;
import com.example.learnin5.model.VideoQuestions;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import com.example.learnin5.utils.Constants;
import com.example.learnin5.adapter.QuestionsAdapter;

import java.util.ArrayList;

public class LoginYoutubePlayerActivity extends YouTubeBaseActivity {
    private static final String TAG = YoutubePlayerActivity.class.getSimpleName();
    private String videoID;
    private String videoTitle;
    private String videoDuration;
    private String username;
    private String password;
    private YouTubePlayerView youTubePlayerView;
    private RecyclerView recyclerView;
    private TextView textView;
    private ImageButton upButton;
    private ImageButton downButton;
    private Button checkButton;
    private int correctCount;
    private int incorrectCount;
    private int emptyCount;
    private Button favs;
    SharedPreferences favspref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_youtube_player);
        //get the video id
        videoID = getIntent().getStringExtra("video_id");
        videoTitle = getIntent().getStringExtra("video_title");
        videoDuration = getIntent().getStringExtra("video_duration");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        initializeYoutubePlayer();
        textView = findViewById(R.id.video_text);
        textView.setText(videoTitle);
        favs = findViewById(R.id.add_favs);
        favs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favspref = getSharedPreferences("UserFavorites", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = favspref.edit();
                editor.putString(username + password + videoID, videoID);
                editor.commit();
                Toast.makeText(getApplicationContext(), "Video is added\n  to Favorites", Toast.LENGTH_LONG).show();

            }
        });
        setUpAndPopulateRecyclerView();
    }
    private void setUpAndPopulateRecyclerView() {
        recyclerView = findViewById(R.id.recycler_questions_view);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final ArrayList<Questions> questions = new ArrayList<>();
        VideoQuestions videoQuestions = new VideoQuestions();

        for (int i=0; i< videoQuestions.math_questions.length; i++) {
            if (videoTitle.equals(videoQuestions.math_questions[i][videoQuestions.math_questions[i].length-1])) {
                Questions question = new Questions(videoQuestions.math_questions[i]);
                questions.add(question);
            }
        }
        final QuestionsAdapter mAdapter = new QuestionsAdapter(questions);
        recyclerView.setAdapter(mAdapter);
        upButton = findViewById(R.id.upbutton);
        downButton = findViewById(R.id.downbutton);
        checkButton = findViewById(R.id.checkall_button);

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutManager.findFirstCompletelyVisibleItemPosition() != 0) {
                    recyclerView.smoothScrollToPosition(layoutManager.findFirstCompletelyVisibleItemPosition() - 1);

                }
            }
        });
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(layoutManager.findFirstCompletelyVisibleItemPosition()+1);
            }
        });
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctCount = 0;
                incorrectCount = 0;
                emptyCount = 0;
                for (int x=0; x< questions.size(); x++) {
                    if (questions.get(x).getCorrect().equals("1")) correctCount++;
                    else if (questions.get(x).getCorrect().equals("-1")) incorrectCount++;
                    else emptyCount++;
                }
                Toast.makeText(getApplicationContext(),"TOTAL:  " + questions.size() + " QUESTIONS\n" +
                        "TRUE:  " + correctCount + "\n" + "FALSE:  " + incorrectCount + "\n" +
                        "EMPTY:  " + emptyCount ,Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * initialize the youtube player
     */
    private void initializeYoutubePlayer() {
        youTubePlayerView.initialize(Constants.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer,
                                                boolean wasRestored) {

                //if initialization success then load the video id to youtube player
                if (!wasRestored) {
                    //set the player style here: like CHROMELESS, MINIMAL, DEFAULT
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);

                    //load the video
                    //youTubePlayer.loadVideo(videoID);

                    //OR

                    //cue the video
                    youTubePlayer.cueVideo(videoID);

                    //if you want when activity start it should be in full screen uncomment below comment
                    //  youTubePlayer.setFullscreen(true);

                    //If you want the video should play automatically then uncomment below comment
                    //  youTubePlayer.play();

                    //If you want to control the full screen event you can uncomment the below code
                    //Tell the player you want to control the fullscreen change
                   /*player.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
                    //Tell the player how to control the change
                    player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                        @Override
                        public void onFullscreen(boolean arg0) {
                            // do full screen stuff here, or don't.
                            Log.e(TAG,"Full screen mode");
                        }
                    });*/

                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                //print or show error if initialization failed
                Log.e(TAG, "Youtube Player View initialization failed");
            }
        });
    }

}
