package com.example.pranav.karaoke;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class drunkModeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int recoveryRequest = 1;
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drunk_mode);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youTubeView);
        youTubePlayerView.initialize(youtubeConfigurationActivity.youtubeAPIKey, drunkModeActivity.this);
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo("6I3lHjuNn7g");
        }
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(drunkModeActivity.this, recoveryRequest).show();
        }
        else {
            String error = String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
            Toast.makeText(drunkModeActivity.this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == recoveryRequest) {
            getYouTubePlayerProvider().initialize(youtubeConfigurationActivity.youtubeAPIKey, drunkModeActivity.this);
        }
    }

    protected Provider getYouTubePlayerProvider() {
        return youTubePlayerView;
    }
}
