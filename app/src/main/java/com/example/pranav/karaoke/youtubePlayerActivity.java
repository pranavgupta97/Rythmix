package com.example.pranav.karaoke;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class youtubePlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    private YouTubePlayerView youTubePlayerView;
    private ArrayList<String> videoIds;
    private Button playerButton;
    private ListView queuedVideos;
    private ArrayList<String> videoTitles;
    private TextView upNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player_view);
        youTubePlayerView.initialize(youtubeConfiguration.MY_DEVELOPER_KEY, youtubePlayerActivity.this);
        playerButton = (Button) findViewById(R.id.player_button);
        queuedVideos = (ListView) findViewById(R.id.queued_songs);
        videoTitles = getIntent().getStringArrayListExtra("VIDEO_TITLE");
        upNext = (TextView) findViewById(R.id.upNext);

        if (videoTitles.size() > 1) {
            videoTitles.remove(0);
        }
        else if (videoTitles.size() == 1) {
            videoTitles.remove(0);
            upNext.setVisibility(View.INVISIBLE);
            playerButton.setVisibility(View.INVISIBLE);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, videoTitles) {
            @Override
            public View getView (int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);
                return view;
            }
        };
        queuedVideos.setAdapter(arrayAdapter);
    }



    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            final ArrayList<String> nameList = getIntent().getStringArrayListExtra("VIDEO_ID");
                youTubePlayer.cueVideo(nameList.get(0));
                nameList.remove(0);
                youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String s) {

                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {

                    }

                    @Override
                    public void onVideoEnded() {
                        if (nameList.size() == 0) {
                            Intent finalIntent = new Intent(youtubePlayerActivity.this, finalActivity.class);
                            startActivity(finalIntent);
                        }
                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {

                    }
                });
                playerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sameIntent = getIntent();
                        sameIntent.putStringArrayListExtra("VIDEO_ID", nameList);
                        sameIntent.putStringArrayListExtra("VIDEO_TITLE", videoTitles);
                        finish();
                        startActivity(sameIntent);
                    }
                });
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(youtubePlayerActivity.this, getString(R.string.failed), Toast.LENGTH_LONG).show();
    }
}
