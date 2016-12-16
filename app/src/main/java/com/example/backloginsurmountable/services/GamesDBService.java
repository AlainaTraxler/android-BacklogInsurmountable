package com.example.backloginsurmountable.services;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.backloginsurmountable.Constants;
import com.example.backloginsurmountable.R;
import com.example.backloginsurmountable.adapters.GameletListAdapter;
import com.example.backloginsurmountable.models.Game;
import com.example.backloginsurmountable.models.GamesDBGame;
import com.example.backloginsurmountable.models.GamesDBGamelet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.XML;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.GAMESDB_GAMESLIST_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("name", name);
        String url = urlBuilder.build().toString();

        Log.v("URL:", url);

        final Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

    }

    public void findGameById(String id, final Callback callback) {
        final OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.GAMESDB_GAME_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("id", id);
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

    public GamesDBGame processResultById(Response response, int id) {
        List<String> genres = new ArrayList<>();
        String players = "?";
        String publisher = "?";
        String developer = "?";
        List<String> screenshots = new ArrayList<>();
        String coop = "?";
        String overview = "?";
        String gameTitle = "?";
        String boxArt = "?";
        String releaseDate = "?";

        JSONObject jsonObj = null;
        try {
            jsonObj = XML.toJSONObject(response.body().string());

            Log.v("::::", jsonObj.toString());
            if(jsonObj.getJSONObject("Data").getJSONObject("Game").has("GameTitle")){
                gameTitle = jsonObj.getJSONObject("Data").getJSONObject("Game").getString("GameTitle");
            }

            Log.v("Title", gameTitle);
            if (jsonObj.getJSONObject("Data").getJSONObject("Game").has("Players")) {
                players = jsonObj.getJSONObject("Data").getJSONObject("Game").getString("Players");
                Log.v("Players", players);
            }

            if(jsonObj.getJSONObject("Data").getJSONObject("Game").has("Publisher")){
                publisher = jsonObj.getJSONObject("Data").getJSONObject("Game").getString("Publisher");
                Log.v("Publisher", publisher);
            }

            if(jsonObj.getJSONObject("Data").getJSONObject("Game").has("Developer")){
                developer = jsonObj.getJSONObject("Data").getJSONObject("Game").getString("Developer");
                Log.v("Developer", developer);
            }

            if(jsonObj.getJSONObject("Data").getJSONObject("Game").has("ReleaseDate")){
                releaseDate = jsonObj.getJSONObject("Data").getJSONObject("Game").getString("ReleaseDate");
                Log.v("Date", releaseDate);
            }

            if(jsonObj.getJSONObject("Data").getJSONObject("Game").has("Co-op")){
                coop = jsonObj.getJSONObject("Data").getJSONObject("Game").getString("Co-op");
                Log.v("Co-op", coop);
            }

            if(jsonObj.getJSONObject("Data").getJSONObject("Game").has("Overview")){
                overview = jsonObj.getJSONObject("Data").getJSONObject("Game").getString("Overview").replace("*", ".").replace("\r", "").replace("\n", "");
                Log.v("Overview", overview);
            }

            if(jsonObj.getJSONObject("Data").getJSONObject("Game").getJSONObject("Images").has("boxart")){
                if(jsonObj.getJSONObject("Data").getJSONObject("Game").getJSONObject("Images").getString("boxart").contains("[")){
                    Log.v("::Boxart::", "Array");
                    for (int i = 0; i < jsonObj.getJSONObject("Data").getJSONObject("Game").getJSONObject("Images").getJSONArray("boxart").length(); i++){
                        if(jsonObj.getJSONObject("Data").getJSONObject("Game").getJSONObject("Images").getJSONArray("boxart").getJSONObject(i).getString("side").equals("front")){
                            boxArt = "http://thegamesdb.net/banners/_gameviewcache/" + jsonObj.getJSONObject("Data").getJSONObject("Game").getJSONObject("Images").getJSONArray("boxart").getJSONObject(i).getString("content");
                            Log.v("Boxart", boxArt);
                        }
                    }
                }else{
                    Log.v("::::", "object");
                    boxArt = "http://thegamesdb.net/banners/_gameviewcache/" + jsonObj.getJSONObject("Data").getJSONObject("Game").getJSONObject("Images").getJSONObject("boxart").getString("content");
                }

            }else{
                boxArt = ("https://image.freepik.com/free-icon/question-mark_318-52837.jpg");
            }

            if(jsonObj.getJSONObject("Data").getJSONObject("Game").getJSONObject("Images").has("screenshot")){
                if(jsonObj.getJSONObject("Data").getJSONObject("Game").getJSONObject("Images").getString("screenshot").contains("[")){
                    Log.v("::Screenshots::", "Array");
                    for (int i = 0; i < jsonObj.getJSONObject("Data").getJSONObject("Game").getJSONObject("Images").getJSONArray("screenshot").length(); i++){
                        String catcher = "http://thegamesdb.net/banners/_gameviewcache/" + jsonObj.getJSONObject("Data").getJSONObject("Game").getJSONObject("Images").getJSONArray("screenshot").getJSONObject(i).getJSONObject("original").getString("content");
                        screenshots.add(catcher);
                        Log.v("Screenshot", screenshots.get(i));

                    }
                }else{
                    Log.v("::::", "object");
                }
            }else{
                screenshots.add("https://image.freepik.com/free-icon/question-mark_318-52837.jpg");
            }

            if(jsonObj.getJSONObject("Data").getJSONObject("Game").has("Genres")){
                String catcher = jsonObj.getJSONObject("Data").getJSONObject("Game").getJSONObject("Genres").getString("genre").replace("[", "").replace("\"", "").replace("]", "");
                Log.v("::Genres::", catcher);
            }else{
                genres.add("?");
            }
            Log.v(">>>>>", "");
        } catch (JSONException e) {
            Log.e("JSON exception", e.getMessage());
            e.printStackTrace();
        } catch (IOException e){

        }

        GamesDBGame game = new GamesDBGame(gameTitle, overview, coop, developer, publisher, players, boxArt, id, screenshots, genres);

        return game;
    }
}
