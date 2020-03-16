package com.example.fenrirassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    private JSONObject restInfo;
    private Bitmap shopImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = this.getIntent();
        try {
            restInfo = new JSONObject(intent.getStringExtra("restInfo"));
            shopImage = (Bitmap)intent.getParcelableExtra("shopImage");
            setRestInfo();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setRestInfo(){
        ImageView thumb = (ImageView)findViewById(R.id.thumb);
        TextView name = (TextView)findViewById(R.id.name);
        TextView access = (TextView)findViewById(R.id.access);
        TextView tell = (TextView)findViewById(R.id.tell);
        TextView address = (TextView)findViewById(R.id.address);
        TextView openHours = (TextView)findViewById(R.id.openHours);

        try {
            if(shopImage != null){
                thumb.setImageBitmap(shopImage);
            }else{
                thumb.setImageResource(R.drawable.ic_launcher_background);
            }

            name.setText(restInfo.getString("name"));

            JSONObject accessInfo = restInfo.getJSONObject("access");
            String accessText = accessInfo.getString("line") + accessInfo.getString("station") + " " + accessInfo.getString("walk") + "分";
            if(accessText.equals(" 分")){
                accessText = " --";
            }
            access.setText(accessText);

            tell.setText(tell.getText() + "\n" +restInfo.getString("tel"));

            address.setText(address.getText() + "\n" + restInfo.getString("address"));

            openHours.setText(openHours.getText() + "\n" + restInfo.getString("opentime"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void backActivity(View view){
        finish();
    }
}
