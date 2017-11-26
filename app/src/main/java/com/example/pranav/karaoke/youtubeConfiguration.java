package com.example.pranav.karaoke;

import android.content.Context;
import android.util.Log;
import java.util.List;
import java.util.ArrayList;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.List;

/**
 * Created by Pranav on 11/24/2017.
 */

public class youtubeConfiguration {
    private YouTube youTube;
    private YouTube.Search.List searchQuery;

    public static final String MY_DEVELOPER_KEY = "AIzaSyB1otWRkq9LPf2UqbswHSoPrO8M39ViUCk";

    public youtubeConfiguration(Context content) {
        youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {}
        }).setApplicationName(content.getString(R.string.app_name)).build();

        try {
            searchQuery = youTube.search().list("id,snippet");
            searchQuery.setKey(MY_DEVELOPER_KEY);
            searchQuery.setType("Video");
            searchQuery.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
        }catch (IOException e) {
            Log.d("YC", "Could not initialize: " + e);
        }
    }

    public List<video> search(String keyWords) {
        searchQuery.setQ(keyWords);
        try {
            SearchListResponse response = searchQuery.execute();
            List<SearchResult> results = response.getItems();

            List<video> items = new ArrayList<video>();
            for (SearchResult result:results) {
                video item = new video();
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailUrl(result.getSnippet().getThumbnails().getDefault().getUrl());
                item.setId(result.getId().getVideoId());
                items.add(item);
            }
            return items;
        }catch (IOException e) {
            Log.d("YC", "Could not search: " + e);
            return null;
        }
    }
}
