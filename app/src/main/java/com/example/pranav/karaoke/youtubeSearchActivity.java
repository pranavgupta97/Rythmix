package com.example.pranav.karaoke;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class youtubeSearchActivity extends AppCompatActivity {

    private EditText searchInput;
    private ListView listOfVideos;
    private ArrayList<String> videoIds;
    private ArrayList<String> users;


    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_search);

        searchInput = (EditText) findViewById(R.id.search_input);
        listOfVideos = (ListView) findViewById(R.id.videos_found);
        videoIds = new ArrayList<String>();
        users = new ArrayList<String>();

        handler = new Handler();

        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchOnYoutube(v.getText().toString() + " karaoke");
                    return false;
                }
                return true;
            }
        });

        //for (int i = 0; i < 4; i++) {
            addClickListener();
        //}
    }

    private List<video> searchResults;

    private void searchOnYoutube(final String keyWords) {
        new Thread() {
            public void run() {
                youtubeConfiguration yc = new youtubeConfiguration(youtubeSearchActivity.this);
                searchResults = yc.search(keyWords);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateVideosFound();
                    }
                });
            }
        }.start();
    }

    private void updateVideosFound() {
        ArrayAdapter<video> adapter = new ArrayAdapter<video>(getApplicationContext(), R.layout.video_item, searchResults) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.video_item, parent, false);
                }
                ImageView thumbnail = (ImageView) convertView.findViewById(R.id.video_thumbnail);
                TextView title = (TextView) convertView.findViewById(R.id.video_title);
                TextView description = (TextView) convertView.findViewById(R.id.video_description);

                video searchResult = searchResults.get(position);

                Picasso.with(getApplicationContext()).load(searchResult.getThumbnailUrl()).into(thumbnail);title.setText(searchResult.getTitle());description.setText(searchResult.getDescription());
                return convertView;
            }
        };
        listOfVideos.setAdapter(adapter);
    }

    private void addClickListener() {
        listOfVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                users = getIntent().getStringArrayListExtra("USERS");
                videoIds.add(searchResults.get(position).getId());
                Toast.makeText(youtubeSearchActivity.this, "Song queued. Please select next song.", Toast.LENGTH_LONG).show();
                if (videoIds.size() == users.size()) {
                    Intent intent = new Intent(youtubeSearchActivity.this, youtubePlayerActivity.class);
                    intent.putStringArrayListExtra("VIDEO_ID", videoIds);
                    startActivity(intent);
                }
            }
        });
    }
}
