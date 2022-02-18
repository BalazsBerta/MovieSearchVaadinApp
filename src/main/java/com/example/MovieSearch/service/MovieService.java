package com.example.MovieSearch.service;



import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;


@RestController
@Service

public class MovieService {
    private OkHttpClient client;
    private Response response;
    private final String key = "f0dcdeea"; // please register on the omdbapi com site for an Api key
    private String title;


    public JSONObject getMovieData() throws IOException, JSONException {
        client = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.omdbapi.com/?t=" + getTitle() + "&apikey=" + key).build();
        response = client.newCall(request).execute();
        return new JSONObject(response.body().string());

    }

    public JSONObject getMultipleMovieData() throws IOException, JSONException {
        client = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.omdbapi.com/?s=" + getTitle() + "&apikey=" + key).build();
        response = client.newCall(request).execute();
        return new JSONObject(response.body().string());

    }

    public JSONArray returnRatings() throws JSONException, IOException {
        return getMovieData().getJSONArray("Ratings");
    }

    public JSONArray returnMovies() throws JSONException, IOException {
        return getMovieData().getJSONArray("Search");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}



