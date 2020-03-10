package com.example.fenrirassignment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageGetter extends AsyncTask<String, Void, Bitmap> {

    private SearchResult searchResult;
    private int index;

    public ImageGetter(SearchResult searchResult, int index){
        this.searchResult = searchResult;
        this.index = index;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL imageUrl = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection)imageUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap image = BitmapFactory.decodeStream(inputStream);
            connection.disconnect();
            return image;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        searchResult.setRestInfo(result, index);
    }
}
