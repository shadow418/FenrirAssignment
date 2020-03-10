package com.example.fenrirassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchResult extends AppCompatActivity {

    private JSONObject searchResult;
    private JSONArray restList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = this.getIntent();
        String result = intent.getStringExtra("result");
        try {
            searchResult = new JSONObject(result);
            restList = searchResult.getJSONArray("rest");
            JSONObject restInfo = restList.getJSONObject(0);
            ImageGetter imageGetter = new ImageGetter(this);
            JSONObject thumbInfo = restInfo.getJSONObject("image_url");
            imageGetter.execute(thumbInfo.getString("shop_image1"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setRestInfo(Bitmap image){
        try{
            JSONObject restInfo = restList.getJSONObject(0);
            ImageView thumb = (ImageView)findViewById(R.id.thumb1);
            thumb.setImageBitmap(image);
            TextView name = (TextView)findViewById(R.id.name1);
            name.setText(restInfo.getString("name"));
            JSONObject accessInfo = restInfo.getJSONObject("access");
            TextView access = (TextView)findViewById(R.id.access1);
            access.setText(accessInfo.getString("line") + accessInfo.getString("station") + " " + accessInfo.getString("walk") + "分");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void backActivity(View view){
        finish();
    }
}
