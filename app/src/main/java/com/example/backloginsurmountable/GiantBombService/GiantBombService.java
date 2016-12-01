package com.example.backloginsurmountable.GiantBombService;

import android.util.Log;

import com.example.backloginsurmountable.Constants;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Callback;

/**
 * Created by Guest on 12/1/16.
 */
public class GiantBombService {

    public static void findGameByName(String name) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.API_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("api_key", Constants.GIANTBOMB_API_KEY);
        urlBuilder.addQueryParameter("format", "json");
        urlBuilder.addQueryParameter("query", '"' + name + '"');
        urlBuilder.addQueryParameter("limit", "1");
        urlBuilder.addQueryParameter("resources", "game");
        urlBuilder.addQueryParameter("field_list", "id,image,deck");
        String url = urlBuilder.build().toString();

        Log.v("URL:", url);
    }

}
