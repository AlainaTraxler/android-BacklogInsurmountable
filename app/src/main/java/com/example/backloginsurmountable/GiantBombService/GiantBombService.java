package com.example.backloginsurmountable.GiantBombService;

import android.util.Log;

import com.example.backloginsurmountable.Constants;
import com.example.backloginsurmountable.models.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Guest on 12/1/16.
 */
public class GiantBombService {

    public static void findGameByName(String name, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.API_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("api_key", Constants.GIANTBOMB_API_KEY);
        urlBuilder.addQueryParameter("format", "json");
        urlBuilder.addQueryParameter("query", '"' + name + '"');
        urlBuilder.addQueryParameter("limit", "1");
        urlBuilder.addQueryParameter("resources", "game");
        urlBuilder.addQueryParameter("field_list", "id,image,deck,name");
        String url = urlBuilder.build().toString();

        Log.v("URL:", url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public Game processResultByName(Response response) {
        String name;
        String genre = "Unknown";
        String deck;
        Game game = new Game("Error", "Error", "Error");

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject gameJSON = new JSONObject(jsonData);
                gameJSON = gameJSON.getJSONArray("results").getJSONObject(0);
                Log.v("JSON String", gameJSON.toString());

                name = gameJSON.getString("name");
                Log.v("Name: ", name);
                deck = gameJSON.getString("deck");
                Log.v("Deck: ", deck);
                game = new Game("x", genre, deck);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return game;
    }
}
