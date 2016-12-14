package com.example.backloginsurmountable.services;

import android.util.Log;

import com.example.backloginsurmountable.Constants;
import com.example.backloginsurmountable.models.Game;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Guest on 12/14/16.
 */
public class GamesDBService {
    public void findGameListByName(String name, final Callback callback) {
        final OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.GAMESDB_API_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("name", name);
        String url = urlBuilder.build().toString();

        Log.v("URL:", url);

        final Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

    }

    public void findGameById(String name, final Callback callback) {
        final OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.API_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("api_key", Constants.GIANTBOMB_API_KEY);
        String url = urlBuilder.build().toString();

        Log.v("URL:", url);

        final Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

    }

    public Game processResultByName(Response response) {
        String name;
        String genre = "Unknown";
        String deck;
        String imageURL;
        String id;

        Game game = new Game("Not Found", "Not Found", "Not Found", "https://image.freepik.com/free-icon/question-mark_318-52837.jpg", "Not Found");

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject gameJSON = new JSONObject(jsonData);
                gameJSON = gameJSON.getJSONArray("results").getJSONObject(0);
                Log.v("JSON String", gameJSON.toString());

                name = gameJSON.getString("name");
                deck = gameJSON.getString("deck");
                imageURL = gameJSON.getJSONObject("image").getString("super_url");
                id = gameJSON.getString("id");
                game = new Game(name, genre, deck, imageURL, id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return game;
    }

    public Game processResultById(Response response) {
        String name;
        String genre = "Unknown";
        String deck;
        String imageURL;
        String id;

        Game game = new Game("Not Found", "Not Found", "Not Found", "https://image.freepik.com/free-icon/question-mark_318-52837.jpg", "Not Found");

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject gameJSON = new JSONObject(jsonData);
                gameJSON = gameJSON.getJSONArray("results").getJSONObject(0);
                Log.v("JSON String", gameJSON.toString());

                name = gameJSON.getString("name");
                deck = gameJSON.getString("deck");
                imageURL = gameJSON.getJSONObject("image").getString("super_url");
                id = gameJSON.getString("id");
                game = new Game(name, genre, deck, imageURL, id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return game;
    }
}
