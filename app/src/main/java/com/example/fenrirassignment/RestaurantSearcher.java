package com.example.fenrirassignment;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestaurantSearcher extends AsyncTask<String, Void, String> {

    private final  String API_URL = "https://api.gnavi.co.jp/RestSearchAPI/v3/";
    private final String ACCESS_KEY = "a50663627a97d8e8159fa7c1f8db91b1";

    private MainActivity mainActivity;

    public RestaurantSearcher(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = API_URL + "?keyid=" + ACCESS_KEY + "&latitude=" + strings[0] + "&longitude=" + strings[1] + "&range=" + strings[2];
        //エミュレータでのデバッグ用
        //String url = API_URL + "?keyid=" + ACCESS_KEY + "&latitude=35.0706" + "&longitude=135.9940" + "&range=" + strings[2];
        try {
            URL query = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)query.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int statusCode = connection.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                StringBuilder result = new StringBuilder();
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                while((line = reader.readLine()) != null) {
                    result.append(line);
                }
                connection.disconnect();
                return result.toString();
            }else{
                connection.disconnect();
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        mainActivity.transSearchResult(result);
    }
}
