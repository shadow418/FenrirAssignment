package com.example.fenrirassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchResult extends AppCompatActivity {

    private JSONObject searchResult;
    private JSONArray restList;
    private LinearLayout list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = this.getIntent();
        String result = intent.getStringExtra("result");
        list = (LinearLayout)findViewById(R.id.list);
        try {
            searchResult = new JSONObject(result);
            restList = searchResult.getJSONArray("rest");
            for(int i = 0; i < 2; i++){
                JSONObject restInfo = restList.getJSONObject(i);
                ImageGetter imageGetter = new ImageGetter(this, i);
                JSONObject thumbInfo = restInfo.getJSONObject("image_url");
                imageGetter.execute(thumbInfo.getString("shop_image1"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //このメソッド肥大化してるからメソッド分割する
    public void setRestInfo(Bitmap image, int index){
        try{
            final JSONObject restInfo = restList.getJSONObject(index);

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchResult.this, DetailActivity.class);
                    intent.putExtra("restInfo", restInfo.toString());
                    startActivity(intent);
                    Log.d("test", "jumpDetail");
                }
            });

            ImageView thumb = new ImageView(this);
            //Javaからの画像サイズの変え方がわからない，heightを固定値にしたい
            thumb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            thumb.setImageBitmap(image);
            thumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            thumb.setAdjustViewBounds(true);

            TextView name = new TextView(this);
            name.setText(restInfo.getString("name"));
            name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

            JSONObject accessInfo = restInfo.getJSONObject("access");
            TextView access = new TextView(this);
            access.setText(accessInfo.getString("line") + accessInfo.getString("station") + " " + accessInfo.getString("walk") + "分");
            access.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

            TextView frame = new TextView(this);
            frame.setBackgroundColor(Color.rgb(255,235,205));

            linearLayout.addView(thumb);
            linearLayout.addView(name);
            linearLayout.addView(access);
            linearLayout.addView(frame);
            list.addView(linearLayout);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void backActivity(View view){
        finish();
    }
}
